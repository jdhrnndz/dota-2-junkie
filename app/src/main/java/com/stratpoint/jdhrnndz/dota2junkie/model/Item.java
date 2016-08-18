package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in ItemReference class
 */
public class Item {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("cost")
    private int cost;

    @SerializedName("secret_shop")
    private short secretShop;

    @SerializedName("side_shop")
    private short sideShop;

    @SerializedName("recipe")
    private short recipe;

    @SerializedName("localized_name")
    private String localizedName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public short getSecretShop() {
        return secretShop;
    }

    public short getSideShop() {
        return sideShop;
    }

    public short getRecipe() {
        return recipe;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}
