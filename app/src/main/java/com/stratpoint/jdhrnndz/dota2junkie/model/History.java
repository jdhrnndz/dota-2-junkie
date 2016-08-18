package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in MatchHistory class
 */
public class History {
    @SerializedName("status")
    private int status;

    @SerializedName("num_results")
    private int retrievedMatchCount;

    @SerializedName("total_results")
    private int totalMatchCount;

    @SerializedName("results_remaining")
    private int remainingMatchCount;

    @SerializedName("matches")
    private Match[] matches;

    public int getStatus() {
        return status;
    }

    public int getRetrievedMatchCount() {
        return retrievedMatchCount;
    }

    public int getTotalMatchCount() {
        return totalMatchCount;
    }

    public int getRemainingMatchCount() {
        return remainingMatchCount;
    }

    public Match[] getMatches() {
        return matches;
    }
}
