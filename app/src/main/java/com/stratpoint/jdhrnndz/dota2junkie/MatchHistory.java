package com.stratpoint.jdhrnndz.dota2junkie;

/**
 * Created by johndeniellehernandez on 8/3/16.
 */
public class MatchHistory {
    private Result result;

    public Result getResult() {
        return result;
    }

    public static class Result {
        private int status;
        private int num_results;
        private int total_results;
        private int results_remaining;
        private Match[] matches;

        public int getStatus() {
            return status;
        }

        public int getNum_results() {
            return num_results;
        }

        public int getTotal_results() {
            return total_results;
        }

        public int getResults_remaining() {
            return results_remaining;
        }

        public Match[] getMatches() {
            return matches;
        }
    }

    public static class Match {
        private long match_id;
        private long match_seq_num;
        private long start_time;
        private int lobby_type;
        private long radiant_team_id;
        private long dire_team_id;
        private MatchPlayer[] players;

        public long getMatch_id() {
            return match_id;
        }

        public long getMatch_seq_num() {
            return match_seq_num;
        }

        public long getStart_time() {
            return start_time;
        }

        public int getLobby_type() {
            return lobby_type;
        }

        public long getRadiant_team_id() {
            return radiant_team_id;
        }

        public long getDire_team_id() {
            return dire_team_id;
        }

        public MatchPlayer[] getPlayers() {
            return players;
        }
    }

    public static class MatchPlayer {
        private long account_id;
        private short player_slot;
        private int hero_id;

        public long getAccount_id() {
            return account_id;
        }

        public short getPlayer_slot() {
            return player_slot;
        }

        public int getHero_id() {
            return hero_id;
        }
    }
}

