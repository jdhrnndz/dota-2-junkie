package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.support.v4.app.Fragment;

import com.stratpoint.jdhrnndz.dota2junkie.fragment.MatchesFragment;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.PlayStyleFragment;
import com.stratpoint.jdhrnndz.dota2junkie.fragment.ProfileFragment;

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