package com.stratpoint.jdhrnndz.dota2junkie.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stratpoint.jdhrnndz.dota2junkie.adapter.AppFragmentPagerAdapter;
import com.stratpoint.jdhrnndz.dota2junkie.model.DotaPlayer;
import com.stratpoint.jdhrnndz.dota2junkie.model.HeroReference;
import com.stratpoint.jdhrnndz.dota2junkie.model.ItemReference;
import com.stratpoint.jdhrnndz.dota2junkie.model.Match;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchHistory;
import com.stratpoint.jdhrnndz.dota2junkie.model.PlayerSummary;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.MatchesFragment;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.ProfileFragment;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.TabFragment;
import com.stratpoint.jdhrnndz.dota2junkie.network.ApiManager;
import com.stratpoint.jdhrnndz.dota2junkie.network.DotaApiResponseListener;
import com.stratpoint.jdhrnndz.dota2junkie.network.UrlBuilder;

import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/8/16
 * Description: This class uses the activity_main layout to create the view for the main activity.
 * Inside the class is where the data are linked to the respective views.
 * It also implements DotaApiResponseListener to handle the processing of the requests to the dota
 * api and distribute the proper data among its fragments.
 */
public class MainActivity extends AppCompatActivity implements DotaApiResponseListener{
    private final int MATCH_COUNT_FOR_GRAPH = 20;

    @BindView(R.id.viewpager) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.appbar) AppBarLayout mAppBarLayout;
    @BindView(R.id.main_layout) CoordinatorLayout mRootView;

    private DotaPlayer mCurrentPlayer;
    private boolean profileHasMatchData = false;

    @BindString(R.string.sharedpref_herojson_key) String heroJsonKey;
    @BindString(R.string.sharedpref_itemjson_key) String itemJsonKey;

    public static HeroReference heroRef;
    public static ItemReference itemRef;

    private Snackbar mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Assign values to views
        populateViews();

        // Process data from sign in activity passed via intent
        parseUserInfoFromIntent();
        ((ProfileFragment) TabFragment.PROFILE.getFragment()).setCurrentPlayer(mCurrentPlayer);
        // Gets the list of matches with only the basic match info, requests for details afterwards
        fetchMatchHistoryFromNetwork();

        // Retrieve hero and item references from SharedPreferences
        SharedPreferences defaultSP = PreferenceManager.getDefaultSharedPreferences(this);
        String heroJson = defaultSP.getString(heroJsonKey, "");
        String itemJson = defaultSP.getString(itemJsonKey, "");
        Gson gson = new Gson();
        heroRef = gson.fromJson(heroJson, HeroReference.class);
        itemRef = gson.fromJson(itemJson, ItemReference.class);
    }

    @Override
    public void onBackPressed() {
        // Goes back to the previous tab or to the log in screen (if the current tab is the first)
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
          mViewPager.setCurrentItem(currentItem - 1);
        }
    }

    private void populateViews() {
        // Custom Toolbar
        setSupportActionBar(mToolbar);
        // Use custom adapter
        mViewPager.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Returns the toolbar to the collapsed state when not in scrollable tab
                if (position == TabFragment.ARCADE.getPosition() || position == TabFragment.PROFILE.getPosition())
                    mAppBarLayout.setExpanded(false, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        mAppBarLayout.setExpanded(false, false);

        mErrorMessage = Snackbar.make(mRootView, R.string.match_history_error_message, Snackbar.LENGTH_LONG);
    }

    private void parseUserInfoFromIntent() {
        Gson gson = new GsonBuilder().create();

        // Retrieves user info from intent (value provided by LogInActivity) as string
        String userInfoString = getIntent().getStringExtra(LogInActivity.EXTRA_USER_INFO);
        // Converts user info string to an object
        PlayerSummary playerSummary = gson.fromJson(userInfoString, PlayerSummary.class);
        // Get the first (and only) player in the result object
        mCurrentPlayer = playerSummary.getResponse().getPlayers()[0];
        // Pass the player object to Match Tab
        ((MatchesFragment) TabFragment.MATCHES.getFragment()).setCurrentPlayer(mCurrentPlayer);
    }

    private void fetchMatchHistoryFromNetwork() {
        // Build the url's query section
        HashMap<String, String> args = new HashMap<>();

        // Add query attributes
        args.put("account_id", mCurrentPlayer.getSteamId());
        args.put("matches_requested", "20");

        // Build the url to retrieve match details
        String url = UrlBuilder.buildGenericUrl(this, R.string.get_match_history, args);

        // First param as context, third as DotaApiResponseListener
        ApiManager.fetchMatchHistory(this, url, this);
    }

    public void onReceiveResponse(int statusCode, Object response, int type) {
        // Generic receive response method, identifies the type of response by the argument from
        // the ApiManager class
        switch (type) {
            case ApiManager.MATCH_HISTORY_RESPONSE_TYPE:
                MatchHistory mMatchHistory = (MatchHistory) response;
                Match[] matches = mMatchHistory.getHistory().getMatches();

                // Build the url's query section
                HashMap<String, String> args = new HashMap<>();

                // Queues all the request for match details using the fetched match IDs
                for (Match match : matches) {
                    args.put("match_id", String.valueOf(match.getId()));
                    String url = UrlBuilder.buildGenericUrl(getApplicationContext(), R.string.get_match_details, args);

                    ApiManager.fetchMatchDetails(getApplicationContext(), url, MainActivity.this);
                }
                break;
            case ApiManager.MATCH_DETAILS_RESPONSE_TYPE:
                // Updates the matches in the match fragment everytime a response for match details arrives
                MatchesFragment matchesFragment = (MatchesFragment) TabFragment.MATCHES.getFragment();
                ProfileFragment profileFragment = (ProfileFragment) TabFragment.PROFILE.getFragment();

                matchesFragment.updateMatches(((Match) response));
                // Updates progress 'til the number of received matches == MATCH_COUNT_FOR_GRAPH
                if (!profileHasMatchData) {
                    if (matchesFragment.getMatches().size() >= MATCH_COUNT_FOR_GRAPH) {
                        profileHasMatchData = true;
                        profileFragment.populateMatchResultsGraph(matchesFragment.getMatches(), MATCH_COUNT_FOR_GRAPH);
                    } else {
                        profileFragment.incrementProgress();
                    }
                }
                break;
        }
    }

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        if(!mErrorMessage.isShown()) mErrorMessage.show(); // Don't spam the error message
    }
}
