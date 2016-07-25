package com.stratpoint.jdhrnndz.dota2junkie;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by johndeniellehernandez on 7/20/16.
 */
public class AppFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"Profile", "Matches", "Play Style"};
    private Context context;

    public AppFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mFragment = null;

        switch(position) {
            case 0: // Profile Fragment
                mFragment = ProfileFragment.newInstance();
                break;
            case 1: // Matches Fragment
                mFragment = MatchesFragment.newInstance();
                break;
            case 2: // Play Style Fragment
                mFragment = PlayStyleFragment.newInstance();
                break;
        }
        return mFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
