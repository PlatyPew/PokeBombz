package com.ict1009.pokemanz.item;

import com.badlogic.gdx.physics.box2d.World;

public class Potion extends Item {
    final private int health;

    public Potion(World world, String textureLocation, float initialX, float initialY, int cost,
                  int health) {
        super(world, textureLocation, initialX, initialY, cost);
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }
}