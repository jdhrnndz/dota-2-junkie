package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date:  8/18/16
 * Description: Used in MatchDetails and Result classes
 */
public class Match {
    @SerializedName("match_id")
    private long id;

    @SerializedName("match_seq_num")
    private long sequenceNumber;

    @SerializedName("start_time")
    private long startTime;

    @SerializedName("lobby_type")
    private int lobbyType;

    @SerializedName("radiant_team_id")
    private long radiantTeamId;

    @SerializedName("dire_team_id")
    private long direTeamId;

    @SerializedName("players")
    private MatchPlayer[] players;

    @SerializedName("radiant_win")
    private boolean didRadiantWin;

    @SerializedName("duration")
    private int duration;

    @SerializedName("pre_game_duration")
    private int preGameDuration;

    @SerializedName("tower_status_radiant")
    private int radiantTowerStatus;

    @SerializedName("tower_status_dire")
    private int direTowerStatus;

    @SerializedName("barracks_status_radiant")
    private int radiantBarracksStatus;

    @SerializedName("barracks_status_dire")
    private int direBarracksStatus;

    @SerializedName("cluster")
    private int cluster;

    @SerializedName("first_blood_time")
    private int firstBloodTime;

    @SerializedName("human_players")
    private int humanPlayerCount;

    @SerializedName("leagueid")
    private int leagueId;

    @SerializedName("positive_votes")
    private int positiveVotes;

    @SerializedName("negative_votes")
    private short negativeVotes;

    @SerializedName("game_mode")
    private short gameMode;

    @SerializedName("flags")
    private int flags;

    @SerializedName("engine")
    private int engine;

    @SerializedName("radiant_score")
    private int radiantScore;

    @SerializedName("dire_score")
    private int direScore;

    public long getId() {
        return id;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getLobbyType() {
        return lobbyType;
    }

    public long getRadiantTeamId() {
        return radiantTeamId;
    }

    public long getDireTeamId() {
        return direTeamId;
    }

    public MatchPlayer[] getPlayers() {
        return players;
    }

    public boolean didRadiantWin() {
        return didRadiantWin;
    }

    public int getDuration() {
        return duration;
    }

    public int getPreGameDuration() {
        return preGameDuration;
    }

    public int getRadiantTowerStatus() {
        return radiantTowerStatus;
    }

    public int getDireTowerStatus() {
        return direTowerStatus;
    }

    public int getRadiantBarracksStatus() {
        return radiantBarracksStatus;
    }

    public int getDireBarracksStatus() {
        return direBarracksStatus;
    }

    public int getCluster() {
        return cluster;
    }

    public int getFirstBloodTime() {
        return firstBloodTime;
    }

    public int getHumanPlayerCount() {
        return humanPlayerCount;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public int getPositiveVotes() {
        return positiveVotes;
    }

    public short getNegativeVotes() {
        return negativeVotes;
    }

    public short getGameMode() {
        return gameMode;
    }

    public int getFlags() {
        return flags;
    }

    public int getEngine() {
        return engine;
    }

    public int getRadiantScore() {
        return radiantScore;
    }

    public int getDireScore() {
        return direScore;
    }
}
