package com.ict1009.pokemanz.room;

public class Breakable {
    private Obstacle obstacle;

    private boolean toDestroy = false;
    private boolean destroyed = false;

    public Breakable(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public void setToDestroy() {
        toDestroy = true;
    }

    public boolean getDestroyed() {
        return destroyed;
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
}
