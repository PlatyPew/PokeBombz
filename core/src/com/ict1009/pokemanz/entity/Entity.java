package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public class Entity extends Sprite {
    final private String name;

    private World world;
    private Body body;

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

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        body.createFixture(shape, 1f);
        shape.dispose();
        return body;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public Body getBody() {
        return this.body;
    }

    public void updateHealth(int health) {
        this.health += health;
    }

    public void updateEntity() {
        setPosition(body.getPosition().x, body.getPosition().y);
    }
}
