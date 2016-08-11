package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.support.v4.app.Fragment;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/3/16.
 * Description: Enums used to store and provide access to fragments.
 */
public enum TabFragment {
    PROFILE ("Profile", ProfileFragment.newInstance(), 0),
    MATCHES ("Matches", MatchesFragment.newInstance(), 1),
    PLAY_STYLE ("Play Style", PlayStyleFragment.newInstance(), 2);

    private String title;
    private Fragment fragment;
    private int position;

    TabFragment(String title, Fragment fragment, int position) {
        this.title = title;
        this.fragment = fragment;
        this.position = position;
    }

    public String getTitle() {
        return this.title;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    public int getPosition() {
        return this.position;
    }

    public static String getTitle(int position) {
        String mTitle = "";

        for (TabFragment tf : TabFragment.values()) {
            if (tf.getPosition() == position) {
                mTitle = tf.getTitle();
                break;
            }
        }

        return mTitle;
    }

    public static Fragment getFragment(int position) {
        Fragment mFragment = null;

        for (TabFragment tf : TabFragment.values()) {
            if (tf.getPosition() == position) {
                mFragment = tf.getFragment();
                break;
            }
        }

        return mFragment;
    }
}