package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by johndeniellehernandez on 7/20/16.
 */
public class PageFragment extends Fragment {
    public static final String ARG_LAYOUT = "ARG_LAYOUT";

    private int mLayout;

    public static PageFragment newInstance(int layout) {
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT, layout);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = getArguments().getInt(ARG_LAYOUT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);
        return view;
    }
}
