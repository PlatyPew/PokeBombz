package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

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

    public void detectInput(float delta) {
        float velX = 0, velY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velY = -1;
        }

        getBody().setLinearVelocity(velX * GameInfo.PPM, velY * GameInfo.PPM);
        updateEntity();
    }
}
