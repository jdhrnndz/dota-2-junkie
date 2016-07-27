package com.stratpoint.jdhrnndz.dota2junkie;

import android.graphics.Color;
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

    private RadarChart mPlayStyleChart;

    public static PlayStyleFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        PlayStyleFragment fragment = new PlayStyleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);
        mPlayStyleChart = (RadarChart) view.findViewById(R.id.play_style_chart);
        mPlayStyleChart.setRotationEnabled(false);
        mPlayStyleChart.setDescription("Recent 20 Matches");

        setData();

        return view;
    }

    public void setData() {
        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();

        entries1.add(new RadarEntry(0.15f, "FIGHTING"));
        entries1.add(new RadarEntry(0.65f, "FARMING"));
        entries1.add(new RadarEntry(0.60f, "SUPPORTING"));
        entries1.add(new RadarEntry(0.35f, "PUSHING"));
        entries1.add(new RadarEntry(0.70f, "VERSATILITY"));

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        mPlayStyleChart.setData(data);
        mPlayStyleChart.invalidate();
    }
}
