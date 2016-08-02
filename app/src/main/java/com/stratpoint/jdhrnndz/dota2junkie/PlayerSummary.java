package com.stratpoint.jdhrnndz.dota2junkie;

/**
 * Created by johndeniellehernandez on 8/2/16.
 */
public class PlayerSummary {
    private DotaResponse response;

    public DotaResponse getResponse() {
        return response;
    }
}

class DotaResponse {
    private DotaPlayer[] players;

    public DotaPlayer[] getPlayers() {
        return players;
    }
}

class DotaPlayer {
    private String steamid;
    private short communityvisibilitystate;
    private short profilestate;
    private String personaname;
    private long lastlogoff;
    private String profileurl;
    private String avatar;
    private String avatarmedium;
    private String avatarfull;
    private short personastate;
    private String realname;
    private String primaryclanid;
    private long timecreated;
    private short personastateflags;
    private String loccountrycode;
    private String locstatecode;
    private long loccityid;

    public String getSteamid() {
        return steamid;
    }

    public short getCommunityvisibilitystate() {
        return communityvisibilitystate;
    }

    public short getProfilestate() {
        return profilestate;
    }

    public String getPersonaname() {
        return personaname;
    }

    public long getLastlogoff() {
        return lastlogoff;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public short getPersonastate() {
        return personastate;
    }

    public String getRealname() {
        return realname;
    }

    public String getPrimaryclanid() {
        return primaryclanid;
    }

    public long getTimecreated() {
        return timecreated;
    }

    public short getPersonastateflags() {
        return personastateflags;
    }

    public String getLoccountrycode() {
        return loccountrycode;
    }

    public String getLocstatecode() {
        return locstatecode;
    }

    public long getLoccityid() {
        return loccityid;
    }
}