package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.item.Coin;
import com.ict1009.pokemanz.item.Potion;

public class Player extends Entity implements ContactListener {
    private int coin = 0;

    public Player(World world, String textureLocation, String name) {
        super(world, textureLocation, name);
    }

    public int getCoin() {
        return this.coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void handleMovement() {
        float velX = 0, velY = 0;
        float currX = (getBody().getPosition().x) * GameInfo.PPM;
        float currY = (getBody().getPosition().y) * GameInfo.PPM;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && currY < GameInfo.HEIGHT - GameInfo.PPM) {
            velY = GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && currX > 0) {
            velX = -GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && currY > 0) {
            velY = -GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && currX < GameInfo.WIDTH - GameInfo.PPM) {
            velX = GameInfo.PLAYER_VELOCITY;
        }

        getBody().setLinearVelocity(velX, velY);
    }

    public void handleAttack() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

        }
    }

    public void update(float delta) {
        handleMovement();
        updateEntity();
    }

    @Override
    public void beginContact(Contact contact) {
        Object body;

        if (contact.getFixtureA().getUserData() instanceof Player) {
            body = contact.getFixtureB().getUserData();
        } else {
            body = contact.getFixtureA().getUserData();
        }

        // TODO: Make code less disgusting
        if (body instanceof Coin) {
            Coin coin = (Coin)body;
            coin.applyProperty(this);
        } else if (body instanceof Potion) {
            Potion potion = (Potion)body;
            potion.applyProperty(this);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
    }
}
