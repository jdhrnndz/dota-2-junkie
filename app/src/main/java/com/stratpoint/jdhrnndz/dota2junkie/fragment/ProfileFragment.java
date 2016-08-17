package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchHistory;
import com.stratpoint.jdhrnndz.dota2junkie.model.PlayerSummary;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.network.VolleySingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/21/16
 * Description: This class uses the fragment_profile layout to create the view for the Profile Tab.
 * Inside the class is where the data are linked to the respective views.
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
    private List<MatchHistory.Match> mMatches;
    private LineData mProfileGraphData;
    private PlayerSummary.DotaPlayer mCurrentPlayer;

    public static ProfileFragment newInstance() {
        Bundle args = initBundle(LAYOUT);
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

    @Override
    public void onResume() {
        super.onResume();

        if (mProfileGraphData != null) {
            // set data
            mMatchesChart.setData(mProfileGraphData);
            mMatchesChart.notifyDataSetChanged();
            mMatchesChart.invalidate();
        }
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

        mUserAvatar.setImageUrl(mCurrentPlayer.getAvatarFull(), imageLoader);
        mUserAvatar.setProgressBar(mUserAvatarProgressBar);
        mUserPersonaName.setText(mCurrentPlayer.getPersonaName());
        mUserRealName.setText(mCurrentPlayer.getRealName());
        mUserCountry.setText(mCurrentPlayer.getCountryCode());

        Typeface fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fontAwesome.ttf");
        mMemberSinceSigil.setTypeface(fontAwesome);
        mSteamIdSigil.setTypeface(fontAwesome);
        mLastLogOffSigil.setTypeface(fontAwesome);

        // Converts seconds representation of the time created into the pattern below using the
        // SimpleDateFormat class
        String datePattern = getResources().getString(R.string.profile_pattern_date);
        Date dMemberSince = null;
        try{
            dMemberSince = new SimpleDateFormat("s", Locale.ENGLISH)
                    .parse(String.valueOf(mCurrentPlayer.getTimeCreated()));
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        mUserMemberSince
                .setText(new SimpleDateFormat(datePattern, Locale.ENGLISH)
                .format(dMemberSince));

        // Casting long to int to obtain the SteamID32 version
        int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
        mUserSteamId.setText(String.valueOf(steamId32));

        // Converts seconds representation of the time created into the same pattern with member
        // since value using the SimpleDateFormat class
        Date dLastLagOff = null;
        try{
            dLastLagOff = new SimpleDateFormat("s", Locale.ENGLISH)
                    .parse(String.valueOf(mCurrentPlayer.getLastLogOff()));
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        mUserLastLogOff
                .setText(new SimpleDateFormat(datePattern, Locale.ENGLISH)
                .format(dLastLagOff));

        mMatchesChart
                .setTouchEnabled(false);
        mMatchesChart.setDescription(getResources()
                .getString(R.string.profile_description_matches_graph));
        mMatchesChart
                .setDescriptionColor(ContextCompat.getColor(getContext(), R.color.primary_dark));

        XAxis xAxis = mMatchesChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mMatchesChart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = mMatchesChart.getAxisRight();
        leftAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
    }

    public void setCurrentPlayer(PlayerSummary.DotaPlayer player) {
        this.mCurrentPlayer = player;
    }

    public void populateMatchResultsGraph(List<MatchHistory.Match> matches, int matchCountForGraph) {
        mMatches = matches;
        List<Boolean> matchResults = parseMatchResultsOfPlayer(mMatches, matchCountForGraph);
        ArrayList<Entry> graphValues = generateDataSet(matchResults, matchCountForGraph);
        buildMatchResultsGraph(graphValues);
    }

    private List<Boolean> parseMatchResultsOfPlayer(List<MatchHistory.Match> matches, int matchCountForGraph) {
        List<Boolean> matchResults = new ArrayList<>();

        for (int i=0; i<matchCountForGraph; i++) {
            MatchHistory.Match currentMatch = matches.get(i);
            MatchHistory.MatchPlayer[] players = currentMatch.getPlayers();
            MatchHistory.MatchPlayer currentPlayer = null;

            // Gets the player in the match context, used for finding the player's slot
            for(MatchHistory.MatchPlayer player: players) {
                int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
                if (steamId32 == player.getAccountId()) {
                    currentPlayer = player;
                }
            }

            boolean didPlayerWin = false;
            // Identifies if the player won or not
            if (currentPlayer != null) {
                int playerSlot = currentPlayer.getPlayerSlot();
                didPlayerWin = (currentMatch.didRadiantWin())?
                        playerSlot >> 7 == 0:
                        playerSlot >> 7 != 0;
            }

            matchResults.add(didPlayerWin);
        }

        return matchResults;
    }

    private ArrayList<Entry> generateDataSet(List<Boolean> matchResults, int matchCountForGraph) {
        ArrayList<Entry> values = new ArrayList<>();

        int cumulativeValue = matchCountForGraph;
        for (int i = 0; i < matchCountForGraph; i++) {
            float val = matchResults.get(i)?cumulativeValue--:cumulativeValue++;
            values.add(0, new Entry(matchCountForGraph-i, val));
        }

        return values;
    }

    private void buildMatchResultsGraph(ArrayList<Entry> values) {
        LineDataSet matchResultDataSet;

        // Create a dataset and give it a type
        matchResultDataSet = new LineDataSet(values, null);

        matchResultDataSet.setColor(ContextCompat.getColor(getContext(), R.color.primary));
        matchResultDataSet.setCircleColor(ContextCompat.getColor(getContext(), R.color.primary_dark));
        matchResultDataSet.setLineWidth(1.5f);
        matchResultDataSet.setCircleRadius(3f);
        matchResultDataSet.setDrawCircleHole(false);
        matchResultDataSet.setDrawFilled(true);
        matchResultDataSet.setFillColor(ContextCompat.getColor(getContext(), R.color.primary));

        // Prevents node values to be displayed
        matchResultDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(matchResultDataSet); // add the datasets

        // create a data object with the datasets
        mProfileGraphData = new LineData(dataSets);

        // set data
        mMatchesChart.setData(mProfileGraphData);
        mMatchesChart.notifyDataSetChanged();
        mMatchesChart.invalidate();
    }
}
