package com.ict1009.pokemanz.room;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.room.Obstacle;

public class Room {
    final private Texture texture;
    private Obstacle obstacle;
    private int [][] level1 = {
    		{1,1},{1,10},{2,5},{2,7},{2,6},{3,15},{3,10},{3,5},
    		{5,6},{7,10},{9,1},{5,10},{7,1},{16,10}
    };
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    public Room(String textureLocation) {
        this.texture = new Texture(textureLocation);
    }

    public Texture getTexture() {
        return this.texture;
    }
    public void makeRoom(World world) {
    	for(int i=0; i<level1.length; i++) {
    		this.obstacle = new Obstacle(world, "room/unbreakable.png",
    				level1[i][0], level1[i][1]);
        	this.obstacles.add(this.obstacle);
    	}
    	
    }
    public void render(SpriteBatch batch) {
    	for (int i = 0; i < obstacles.size(); i++) {
    	      obstacles.get(i).render(batch);
    	    }
    }
    
}
