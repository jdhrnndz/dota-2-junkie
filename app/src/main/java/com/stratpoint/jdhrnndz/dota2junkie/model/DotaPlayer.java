package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in DotaResponse class
 */
public class DotaPlayer {
    @SerializedName("steamid")
    private String steamId;

    @SerializedName("communityvisibilitystate")
    private short communityVisibilityState;

    @SerializedName("profilestate")
    private short profileState;

    @SerializedName("personaname")
    private String personaName;

    @SerializedName("lastlogoff")
    private long lastLogOff;

    @SerializedName("profileurl")
    private String profileUrl;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("avatarmedium")
    private String avatarMedium;

    @SerializedName("avatarfull")
    private String avatarFull;

    @SerializedName("personastate")
    private short personaState;

    @SerializedName("realname")
    private String realName;

    @SerializedName("primaryclanid")
    private String primaryClanId;

    @SerializedName("timecreated")
    private long timeCreated;

    @SerializedName("personastateflags")
    private short personaStateFlags;

    @SerializedName("loccountrycode")
    private String countryCode;

    @SerializedName("locstatecode")
    private String stateCode;

    @SerializedName("loccityid")
    private long cityId;

    public String getSteamId() {
        return steamId;
    }

    public short getCommunityVisibilityState() {
        return communityVisibilityState;
    }

    public short getProfileState() {
        return profileState;
    }

    public String getPersonaName() {
        return personaName;
    }

    public long getLastLogOff() {
        return lastLogOff;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarMedium() {
        return avatarMedium;
    }

    public String getAvatarFull() {
        return avatarFull;
    }

    public short getPersonaState() {
        return personaState;
    }

    public String getRealName() {
        return realName;
    }

    public String getPrimaryClanId() {
        return primaryClanId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public short getPersonaStateFlags() {
        return personaStateFlags;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public long getCityId() {
        return cityId;
    }
}
