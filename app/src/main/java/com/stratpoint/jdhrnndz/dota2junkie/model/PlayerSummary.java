package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/2/16.
 * Description: The class used in MainActivity to convert user data from intent into a Java Object.
 */
public class PlayerSummary {
    @SerializedName("response")
    private DotaResponse response;

    public DotaResponse getResponse() {
        return response;
    }
}

