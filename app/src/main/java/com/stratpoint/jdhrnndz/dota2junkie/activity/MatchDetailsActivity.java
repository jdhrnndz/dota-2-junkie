package com.stratpoint.jdhrnndz.dota2junkie.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.customview.Minimap;
import com.stratpoint.jdhrnndz.dota2junkie.model.Match;
import com.stratpoint.jdhrnndz.dota2junkie.util.LobbyType;

import butterknife.BindArray;
import butterknife.BindView;
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
    @BindView(R.id.minimap) Minimap minimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        ButterKnife.bind(this);

        Gson gson = new Gson();

        String matchDetailsString = getIntent().getStringExtra(MainActivity.EXTRA_MATCH_DETAILS);

        Match matchDetails = gson.fromJson(matchDetailsString, Match.class);

        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mWinner.setText(String.format("%s Victory", (matchDetails.didRadiantWin()?"Radiant":"Dire")));
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}