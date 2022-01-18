package com.ict1009.pokemanz.attack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public class Attack extends Sprite {
    final private World world;
    final private Body body;

    final private int damage;

    protected boolean toDestroy = false;
    private boolean activated = false;

    public Attack(World world, String textureLocation, int damage) {
        super(new Texture(textureLocation));
        this.world = world;
        this.damage = damage;

        this.body = createBody();
    }

    public int getDamage() {
        return damage;
    }

    public World getWorld() {
        return world;
    }

    public Body getBody() {
        return body;
    }

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(this);
        fixture.setSensor(true);

        shape.dispose();
        return body;
    }

    public void draw(SpriteBatch batch) {
        if (activated)
            batch.draw(this, this.getX(), this.getY());
    }

    public void update(float delta) {
        if (toDestroy && activated) {
            this.world.destroyBody(this.body);
            this.activated = false;
        }
    }
}
