package com.stratpoint.jdhrnndz.dota2junkie.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stratpoint.jdhrnndz.dota2junkie.adapter.AppFragmentPagerAdapter;
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

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/8/16
 * Description: This class uses the activity_main layout to create the view for the main activity.
 * Inside the class is where the data are linked to the respective views.
 * It also implements DotaApiResponseListener to handle the processing of the requests to the dota
 * api and distribute the proper data among its fragments.
 */
public class MainActivity extends AppCompatActivity implements DotaApiResponseListener{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    private PlayerSummary.DotaPlayer mCurrentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map views from content view as the activity's attributes
        assignViews();
        // Assign values to views
        populateViews();
        // Process data from sign in activity passed via intent
        parseUserInfoFromIntent();
        ((ProfileFragment) TabFragment.PROFILE.getFragment()).setCurrentPlayer(mCurrentPlayer);
        // Gets the list of matches with only the basic match info, requests for details afterwards
        fetchMatchHistoryFromNetwork();
    }

    @Override
    public void onBackPressed() {
        int currentItem = mViewPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
          mViewPager.setCurrentItem(currentItem - 1);
        }
    }

    private void assignViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void populateViews() {
        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void parseUserInfoFromIntent() {
        Gson gson = new GsonBuilder().create();

        String userInfoString = getIntent().getStringExtra(LogInActivity.EXTRA_USER_INFO);

        PlayerSummary playerSummary = gson.fromJson(userInfoString, PlayerSummary.class);

        mCurrentPlayer = playerSummary.getResponse().getPlayers()[0];
    }

    private void fetchMatchHistoryFromNetwork() {
        // Build the url's query section
        HashMap<String, String> args = new HashMap<>();

        args.put("account_id", mCurrentPlayer.getSteamId());

        // Build the url to retrieve match details
        String url = UrlBuilder.buildUrl(MainActivity.this, R.string.get_match_history, args);

        ApiManager.fetchMatchHistory(getApplicationContext(), url, this);
    }

    public void onReceiveResponse(int statusCode, Object response, int type) {
        // Generic receive response method, identifies the type of response by the argument from
        // the ApiManager class
        switch (type) {
            case ApiManager.MATCH_HISTORY_RESPONSE_TYPE:
                MatchHistory mMatchHistory = (MatchHistory) response;
                MatchHistory.Match[] matches = mMatchHistory.getResult().getMatches();

                // Build the url's query section
                HashMap<String, String> args = new HashMap<>();

                // Queues all the request for match details using fetched match IDs
                for (MatchHistory.Match match : matches) {
                    args.put("match_id", String.valueOf(match.getId()));
                    String url = UrlBuilder.buildUrl(getApplicationContext(), R.string.get_match_details, args);

                    ApiManager.fetchMatchDetails(getApplicationContext(), url, MainActivity.this);
                }
                break;
            case ApiManager.MATCH_DETAILS_RESPONSE_TYPE:
                // Updates the matches in the match fragment everytime a response for match details
                // arrives
                ((MatchesFragment) TabFragment.MATCHES.getFragment()).updateMatches(((MatchHistory.Match) response));
                break;
        }
    }

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        Snackbar mErrorMessage = Snackbar.make(findViewById(R.id.recycler_view_matches),
                R.string.match_history_error_message,
                Snackbar.LENGTH_LONG);
        mErrorMessage.show();
    }
}
