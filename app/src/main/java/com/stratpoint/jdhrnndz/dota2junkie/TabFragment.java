package com.stratpoint.jdhrnndz.dota2junkie;

import android.support.v4.app.Fragment;

/**
 * Created by johndeniellehernandez on 8/3/16.
 */
public enum TabFragment {
    PROFILE ("Profile", ProfileFragment.newInstance()),
    MATCHES ("Matches", MatchesFragment.newInstance()),
    PLAY_STYLE ("Play Style", PlayStyleFragment.newInstance());

    private String title;
    private Fragment fragment;

    TabFragment(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return this.title;
    }

    public Fragment getFragment() {
        return this.fragment;
    }
}