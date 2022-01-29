package com.ict1009.pokemanz.bomb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public class Bomb extends Sprite {
    final World world;
    final Body body;
    final private int timer = 3;
    final private int initialX;
    final private int initialY;

    private int range = 3;
	private SpriteBatch batch;

    public Bomb(World world, String textureLocation, int initialX, int initialY) {
        super(new Texture(textureLocation));
        this.world = world;
        this.initialX = initialX;
        this.initialY = initialY;
        setPosition((initialX + 1) * GameInfo.PPM, (initialY + 1) * GameInfo.PPM);
        this.body = createBody();
    }

    public Bomb(World world, String textureLocation, int initialX, int initialY, int range) {
        super(new Texture(textureLocation));
        this.world = world;
        this.range = range;
        this.initialX = initialX;
        this.initialY = initialY;
        setPosition((initialX + 1) * GameInfo.PPM, (initialY + 1) * GameInfo.PPM);
        this.body = createBody();
    }

    public int getTimer() {
        return timer;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(this);

        shape.dispose();
        return body;
    }


    public void render(SpriteBatch batch) {
        batch.draw(this, this.getX(), this.getY());
    }
}
