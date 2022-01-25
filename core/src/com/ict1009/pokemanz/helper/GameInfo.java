package com.ict1009.pokemanz.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameInfo {
    final public static int WIDTH = 1280;
    final public static int HEIGHT = 720;
    final public static float RATIO_WIDTH = 16f;
    final public static float RATIO_HEIGHT = 9f;
    final public static int FPS = 60;
    final public static int PPM = 60;
    final public static int PLAYER_VELOCITY = 5;
    public static World world = new World(new Vector2(0, 0), true);
}
