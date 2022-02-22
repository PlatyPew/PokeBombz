package com.ict1009.pokebombz.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface BoardElement {
    public void render(SpriteBatch batch);
    public void update(float delta);
}
