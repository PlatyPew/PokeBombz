package com.ict1009.pokemanz.helper;

import com.badlogic.gdx.physics.box2d.Body;

public interface Destoryable {
    public Body getBody();
    public void setToDestroy();
    public boolean getDestroyed();
}
