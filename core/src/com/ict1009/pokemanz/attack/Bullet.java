package com.ict1009.pokemanz.attack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.GameInfo;

public abstract class Bullet extends Sprite {
    final private World world;
    final private Body body;
    final private Player player;
    final private int damage;

    private boolean activated = false;
    protected boolean toDestroy = false;

    public Bullet(World world, Player player, String textureLocation, int damage) {
        super(new Texture(textureLocation));
        this.world = world;
        this.player = player;
        this.damage = damage;

        this.body = createBody();
    }

    public Body getBody() {
        return body;
    }

    public int getDamage() {
        return damage;
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

    public void attackRight() {
        setPosition(player.getX() + GameInfo.PPM, player.getY() + GameInfo.PPM / 2);
        this.activated = true;
        getBody().setLinearVelocity(5f, 0f);
    }

    public void draw(SpriteBatch batch) {
        if (activated) {
            batch.draw(this, this.getX(), this.getY());
        }
    }

    public void update(float delta) {
        if (toDestroy && activated) {
            this.world.destroyBody(this.body);
            this.activated = false;
        }
    }
}
