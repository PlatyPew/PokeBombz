package com.ict1009.pokemanz.helper;

public class GameInfo {
    final public static int WIDTH = 1280;
    final public static int HEIGHT = 720;
    final public static int MAP_WIDTH = 16;
    final public static int MAP_HEIGHT = 10;
    final public static int FPS = 60;
    final public static int PPM = 60;

    public static int SUDDEN_DEATH = FPS * 120;
    public static int SUDDEN_DEATH_DROP = FPS / 2;
    public static int ITEM_SPAWN_CHANCE = 40; // In percent

    public static float PLAYER_VELOCITY = 2.5f;
    public static int PLAYER_BOMBS = 1;
    public static int PLAYER_BOMB_RANGE = 2;

    public static int MAX_PLAYER_SPEED = 5;
    public static int MAX_PLAYER_BOMBS = 5;
    public static int MAX_PLAYER_BOMB_RANGE = 5;

    public static int timeElapsed = 0;
}
