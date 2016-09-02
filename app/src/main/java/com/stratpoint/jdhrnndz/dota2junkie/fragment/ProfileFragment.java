package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.model.DotaPlayer;
import com.stratpoint.jdhrnndz.dota2junkie.model.Match;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchPlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/21/16
 * Description: This class uses the fragment_profile layout to create the view for the Profile Tab.
 * Inside the class is where the data are linked to the respective views.
 */
public class ProfileFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_profile;

    @BindView(R.id.user_avatar) ImageView mUserAvatar;
    @BindViews({ R.id.symbol_member_since, R.id.symbol_steam_id, R.id.symbol_last_log_off })
    List<AppCompatButton> mSymbols;
    @BindView(R.id.user_persona_name) TextView mUserPersonaName;
    @BindView(R.id.user_real_name) TextView mUserRealName;
    @BindView(R.id.user_country) TextView mUserCountry;
    @BindView(R.id.user_member_since) TextView mUserMemberSince;
    @BindView(R.id.user_steam_id) TextView mUserSteamId;
    @BindView(R.id.user_last_log_off) TextView mUserLastLogOff;
    @BindView(R.id.matches_chart) LineChart mMatchesChart;
    @BindView(R.id.matches_chart_progress_bar) ProgressBar mMatchesChartProgressBar;

    @BindString(R.string.profile_pattern_date) String mDatePattern;
    @BindString(R.string.profile_description_matches_graph) String mMatchesChartDescription;

    @BindColor(R.color.primary) int mPrimaryColor;
    @BindColor(R.color.primary_dark) int mPrimaryDarkColor;
    @BindColor(R.color.accent_dark) int mAccentDarkColor;

    List<Match> mMatches;
    LineData mProfileGraphData;
    DotaPlayer mCurrentPlayer;
    private int mMatchesChartProgress = 0;

    public static ProfileFragment newInstance() {
        Bundle args = initBundle(LAYOUT);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * TODO [Optional] Use Icepick for less boilerplate, type-safe state save/restore
     * @param savedInstanceState
     */
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        mCurrentPlayer = savedInstanceState.getParcelable("CURRENT_PLAYER");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("CURRENT_PLAYER", mCurrentPlayer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);
        ButterKnife.bind(this, view);

        // Assign values to views from the user info passed via the intent
        populateViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mProfileGraphData != null) {
            activateChart();
        }
    }

    // Sets a custom typeface via ButterKnife's apply method
    static final ButterKnife.Setter<AppCompatButton, Typeface> TYPEFACE = new ButterKnife.Setter<AppCompatButton, Typeface>() {
        @Override public void set(@NonNull AppCompatButton view, Typeface value, int index) {
            view.setTypeface(value);
        }
    };

    private void populateViews() {
        mUserAvatar.setBackgroundResource(R.drawable.loading_gradient_red);
        final AnimationDrawable frameAnimation = (AnimationDrawable) mUserAvatar.getBackground();
        frameAnimation.start();

        Glide.with(getContext())
            .load(mCurrentPlayer.getAvatarFull())
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    mUserAvatar.setBackgroundColor(mAccentDarkColor);
                    frameAnimation.stop();
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mUserAvatar.setBackgroundColor(mPrimaryColor);
                    frameAnimation.stop();
                    return false;
                }
            })
            .into(mUserAvatar);

        mUserPersonaName.setText(mCurrentPlayer.getPersonaName());
        mUserRealName.setText(mCurrentPlayer.getRealName());
        mUserCountry.setText(mCurrentPlayer.getCountryCode());

        Typeface fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fontAwesome.ttf");
        ButterKnife.apply(mSymbols, TYPEFACE, fontAwesome);

        // Initialize Parser and Formatter objects
        SimpleDateFormat mDateParser = new SimpleDateFormat("s", Locale.ENGLISH);
        SimpleDateFormat mDateFormatter = new SimpleDateFormat(mDatePattern, Locale.ENGLISH);

        // Parses time created value and formats into the pattern below using the
        // SimpleDateFormat class
        Date dMemberSince = null;
        try{
            dMemberSince = mDateParser.parse(String.valueOf(mCurrentPlayer.getTimeCreated()));
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        mUserMemberSince.setText(mDateFormatter.format(dMemberSince));

        // Casting long to int to obtain the SteamID32 version
        int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
        mUserSteamId.setText(String.valueOf(steamId32));

        // Converts seconds representation of the time created into the same pattern with member
        // since value using the SimpleDateFormat class
        Date dLastLagOff = null;
        try{
            dLastLagOff = mDateParser.parse(String.valueOf(mCurrentPlayer.getLastLogOff()));
        }
        catch(ParseException pe) {
            pe.printStackTrace();
        }
        mUserLastLogOff.setText(mDateFormatter.format(dLastLagOff));

        // Configure Matches Chart - Touch Disabled, No Grids and Axes, No Left Axis Labels
        mMatchesChart.setTouchEnabled(false);
        mMatchesChart.setDescription(mMatchesChartDescription);
        mMatchesChart.setDescriptionColor(mPrimaryDarkColor);
        mMatchesChart.setVisibility(View.GONE);

        XAxis xAxis = mMatchesChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = mMatchesChart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = mMatchesChart.getAxisRight();
        rightAxis.setEnabled(false);
        leftAxis.setDrawGridLines(false);

        mMatchesChartProgressBar.setMax(20);
    }

    public void setCurrentPlayer(DotaPlayer player) {
        this.mCurrentPlayer = player;
    }

    public void incrementProgress() {
        mMatchesChartProgressBar.setProgress(++mMatchesChartProgress);
    }

    public void populateMatchResultsGraph(List<Match> matches, int matchCountForGraph) {
        mMatches = matches;
        List<Boolean> matchResults = parseMatchResultsOfPlayer(mMatches, matchCountForGraph);
        ArrayList<Entry> graphValues = generateDataSet(matchResults, matchCountForGraph);
        buildMatchResultsGraph(graphValues);
    }

    private List<Boolean> parseMatchResultsOfPlayer(List<Match> matches, int matchCountForGraph) {
        List<Boolean> matchResults = new ArrayList<>();

        for (int i=0; i<matchCountForGraph; i++) {
            Match currentMatch = matches.get(i);
            MatchPlayer[] players = currentMatch.getPlayers();
            MatchPlayer currentPlayer = null;

            // Gets the player in the match context, used for finding the player's slot
            int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
            for(MatchPlayer player: players) {
                if (steamId32 == player.getAccountId()) {
                    currentPlayer = player;
                }
            }

            // Identifies if the player won or not
            boolean didPlayerWin = false;
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

        matchResultDataSet.setColor(mPrimaryColor);
        matchResultDataSet.setCircleColor(mPrimaryDarkColor);
        matchResultDataSet.setLineWidth(1.5f);
        matchResultDataSet.setCircleRadius(1.7f);
        matchResultDataSet.setDrawCircleHole(false);
        matchResultDataSet.setDrawFilled(true);
        matchResultDataSet.setFillColor(mPrimaryColor);

        // Prevents node values to be displayed
        matchResultDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(matchResultDataSet); // Add the dataset

        // Create a data object with the datasets
        mProfileGraphData = new LineData(dataSets);

        activateChart();
    }

    private void activateChart() {
        mMatchesChart.setData(mProfileGraphData);
        mMatchesChart.notifyDataSetChanged();
        mMatchesChartProgressBar.setVisibility(View.GONE);
        mMatchesChart.setVisibility(View.VISIBLE);
        mMatchesChart.invalidate();
    }
}
