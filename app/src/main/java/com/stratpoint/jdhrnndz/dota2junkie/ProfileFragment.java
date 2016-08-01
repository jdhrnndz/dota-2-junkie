package com.stratpoint.jdhrnndz.dota2junkie;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Created by johndeniellehernandez on 7/21/16.
 */
public class ProfileFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_profile;
    private LineChart mMatchesChart;
    private AppCompatButton mMemberSinceSigil, mSteamIdSigil, mLastLogOffSigil;

    public static ProfileFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);
        mMemberSinceSigil = (AppCompatButton) view.findViewById(R.id.sigil_member_since);
        mSteamIdSigil = (AppCompatButton) view.findViewById(R.id.sigil_steam_id);
        mLastLogOffSigil = (AppCompatButton) view.findViewById(R.id.sigil_last_log_off);


        Typeface fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fontAwesome.ttf");
        mMemberSinceSigil.setTypeface(fontAwesome);
        mSteamIdSigil.setTypeface(fontAwesome);
        mLastLogOffSigil.setTypeface(fontAwesome);

        mMatchesChart = (LineChart) view.findViewById(R.id.matches_chart);
        setData(20, 20f);

        return view;
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mMatchesChart.getData() != null &&
                mMatchesChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mMatchesChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mMatchesChart.getData().notifyDataChanged();
            mMatchesChart.setDrawGridBackground(false);
            mMatchesChart.setDrawBorders(false);
            mMatchesChart.setDescription("Recent 20 Matches Results");
            mMatchesChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            // set the line to be drawn like this "- - - - - -"
            set1.setColor(ContextCompat.getColor(getContext(), R.color.primary));
            set1.setCircleColor(ContextCompat.getColor(getContext(), R.color.primary_dark));
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setDrawFilled(true);
            set1.setFillColor(ContextCompat.getColor(getContext(), R.color.primary));
            set1.setLabel("");

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mMatchesChart.setData(data);
        }
    }
}
