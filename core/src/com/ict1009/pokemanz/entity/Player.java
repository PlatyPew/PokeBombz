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
        float currX = getBody().getPosition().x;
        float currY = getBody().getPosition().y;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && currX > 0) {
            velX = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && currX < GameInfo.WIDTH - GameInfo.PPM) {
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && currY < GameInfo.HEIGHT - GameInfo.PPM) {
            velY = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && currY > 0) {
            velY = -1;
        }

        getBody().setLinearVelocity(velX * GameInfo.PPM, velY * GameInfo.PPM);
        updateEntity();
    }
}
