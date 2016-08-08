package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/20/16.
 * Description: Base fragment for the fragments in the tablayout view pager
 */
public class BaseFragment extends Fragment {
    public static final String ARG_LAYOUT = "ARG_LAYOUT";

    protected int mLayout;

    public static Bundle initBundle(int layout) {
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT, layout);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = getArguments().getInt(ARG_LAYOUT);
    }
}
