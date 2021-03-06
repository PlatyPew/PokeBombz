package com.ict1009.pokebombz.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.ict1009.pokebombz.helper.GameInfo;

public class LevelTwo extends Map {
    final private static String textureLocation = "room/background2.png";

    private Music gameMusic;
    // Coordinates of unbreakable obstacles
    final private static int[][] unbreakable = {
        {1, 1},  {1, 2},  {1, 4},  {1, 5},  {1, 7},  {1, 8},  {2, 1},  {2, 2},  {2, 4},
        {2, 5},  {2, 7},  {2, 8},  {3, 1},  {3, 2},  {3, 4},  {3, 5},  {3, 7},  {3, 8},
        {4, 1},  {4, 2},  {4, 4},  {4, 5},  {4, 7},  {4, 8},  {6, 1},  {6, 2},  {6, 4},
        {6, 5},  {6, 7},  {6, 8},  {7, 1},  {7, 2},  {7, 4},  {7, 5},  {7, 7},  {7, 8},
        {8, 1},  {8, 2},  {8, 4},  {8, 5},  {8, 7},  {8, 8},  {9, 1},  {9, 2},  {9, 4},
        {9, 5},  {9, 7},  {9, 8},  {11, 1}, {11, 2}, {11, 4}, {11, 5}, {11, 7}, {11, 8},
        {12, 1}, {12, 2}, {12, 4}, {12, 5}, {12, 7}, {12, 8}, {13, 1}, {13, 2}, {13, 4},
        {13, 5}, {13, 7}, {13, 8}, {14, 1}, {14, 2}, {14, 4}, {14, 5}, {14, 7}, {14, 8},
    };

    // Coordinates of breakable obstacles
    final private static int[][] breakable = {
        {0, 3},  {0, 4},  {0, 5},  {0, 6},  {1, 3},  {1, 6},  {2, 3},  {2, 6},  {3, 0},  {3, 3},
        {3, 6},  {3, 9},  {4, 0},  {4, 3},  {4, 6},  {4, 9},  {5, 0},  {5, 1},  {5, 2},  {5, 3},
        {5, 4},  {5, 5},  {5, 6},  {5, 7},  {5, 8},  {5, 9},  {6, 0},  {6, 3},  {6, 6},  {6, 9},
        {7, 0},  {7, 3},  {7, 6},  {7, 9},  {8, 0},  {8, 3},  {8, 6},  {8, 9},  {9, 0},  {9, 3},
        {9, 6},  {9, 9},  {10, 0}, {10, 1}, {10, 2}, {10, 3}, {10, 4}, {10, 5}, {10, 6}, {10, 7},
        {10, 8}, {10, 9}, {11, 0}, {11, 3}, {11, 6}, {11, 9}, {12, 0}, {12, 3}, {12, 6}, {12, 9},
        {13, 3}, {13, 6}, {14, 3}, {14, 6}, {15, 3}, {15, 4}, {15, 5}, {15, 6},
    };

    public LevelTwo() {
        super(textureLocation, unbreakable, breakable);
    }
    @Override
    public void setGameMusic() {
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_game5.ogg"));
        gameMusic.setLooping(true);
        gameMusic.play();
        GameInfo.currentMusic = gameMusic;
    }

    public Music getGameMusic() {
        return gameMusic;
    }
}
