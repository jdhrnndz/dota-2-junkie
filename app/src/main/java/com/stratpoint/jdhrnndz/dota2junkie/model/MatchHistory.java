package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/3/16.
 * Description: The class used in GsonRequest to convert response into a Java Object.
 */
public class MatchHistory {
    @SerializedName("result")
    private History history;

    public History getHistory() {
        return history;
    }

}