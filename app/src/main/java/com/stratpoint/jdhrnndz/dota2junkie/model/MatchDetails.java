package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by johndeniellehernandez on 8/5/16.
 */
public class MatchDetails {
    @SerializedName("result")
    private MatchHistory.Match matchDetails;

    public MatchHistory.Match getResult() {
        return matchDetails;
    }
}
