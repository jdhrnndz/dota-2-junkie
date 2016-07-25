package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;

/**
 * Created by johndeniellehernandez on 7/21/16.
 */
public class PlayStyleFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_play_style;

    public static PlayStyleFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        PlayStyleFragment fragment = new PlayStyleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);

        return view;
    }
}
