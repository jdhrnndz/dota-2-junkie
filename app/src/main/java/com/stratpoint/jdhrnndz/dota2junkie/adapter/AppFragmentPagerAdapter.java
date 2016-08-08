package com.stratpoint.jdhrnndz.dota2junkie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stratpoint.jdhrnndz.dota2junkie.fragment.TabFragment;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/20/16.
 * Description: Adapter for the tablayout view pager. Utilizes TabFragment enum to access fragments.
 */
public class AppFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;

    public AppFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mFragment = null;

        switch(position) {
            case 0:
                mFragment = TabFragment.PROFILE.getFragment();
                break;
            case 1:
                mFragment = TabFragment.MATCHES.getFragment();
                break;
            case 2:
                mFragment = TabFragment.PLAY_STYLE.getFragment();
                break;
        }
        return mFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch(position) {
            case 0:
                title = TabFragment.PROFILE.getTitle();
                break;
            case 1:
                title = TabFragment.MATCHES.getTitle();
                break;
            case 2:
                title = TabFragment.PLAY_STYLE.getTitle();
                break;
        }

        return title;
    }
}
