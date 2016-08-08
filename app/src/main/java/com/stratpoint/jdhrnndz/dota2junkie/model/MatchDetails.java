package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/5/16.
 * Description: The class used in GsonRequest to convert response into a Java Object.
 */
public class MatchDetails {
    @SerializedName("result")
    private MatchHistory.Match matchDetails;

    public MatchHistory.Match getResult() {
        return matchDetails;
    }
}
