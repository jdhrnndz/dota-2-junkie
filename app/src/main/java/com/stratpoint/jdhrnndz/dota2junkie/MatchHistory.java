package com.stratpoint.jdhrnndz.dota2junkie;

import com.google.gson.annotations.SerializedName;

/**
 * Created by johndeniellehernandez on 8/3/16.
 */
public class MatchHistory {
    @SerializedName("result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public static class Result {
        @SerializedName("status")
        private int status;

        @SerializedName("num_results")
        private int retrievedMatchCount;

        @SerializedName("total_results")
        private int totalMatchCount;

        @SerializedName("results_remaining")
        private int remainingMatchCount;

        @SerializedName("matches")
        private Match[] matches;

        public int getStatus() {
            return status;
        }

        public int getRetrievedMatchCount() {
            return retrievedMatchCount;
        }

        public int getTotalMatchCount() {
            return totalMatchCount;
        }

        public int getRemainingMatchCount() {
            return remainingMatchCount;
        }

        public Match[] getMatches() {
            return matches;
        }
    }

    public static class Match {
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
    }

    public static class MatchPlayer {
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
    }
}

