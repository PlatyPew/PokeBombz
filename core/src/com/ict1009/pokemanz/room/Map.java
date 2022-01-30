package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.helper.GameInfo;

public abstract class Map {
    final private Texture texture;
    private int[][] unbreakable;
    private int[][] breakable;
    private Obstacle[][] obstacleMap = new Obstacle[GameInfo.MAP_WIDTH][GameInfo.MAP_HEIGHT];
    private Bomb[][] bombMap = new Bomb[GameInfo.MAP_WIDTH][GameInfo.MAP_HEIGHT];

    public Map(String textureLocation, int[][] unbreakable, int[][] breakable) {
        this.texture = new Texture(textureLocation);
        this.unbreakable = unbreakable;
        this.breakable = breakable;
    }

    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Creates unbreakable obstacles using 2d array
     *
     * @param world: World
     */
    private void createUnbreakable(World world) {
        for (int i = 0; i < unbreakable.length; i++) {
            int gridX = unbreakable[i][0];
            int gridY = unbreakable[i][1];
            obstacleMap[gridX][gridY] = new Obstacle(world, "room/unbreakable.png", gridX, gridY);
        }
    }

    /**
     * Creates breakable obstacles using 2d array
     *
     * @param world: World
     */
    private void createBreakable(World world) {
        for (int i = 0; i < breakable.length; i++) {
            int gridX = breakable[i][0];
            int gridY = breakable[i][1];
            obstacleMap[gridX][gridY] =
                new Obstacle(world, "room/breakable.png", gridX, gridY, true);
        }
    }

    /**
     * Creates both unbreakable and breakable obstacles
     *
     * @param world: World
     */
    public void createObstacles(World world) {
        createBreakable(world);
        createUnbreakable(world);
    }

    public Obstacle[][] getObstacleMap() {
        return obstacleMap;
    }

    public void setObstacleMap(int gridX, int gridY, Obstacle obstacle) {
        obstacleMap[gridX][gridY] = obstacle;
    }

    public Bomb[][] getBombMap() {
        return bombMap;
    }

    public void setBombMap(int gridX, int gridY, Bomb bomb) {
        bombMap[gridX][gridY] = bomb;
    }

    /**
     * Provides updates to the obstacle class
     *
     * @param delta: 1/fps
     */
    public void update(float delta) {
        for (Obstacle[] x : obstacleMap) {
            for (Obstacle y : x) {
                if (y instanceof Obstacle) {
                    y.update(delta);
                }
            }
        }
    }

    /**
     * Renders the obstacles
     *
     * @param batch: SpriteBatch
     */
    public void render(SpriteBatch batch) {
        for (Obstacle[] x : obstacleMap) {
            for (Obstacle y : x) {
                if (y instanceof Obstacle) {
                    y.render(batch);
                }
            }
        }
    }
}
