package com.ict1009.pokemanz.room;

import com.ict1009.pokemanz.helper.Destoryable;

public class Breakable implements Destoryable {
    private Obstacle obstacle;

    private boolean toDestroy = false;
    private boolean destroyed = false;

    public Breakable(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    /**
     * Checks if sprite needs to be destroyed
     *
     * @param delta: 1/fps
     */
    public void update(float delta) {
        if (toDestroy && !destroyed) {
            obstacle.world.destroyBody(obstacle.body);
            destroyed = true;
        }
    }

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
