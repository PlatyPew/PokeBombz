package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Entity {
    // TODO: add movement, attack

    public Enemy(World world, String textureLocation, float initialX, float initialY, String name,
                 int health) {
        super(world, textureLocation, initialX, initialY, name, health);
    }
}
