package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by johndeniellehernandez on 8/18/16.
 */
public class MatchPlayer {
    @SerializedName("account_id")
    private long accountId;

    @SerializedName("player_slot")
    private short playerSlot;

    @SerializedName("hero_id")
    private int heroId;

    public long getAccountId() {
        return accountId;
    }

    public short getPlayerSlot() {
        return playerSlot;
    }

    public int getHeroId() {
        return heroId;
    }

    @SerializedName("item_0")
    private int item0;

    @SerializedName("item_1")
    private int item1;

    @SerializedName("item_2")
    private int item2;

    @SerializedName("item_3")
    private int item3;

    @SerializedName("item_4")
    private int item4;

    @SerializedName("item_5")
    private int item5;

    @SerializedName("kills")
    private int kills;

    @SerializedName("deaths")
    private int deaths;

    @SerializedName("assists")
    private int assists;

    @SerializedName("leaver_status")
    private int leaverStatus;

    @SerializedName("last_hits")
    private int lastHits;

    @SerializedName("denies")
    private int denies;

    @SerializedName("gold_per_min")
    private int gpm;

    @SerializedName("xp_per_min")
    private int xpm;

    @SerializedName("level")
    private int level;

    @SerializedName("gold")
    private int gold;

    @SerializedName("gold_spent")
    private int goldSpent;

    @SerializedName("hero_damage")
    private int heroDamage;

    @SerializedName("tower_damage")
    private int towerDamage;

    @SerializedName("hero_healing")
    private int heroHealing;

    @SerializedName("ability_upgrades")
    private AbilityUpgrade[] abilityUpgrades;

    public int getItemAtIndex(int index) throws IllegalArgumentException{
        try {
            return getItems()[index];
        } catch (ArrayIndexOutOfBoundsException ie){
            throw new IllegalArgumentException();
        }
    }

    public int[] getItems() {
        return new int[]{item0, item1, item2, item3, item4, item5};
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public int getLeaverStatus() {
        return leaverStatus;
    }

    public int getLastHits() {
        return lastHits;
    }

    public int getDenies() {
        return denies;
    }

    public int getGpm() {
        return gpm;
    }

    public int getXpm() {
        return xpm;
    }

    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

    public int getGoldSpent() {
        return goldSpent;
    }

    public int getHeroDamage() {
        return heroDamage;
    }

    public int getTowerDamage() {
        return towerDamage;
    }

    public int getHeroHealing() {
        return heroHealing;
    }

    public AbilityUpgrade[] getAbilityUpgrades() {
        return abilityUpgrades;
    }
}
