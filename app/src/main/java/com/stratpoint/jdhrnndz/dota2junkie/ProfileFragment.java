package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by johndeniellehernandez on 7/21/16.
 */
public class ProfileFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_profile;

    public static ProfileFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);

        return view;
    }
}
