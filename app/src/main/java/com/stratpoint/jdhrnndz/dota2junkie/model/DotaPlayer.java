package com.stratpoint.jdhrnndz.dota2junkie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/18/16
 * Description: Used in DotaResponse class
 */
public class DotaPlayer implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.steamId);
        dest.writeInt(this.communityVisibilityState);
        dest.writeInt(this.profileState);
        dest.writeString(this.personaName);
        dest.writeLong(this.lastLogOff);
        dest.writeString(this.profileUrl);
        dest.writeString(this.avatar);
        dest.writeString(this.avatarMedium);
        dest.writeString(this.avatarFull);
        dest.writeInt(this.personaState);
        dest.writeString(this.realName);
        dest.writeString(this.primaryClanId);
        dest.writeLong(this.timeCreated);
        dest.writeInt(this.personaStateFlags);
        dest.writeString(this.countryCode);
        dest.writeString(this.stateCode);
        dest.writeLong(this.cityId);
    }

    public DotaPlayer() {
    }

    protected DotaPlayer(Parcel in) {
        this.steamId = in.readString();
        this.communityVisibilityState = (short) in.readInt();
        this.profileState = (short) in.readInt();
        this.personaName = in.readString();
        this.lastLogOff = in.readLong();
        this.profileUrl = in.readString();
        this.avatar = in.readString();
        this.avatarMedium = in.readString();
        this.avatarFull = in.readString();
        this.personaState = (short) in.readInt();
        this.realName = in.readString();
        this.primaryClanId = in.readString();
        this.timeCreated = in.readLong();
        this.personaStateFlags = (short) in.readInt();
        this.countryCode = in.readString();
        this.stateCode = in.readString();
        this.cityId = in.readLong();
    }

    public static final Parcelable.Creator<DotaPlayer> CREATOR = new Parcelable.Creator<DotaPlayer>() {
        @Override
        public DotaPlayer createFromParcel(Parcel source) {
            return new DotaPlayer(source);
        }

        @Override
        public DotaPlayer[] newArray(int size) {
            return new DotaPlayer[size];
        }
    };
}
