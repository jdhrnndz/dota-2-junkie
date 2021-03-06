package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/9/16
 * Description: The class used in GsonRequest to convert SharedPreference string into a Java Object.
 */
public class HeroReference {
    @SerializedName("heroes")
    private Hero[] heroes;

    public Hero[] getHeroes() {
        return heroes;
    }

    public Hero getHero(int id) {
        for(Hero h : heroes) {
            if(h.getId() == id) return h;
        }

        return null;
    }
}
