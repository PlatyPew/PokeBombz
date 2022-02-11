package com.ict1009.pokemanz.bomb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.BoardElement;
import com.ict1009.pokemanz.helper.Destoryable;
import com.ict1009.pokemanz.helper.GameInfo;

public class Bomb extends Sprite implements Destoryable, BoardElement {
    final World world;
    final private int timer = 2;
    private int gridX, gridY;
    final private int playerNumber;

    final private TextureAtlas bombAtlas;

    private Animation<TextureAtlas.AtlasRegion> animation;
    private float elapsedTime;

    private Body body;
    private int counter = 0;

    private boolean destroyed = false;  // When body is destroyed
    private boolean toDestroy = false;  // When body is set to be destroyed
    private boolean unloadOnly = false; // When a old body is going to be replaced
    private boolean updated = false;    // When a body has been updated

    private boolean sensor = true;
    private BodyType bodyType = BodyType.StaticBody;

    public Bomb(World world, String textureLocation, int gridX, int gridY, int playerNumber) {
        super(new Texture(textureLocation));
        this.world = world;
        this.gridX = gridX;
        this.gridY = gridY;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.body = createBody();
        this.bombAtlas = new TextureAtlas("bomb/bomb explosion.atlas");
        this.playerNumber = playerNumber;
    }

    public int getTimer() {
        return timer;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    @Override
    public Body getBody() {
        return body;
    }

    public boolean getUpdated() {
        return updated;
    }

    public int getPlayerNumber() {
        return playerNumber;
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

    public void updatePosition(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        super.setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        body.setTransform(getX() / GameInfo.PPM, getY() / GameInfo.PPM, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!destroyed) {
            elapsedTime += Gdx.graphics.getDeltaTime();

            animation = new Animation<TextureAtlas.AtlasRegion>(1f / 10f, bombAtlas.getRegions());
            batch.draw((TextureRegion)animation.getKeyFrame(elapsedTime, true), this.getX(),
                       this.getY());
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
