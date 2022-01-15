package com.ict1009.pokemanz.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.ict1009.pokemanz.GameMain;

public class MainScene implements Screen {
    private GameMain game;

    private Texture background;

    public MainScene(GameMain game) {
        this.game = game;
        background = new Texture("room/background.jpg");
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
