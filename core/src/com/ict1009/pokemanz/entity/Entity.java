package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public abstract class Entity extends Sprite {
    final private World world;
    final private Body body;
    final private String name;

    private int health;

    public Entity(World world, String textureLocation, String name) {
        super(new Texture(textureLocation));
        this.name = name;
        this.health = 3;
        this.world = world;
        setPosition(7.5f * GameInfo.PPM, 8f * GameInfo.PPM);
        this.body = createBody();
    }

    public Entity(World world, String textureLocation, float initialX, float initialY, String name,
                  int health) {
        super(new Texture(textureLocation));
        this.name = name;
        this.health = health;
        this.world = world;
        setPosition(initialX * GameInfo.PPM, initialY * GameInfo.PPM);
        this.body = createBody();
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Body getBody() {
        return this.body;
    }

    public void updateEntity() {
        setPosition((body.getPosition().x) * GameInfo.PPM, (body.getPosition().y) * GameInfo.PPM);
    }

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        body.createFixture(shape, 1f).setUserData(this);
        shape.dispose();
        return body;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this, this.getX(), this.getY());
    }
}
