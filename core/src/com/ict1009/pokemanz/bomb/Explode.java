package com.ict1009.pokemanz.bomb;

import com.ict1009.pokemanz.room.Map;
import com.ict1009.pokemanz.room.Obstacle;

public class Explode {
    private Obstacle[][] obstacleMap;
    private Bomb[][] bombMap;

    private int gridX, gridY;
    private int range;

    public Explode(Map map, int gridX, int gridY, int range) {
        this.obstacleMap = map.getObstacleMap();
        this.bombMap = map.getBombMap();
        this.gridX = gridX;
        this.gridY = gridY;
        this.range = range;
    }
}
