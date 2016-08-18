package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/9/16
 * Description: The class used in GsonRequest to convert SharedPreference string into a Java Object.
 */
public class ItemReference {
    @SerializedName("items")
    private Item[] items;

    public Item[] getItems() {
        return items;
    }

    public Item getItem(int id) {
        for(Item i : items) {
            if(i.getId() == id) return i;
        }

        return null;
    }
}
