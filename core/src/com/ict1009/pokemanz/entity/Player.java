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
        float currX = (getBody().getPosition().x) * GameInfo.PPM;
        float currY = (getBody().getPosition().y) * GameInfo.PPM;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && currX > 0) {
            velX = -GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && currX < GameInfo.WIDTH - GameInfo.PPM) {
            velX = GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && currY < GameInfo.HEIGHT - GameInfo.PPM) {
            velY = GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && currY > 0) {
            velY = -GameInfo.PLAYER_VELOCITY;
        }

        getBody().setLinearVelocity(velX, velY);
        updateEntity();
    }
}
