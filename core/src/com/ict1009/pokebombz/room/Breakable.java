package com.ict1009.pokebombz.room;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.ict1009.pokebombz.helper.BoardElement;
import com.ict1009.pokebombz.helper.Destoryable;

public class Breakable implements Destoryable, BoardElement {
    private Obstacle obstacle;

    private boolean toDestroy = false;
    private boolean destroyed = false;

    public Breakable(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public Body getBody() {
        return null;
    }

    /**
     * Checks if sprite needs to be destroyed
     *
     * @param delta: 1/fps
     */
    @Override
    public void update(float delta) {
        if (toDestroy && !destroyed) {
            obstacle.world.destroyBody(obstacle.body);
            destroyed = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {}

    /**
     * Destroys the obstacle
     */
    @Override
    public void setToDestroy() {
        toDestroy = true;
    }

    /**
     * Checks if obstacle has been destroyed
     *
     * @return boolean
     */
    @Override
    public boolean getDestroyed() {
        return destroyed;
    }
}
