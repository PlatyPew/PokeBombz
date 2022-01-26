package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

public abstract class Map {
    final private Texture texture;
    private Obstacle obstacle;
    private int[][] unbreakable;
    private int[][] breakable;

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

    /**
     * Creates unbreakable obstacles using 2d array
     *
     * @param world: World
     */
    private void createUnbreakable(World world) {
        for (int i = 0; i < unbreakable.length; i++) {
            this.obstacle =
                new Obstacle(world, "room/unbreakable.png", unbreakable[i][0], unbreakable[i][1]);
            this.obstaclesUnbreakable.add(this.obstacle);
        }
    }

    /**
     * Creates breakable obstacles using 2d array
     *
     * @param world: World
     */
    private void createBreakable(World world) {
        for (int i = 0; i < breakable.length; i++) {
            this.obstacle =
                new Obstacle(world, "room/breakable.png", breakable[i][0], breakable[i][1], true);
            this.obstaclesBreakable.add(this.obstacle);
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
