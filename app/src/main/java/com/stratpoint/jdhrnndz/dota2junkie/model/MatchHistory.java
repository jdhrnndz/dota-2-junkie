package com.stratpoint.jdhrnndz.dota2junkie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/3/16.
 * Description: The class used in GsonRequest to convert response into a Java Object.
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

    public static class AbilityUpgrade {
        @SerializedName("ability")
        private int ability;

        @SerializedName("time")
        private int time;

        @SerializedName("level")
        private int level;

        public int getAbility() {
            return ability;
        }

        public int getTime() {
            return time;
        }

        public int getLevel() {
            return level;
        }
    }
}

