package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in PlayerSummary class
 */
public class DotaResponse {
    @SerializedName("players")
    private DotaPlayer[] players;

    public DotaPlayer[] getPlayers() {
        return players;
    }
}
