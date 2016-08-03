package com.stratpoint.jdhrnndz.dota2junkie;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by johndeniellehernandez on 7/21/16.
 */
public class ProfileFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_profile;
    private NetworkImageView mUserAvatar;
    private ProgressBar mUserAvatarProgressBar;
    private AppCompatButton mMemberSinceSigil, mSteamIdSigil, mLastLogOffSigil;
    private TextView
        mUserPersonaName, mUserRealName, mUserCountry,
        mUserMemberSince, mUserSteamId, mUserLastLogOff;
    private LineChart mMatchesChart;

    private DotaPlayer mCurrentPlayer;

    public static ProfileFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);

        // Map views from content view as the activity's attributes
        assignViews(view);

        // Assign values to views from the user info passed via the intent
        populateViews();

        return view;
    }

    private void assignViews(View view) {
        mUserAvatar = (NetworkImageView) view.findViewById(R.id.user_avatar);
        mUserAvatarProgressBar = (ProgressBar) view.findViewById(R.id.user_avatar_progress_bar);

        mUserPersonaName = (TextView) view.findViewById(R.id.user_persona_name);
        mUserRealName = (TextView) view.findViewById(R.id.user_real_name);
        mUserCountry = (TextView) view.findViewById(R.id.user_country);

        mMemberSinceSigil = (AppCompatButton) view.findViewById(R.id.sigil_member_since);
        mUserMemberSince = (TextView) view.findViewById(R.id.user_member_since);
        mSteamIdSigil = (AppCompatButton) view.findViewById(R.id.sigil_steam_id);
        mUserSteamId = (TextView) view.findViewById(R.id.user_steam_id);
        mLastLogOffSigil = (AppCompatButton) view.findViewById(R.id.sigil_last_log_off);
        mUserLastLogOff = (TextView) view.findViewById(R.id.user_last_log_off);

        mMatchesChart = (LineChart) view.findViewById(R.id.matches_chart);
    }

    private void populateViews() {

        ImageLoader imageLoader = VolleySingleton.getInstance(getActivity()).getImageLoader();

        mUserAvatar.setImageUrl(mCurrentPlayer.getAvatarfull(), imageLoader);
        mUserAvatar.setProgressBar(mUserAvatarProgressBar);
        mUserPersonaName.setText(mCurrentPlayer.getPersonaname());
        mUserRealName.setText(mCurrentPlayer.getRealname());
        mUserCountry.setText(mCurrentPlayer.getLoccountrycode());

        Typeface fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fontAwesome.ttf");
        mMemberSinceSigil.setTypeface(fontAwesome);
        mSteamIdSigil.setTypeface(fontAwesome);
        mLastLogOffSigil.setTypeface(fontAwesome);

        String datePattern = getResources().getString(R.string.profile_pattern_date);
        Date dMemberSince = null;
        try{
            dMemberSince = new SimpleDateFormat("s").parse(String.valueOf(mCurrentPlayer.getTimecreated()));
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        mUserMemberSince.setText(new SimpleDateFormat(datePattern).format(dMemberSince));

        // Casting long to int to obtain the SteamID32 version
        int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamid());
        mUserSteamId.setText(String.valueOf(steamId32));

        Date dLastLagOff = null;
        try{
            dLastLagOff = new SimpleDateFormat("s").parse(String.valueOf(mCurrentPlayer.getLastlogoff()));
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        mUserLastLogOff.setText(new SimpleDateFormat(datePattern).format(dLastLagOff));

        setMatchResultGraphData(20, 20f);
    }


    private void setMatchResultGraphData(int count, float range) {

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
            mMatchesChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setColor(ContextCompat.getColor(getContext(), R.color.primary));
            set1.setCircleColor(ContextCompat.getColor(getContext(), R.color.primary_dark));
            set1.setLineWidth(1.5f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setDrawFilled(true);
            set1.setFillColor(ContextCompat.getColor(getContext(), R.color.primary));

            // Prevents node values to be displayed
            set1.setDrawValues(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mMatchesChart.setData(data);
        }

        mMatchesChart.setTouchEnabled(false);
        mMatchesChart.setDescription(getResources().getString(R.string.profile_description_matches_graph));
        mMatchesChart.setDescriptionColor(ContextCompat.getColor(getContext(), R.color.primary_dark));

        XAxis xAxis = mMatchesChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mMatchesChart.getAxisLeft();

        YAxis rightAxis = mMatchesChart.getAxisRight();
        leftAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
    }

    public void setCurrentPlayer(DotaPlayer player) {
        this.mCurrentPlayer = player;
    }
}
