package com.stratpoint.jdhrnndz.dota2junkie.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.model.Match;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/21/16
 * Description: Activity used for displaying match details from the Match RecyclerView
 */
public class MatchDetailsActivity extends AppCompatActivity {
    @BindView(R.id.match_details_string) TextView mMatchDetailsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);
        ButterKnife.bind(this);

        Gson gson = new Gson();

        String matchDetailsString = getIntent().getStringExtra(MainActivity.EXTRA_MATCH_DETAILS);

        mMatchDetailsString.setText(matchDetailsString);

        Match matchDetails = gson.fromJson(matchDetailsString, Match.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}