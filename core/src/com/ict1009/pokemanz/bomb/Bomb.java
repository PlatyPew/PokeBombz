package com.ict1009.pokemanz.bomb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.Destoryable;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.helper.BoardElement;

public class Bomb extends Sprite implements Destoryable, BoardElement {
    final World world;
    final private int timer = 3;
    final private int gridX, gridY;

    private Body body;
    private int range = 3;

    private int counter = 0;

    private boolean destroyed = false;  // When body is destroyed
    private boolean toDestroy = false;  // When body is set to be destroyed
    private boolean unloadOnly = false; // When a old body is going to be replaced
    private boolean updated = false;    // When a body has been updated

    private boolean sensor = true;
    private BodyType bodyType = BodyType.StaticBody;

    public Bomb(World world, String textureLocation, int gridX, int gridY) {
        super(new Texture(textureLocation));
        this.world = world;
        this.gridX = gridX;
        this.gridY = gridY;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.body = createBody();
    }

    public Bomb(World world, String textureLocation, int gridX, int gridY, int range) {
        super(new Texture(textureLocation));
        this.world = world;
        this.range = range;
        this.gridX = gridX;
        this.gridY = gridY;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
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

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Body getBody() {
        return body;
    }

    public boolean getUpdated() {
        return updated;
    }

    /**
     * Creates a new body
     *
     * @return body: Body
     */
    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(this);
        fixture.setSensor(sensor);

        shape.dispose();
        return body;
    }

    /**
     * Deletes old body and replaces new body
     *
     * @param If body is a sensor
     */
    public void updateBody(boolean sensor) {
        this.sensor = sensor;
        toDestroy = true;
        unloadOnly = true;
        updated = true;
    }

    /**
     * Deletes old body and replaces new body
     *
     * @param BodyType
     * @param If body is a sensor
     */
    public void updateBody(BodyType bodyType, boolean sensor) {
        this.bodyType = bodyType;
        this.sensor = sensor;
        toDestroy = true;
        unloadOnly = true;
        updated = true;
    }

    /**
     * Counts the number of seconds before destroying body
     *
     * @param delta: 1/fps
     */
    private void countDown(float delta) {
        if (counter < timer / delta) {
            counter++;
        } else {
            toDestroy = true;
            updated = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!destroyed) {
            batch.draw(this, this.getX(), this.getY());
        }
    }

    @Override
    public void update(float delta) {
        countDown(delta);

        if (toDestroy && !destroyed) {
            this.world.destroyBody(this.body);
            destroyed = true;
        }

        if (destroyed && unloadOnly) {
            createBody();
            toDestroy = false;
            destroyed = false;
            unloadOnly = false;
        }
    }

    @Override
    public void setToDestroy() {
        toDestroy = true;
    }

    @Override
    public boolean getDestroyed() {
        return destroyed;
    }
}
