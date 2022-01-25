package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;

public abstract class Map {
    final private Texture texture;
    private Obstacle obstacle;
    private int[][] obstaclesUnbreakable;

    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

    public Map(String textureLocation, int[][] obstaclesUnbreakable) {
        this.texture = new Texture(textureLocation);
        this.obstaclesUnbreakable = obstaclesUnbreakable;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void createObstacles(World world) {
        for (int i = 0; i < obstaclesUnbreakable.length; i++) {
            this.obstacle = new Obstacle(world, "room/unbreakable.png", obstaclesUnbreakable[i][0],
                                         obstaclesUnbreakable[i][1]);
            this.obstacles.add(this.obstacle);
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).render(batch);
        }
    }
}
