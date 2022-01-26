package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public class Obstacle extends Sprite {
    final protected World world;
    final protected Body body;

    final private boolean canBreak;
    private Breakable breakable = null;

    public Obstacle(World world, String textureLocation, float initialX, float initialY) {
        super(new Texture(textureLocation));
        this.world = world;
        setPosition((initialX + 1) * GameInfo.PPM, (initialY + 1) * GameInfo.PPM);
        this.body = createBody();
        this.canBreak = false;
    }

    public Obstacle(World world, String textureLocation, float initialX, float initialY,
                    boolean canBreak) {
        super(new Texture(textureLocation));
        this.world = world;
        setPosition((initialX + 1) * GameInfo.PPM, (initialY + 1) * GameInfo.PPM);
        this.body = createBody();
        this.canBreak = canBreak;

        if (canBreak) {
            this.breakable = new Breakable(this);
        }
    }

    public Body getBody() {
        return body;
    }

    public boolean getBreakable() {
        return canBreak;
    }

    /**
     * Creates a static body with a square shape
     *
     * @return body: Static body
     */
    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        body.createFixture(shape, 1f).setUserData(this);
        shape.dispose();
        return body;
    }

    public void setToDestroy() {
        if (canBreak) {
            breakable.setToDestroy();
        }
    }

    public boolean getDestroyed() {
        if (canBreak) {
            return breakable.getDestroyed();
        }
        return false;
    }

    public void update(float delta) {
        if (canBreak) {
            breakable.update(delta);
        }
    }

    /**
     * Renders the body of the obstacle
     * If it can be broken, do not render if destroyed
     *
     * @param batch: The spritebatch of the game
     */
    public void render(SpriteBatch batch) {
        if (!(canBreak && getDestroyed())) {
            batch.draw(this, this.getX(), this.getY());
        }
    }
}
