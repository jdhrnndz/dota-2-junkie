package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stratpoint.jdhrnndz.dota2junkie.network.GsonRequest;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.TabFragment;
import com.stratpoint.jdhrnndz.dota2junkie.network.VolleySingleton;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.MatchesFragment;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    RequestQueue mRequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
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
//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.icon_last_log_off);
//        ab.setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        // TODO: Used serialized name in GSON objects (ex. @SerializedName("name") private String name;)
        // TODO: Move all network requests to ApiManager class
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
        // Builds the url to retrieve match details
        final StringBuilder url = new StringBuilder();
        url.append(getString(R.string.get_match_history));
        url.append("?key=");
        url.append(getString(R.string.api_key));
        url.append("&account_id=");
        url.append(mCurrentPlayer.getSteamId());

        GsonRequest matchHistoryRequest = new GsonRequest<MatchHistory>(url.toString(), MatchHistory.class, null,
                new Response.Listener<MatchHistory>() {
                    @Override
                    public void onResponse(MatchHistory response) {
                        mMatchHistory = response;

                        ArrayList<Long> matchIds = new ArrayList<Long>();
                        int len = response.getResult().getMatches().length;
                        MatchHistory.Match[] matches = response.getResult().getMatches();

                        for(int i=0; i<len; i++) {
                            matchIds.add(matches[i].getId());
                        }

                        ((MatchesFragment)TabFragment.MATCHES.getFragment()).setMatchIds(matchIds);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar mErrorMessage = Snackbar.make(findViewById(R.id.recycler_view_matches), R.string.match_history_error_message, Snackbar.LENGTH_LONG);
                        mErrorMessage.show();
                    }
                }
        );

        mRequestQueue.add(matchHistoryRequest);
    }
}
