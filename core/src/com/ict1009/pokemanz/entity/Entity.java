package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
        setPosition((GameInfo.RATIO_WIDTH / 2) * GameInfo.GRID - getWidth() / 2f,
                    (GameInfo.RATIO_HEIGHT / 2) * GameInfo.GRID - getHeight() / 2f);
        // createBody();
    }

    public Entity(World world, String textureLocation, float initialX, float initialY, String name,
                  int health) {
        super(new Texture(textureLocation));
        this.name = name;
        this.health = health;
        this.world = world;
        setPosition(initialX * GameInfo.GRID - getWidth() / 2f,
                    initialY * GameInfo.GRID - getHeight() / 2f);
        // createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public void updateHealth(int health) {
        this.health += health;
    }
}
