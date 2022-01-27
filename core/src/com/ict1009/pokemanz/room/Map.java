package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;
import java.util.ArrayList;

public abstract class Map {
    final private Texture texture;
    private Obstacle obstacle;
    private int[][] unbreakable;
    private int[][] breakable;
    private int[][] map = new int[GameInfo.MAP_WIDTH][GameInfo.MAP_HEIGHT];

    private ArrayList<Obstacle> obstaclesUnbreakable = new ArrayList<Obstacle>();
    private ArrayList<Obstacle> obstaclesBreakable = new ArrayList<Obstacle>();

    public Map(String textureLocation, int[][] unbreakable, int[][] breakable) {
        this.texture = new Texture(textureLocation);
        this.unbreakable = unbreakable;
        this.breakable = breakable;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setMap(int gridX, int gridY, int state) {
        map[gridX][gridY] = state;
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
            this.obstacle = new Obstacle(world, "room/unbreakable.png", gridX, gridY);
            this.obstaclesUnbreakable.add(this.obstacle);

            map[gridX][gridY] = 2;
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
            this.obstacle = new Obstacle(world, "room/breakable.png", gridX, gridY, true);
            this.obstaclesBreakable.add(this.obstacle);

            map[gridX][gridY] = 1;
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

    /**
     * Provides updates to the obstacle class
     *
     * @param delta: 1/fps
     */
    public void update(float delta) {
        for (int i = 0; i < obstaclesUnbreakable.size(); i++) {
            obstaclesUnbreakable.get(i).update(delta);
        }

        for (int i = 0; i < obstaclesBreakable.size(); i++) {
            obstaclesBreakable.get(i).update(delta);
        }
    }

    /**
     * Renders the obstacles
     *
     * @param batch: SpriteBatch
     */
    public void render(SpriteBatch batch) {
        for (int i = 0; i < obstaclesUnbreakable.size(); i++) {
            obstaclesUnbreakable.get(i).render(batch);
        }

        for (int i = 0; i < obstaclesBreakable.size(); i++) {
            obstaclesBreakable.get(i).render(batch);
        }
    }
}
