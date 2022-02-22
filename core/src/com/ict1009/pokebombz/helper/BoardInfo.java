package com.ict1009.pokebombz.helper;

import java.util.ArrayList;
import java.util.UUID;

import com.ict1009.pokebombz.entity.Player;

public class BoardInfo {
    public static ArrayList<Player> players = new ArrayList<Player>();
    public static int[] playerScore = new int[4];
    public static int[] savedScore = {0, 0, 0, 0};
    public static ArrayList<UUID> explosionIDs = new ArrayList<UUID>();
}
