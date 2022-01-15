package com.ict1009.pokemanz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ict1009.pokemanz.scenes.MainScene;

public class GameMain extends Game {
    private SpriteBatch batch;

    public SpriteBatch getBatch() {
        return this.batch;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainScene(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
