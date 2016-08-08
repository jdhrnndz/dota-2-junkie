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
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchDetails;
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

public class MainActivity extends AppCompatActivity implements DotaApiResponseListener{
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private PlayerSummary.DotaPlayer mCurrentPlayer;
    private MatchHistory mMatchHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Set "Steam ID" label as the center of the user info grid layout
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: Fix action bar scroll behavior to be ignored by user profile and play style tabs

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        parseUserInfoFromIntent();
        ((ProfileFragment) TabFragment.PROFILE.getFragment()).setCurrentPlayer(mCurrentPlayer);

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
        switch (type) {
            case ApiManager.MATCH_HISTORY_RESPONSE_TYPE:
                mMatchHistory = (MatchHistory) response;
                MatchHistory.Match[] matches = mMatchHistory.getResult().getMatches();
                int len = matches.length;

                // Build the url's query section
                HashMap<String, String> args = new HashMap<>();

                for(int i=0; i<len; i++) {
                    args.put("match_id", String.valueOf(matches[i].getId()));
                    String url = UrlBuilder.buildUrl(getApplicationContext(), R.string.get_match_details, args);

                    ApiManager.fetchMatchDetails(getApplicationContext(), url, MainActivity.this);
                }
                break;
            case ApiManager.MATCH_DETAILS_RESPONSE_TYPE:
                ((MatchesFragment) TabFragment.MATCHES.getFragment()).updateMatches(((MatchHistory.Match) response));
                break;
        }
    }

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        Snackbar mErrorMessage = Snackbar.make(findViewById(R.id.recycler_view_matches), R.string.match_history_error_message, Snackbar.LENGTH_LONG);
        mErrorMessage.show();
    }
}
