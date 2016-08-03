package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private DotaPlayer mCurrentPlayer;

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

        parseUserInfoFromIntent();
        ((ProfileFragment) TabFragment.PROFILE.getFragment()).setCurrentPlayer(mCurrentPlayer);


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
}
