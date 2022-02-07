package com.ict1009.pokemanz.helper;

import com.ict1009.pokemanz.entity.Player;
import java.util.ArrayList;
import java.util.UUID;

public class BoardInfo {
    public static ArrayList<Player> players = new ArrayList<Player>();
    public static int[] playerScore = new int[4];

    public static ArrayList<UUID> explosionIDs = new ArrayList<UUID>();
}
