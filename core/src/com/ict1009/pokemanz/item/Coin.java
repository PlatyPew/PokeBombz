package com.ict1009.pokemanz.item;

import com.badlogic.gdx.physics.box2d.World;

public class Coin extends Item {
    final private int value;

    public Coin(World world, String textureLocation, float initialX, float initialY, int value) {
        super(world, textureLocation, initialX, initialY);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
