package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {
    private int coin = 0;

    public Player(String name, String textureLocation, World world) {
        super(name, textureLocation, world);
    }

    public int getCoin() {
        return this.coin;
    }

    public void updateCoin(int coin) {
        this.coin += coin;
    }

}
