package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in HeroReference class
 */
public class Hero {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("localized_name")
    private String localizedName;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}
