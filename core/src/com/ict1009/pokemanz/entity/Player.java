package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {
    private int coin = 0;

    public Player(World world, String textureLocation, String name) {
        super(world, textureLocation, name);
    }

    public int getCoin() {
        return this.coin;
    }

    public void updateCoin(int coin) {
        this.coin += coin;
    }

}
