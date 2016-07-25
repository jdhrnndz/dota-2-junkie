package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.util.ArrayList;

/**
 * Created by johndeniellehernandez on 7/20/16.
 */
public class BaseFragment extends Fragment {
    public static final String ARG_LAYOUT = "ARG_LAYOUT";

    protected int mLayout;

    public static Bundle initBundle(int layout) {
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT, layout);
//        args.putIntegerArrayList(ARG_DATAVIEWIDS, dataViewIds);
//        args.putStringArrayList(ARG_DATAVALUES, dataValues);
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = getArguments().getInt(ARG_LAYOUT);
//        mDataViewIds = getArguments().getIntegerArrayList(ARG_DATAVIEWIDS);
//        mDataValues = getArguments().getStringArrayList(ARG_DATAVALUES);
    }
}
