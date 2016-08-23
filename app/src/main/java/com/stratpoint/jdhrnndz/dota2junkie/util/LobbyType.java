package com.stratpoint.jdhrnndz.dota2junkie.util;

/**
 * Author: John Denielle F. Hernandez
 * Date: 8/23/16
 * Description: Enum used to obtain string counterpart of lobby type id.
 */
public enum LobbyType {
    INVALID(-1, "Invalid"),
    PUBLIC_MM(0, "Public Matchmaking"),
    PRACTICE(1, "Practice"),
    TOURNAMENT(2, "Tournament"),
    TUTORIAL(3, "Tutorial"),
    COOP_AI(4, "Co-op with bots"),
    TEAM_MATCH(5, "Team Match"),
    SOLO_QUEUE(6, "Solo Queue"),
    RANKED(7, "Ranked"),
    ONEVONE_MID(8, "1v1 Mid");

    private int value;
    private String name;

    LobbyType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String nameOf(int value) {
        for (LobbyType lobbyType : LobbyType.values())
            if (lobbyType.value == value)
                return lobbyType.name;
        return null;
    }
}
