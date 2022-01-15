package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends Entity {
    final private int[] initialPos = new int[2];

    // TODO: add movement, attack

    public Enemy(String name, int health, String textureLocation, World world, int[] initialPos) {
        super(name, health, textureLocation, world);
        this.initialPos[0] = initialPos[0];
        this.initialPos[1] = initialPos[1];
    }

    public int getInitialX() {
        return this.initialPos[0];
    }

    public int getInitialY() {
        return this.initialPos[1];
    }
}
