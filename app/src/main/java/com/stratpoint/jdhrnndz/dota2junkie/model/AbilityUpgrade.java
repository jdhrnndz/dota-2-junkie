package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in MatchPlayer class
 */
public class AbilityUpgrade {
    @SerializedName("ability")
    private int ability;

    @SerializedName("time")
    private int time;

    @SerializedName("level")
    private int level;

    public int getAbility() {
        return ability;
    }

    public int getTime() {
        return time;
    }

    public int getLevel() {
        return level;
    }
}
