package com.stratpoint.jdhrnndz.dota2junkie.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.customview.MinimapView;
import com.stratpoint.jdhrnndz.dota2junkie.model.Match;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchPlayer;
import com.stratpoint.jdhrnndz.dota2junkie.util.LobbyType;

import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/21/16
 * Description: Activity used for displaying match details from the Match RecyclerView
 */
public class MatchDetailsActivity extends AppCompatActivity {
    @BindView(R.id.match_details_toolbar) Toolbar mToolbar;
    @BindView(R.id.winner) TextView mWinner;
    @BindView(R.id.game_mode) TextView mGameMode;
    @BindView(R.id.lobby) TextView mLobby;
    @BindView(R.id.game_duration) TextView mDuration;
    @BindView(R.id.dire_score) TextView mDireScore;
    @BindView(R.id.radiant_score) TextView mRadiantScore;
    @BindArray(R.array.game_modes) String[] mGameModes;
    @BindView(R.id.minimap)
    MinimapView minimap;
    @BindViews({
            R.id.radiant_player_1, R.id.radiant_player_2, R.id.radiant_player_3, R.id.radiant_player_4, R.id.radiant_player_5,
            R.id.dire_player_1, R.id.dire_player_2, R.id.dire_player_3, R.id.dire_player_4, R.id.dire_player_5
    })
    List<LinearLayout> mPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        ButterKnife.bind(this);

        Gson gson = new Gson();

        String matchDetailsString = getIntent().getStringExtra(MainActivity.EXTRA_MATCH_DETAILS);

        Match matchDetails = gson.fromJson(matchDetailsString, Match.class);

        mToolbar.setTitle(getString(R.string.match_detail_title, (matchDetails.didRadiantWin()?"Radiant":"Dire")));
        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mGameMode.setText(mGameModes[matchDetails.getGameMode()]);
        mLobby.setText(LobbyType.nameOf(matchDetails.getLobbyType()));
        mDuration.setText(DateUtils.formatElapsedTime(matchDetails.getDuration()));
        mDireScore.setText(String.valueOf(matchDetails.getDireScore()));
        mRadiantScore.setText(String.valueOf(matchDetails.getRadiantScore()));
        minimap.setValues(
                matchDetails.getDireTowerStatus(),
                matchDetails.getDireBarracksStatus(),
                matchDetails.getRadiantTowerStatus(),
                matchDetails.getRadiantBarracksStatus(),
                matchDetails.didRadiantWin()
        );

        ButterKnife.apply(mPlayers, PLAYER_DETAILS, matchDetails.getPlayers());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    // Sets a custom typeface via ButterKnife's apply method
    final ButterKnife.Setter<LinearLayout, MatchPlayer[]> PLAYER_DETAILS = new ButterKnife.Setter<LinearLayout, MatchPlayer[]>() {
        @Override public void set(@NonNull LinearLayout view, MatchPlayer[] players, int index) {
            ((TextView) view.findViewById(R.id.hero_level)).setText(String.valueOf(players[index].getLevel()));
            ((TextView) view.findViewById(R.id.player_kills)).setText(String.valueOf(players[index].getKills()));
            ((TextView) view.findViewById(R.id.player_deaths)).setText(String.valueOf(players[index].getDeaths()));
            ((TextView) view.findViewById(R.id.player_assists)).setText(String.valueOf(players[index].getAssists()));


            Typeface fontAwesome = Typeface.createFromAsset(MatchDetailsActivity.this.getAssets(), "fontAwesome.ttf");
            ((AppCompatButton) view.findViewById(R.id.button_expand)).setTypeface(fontAwesome);
        }
    };
}