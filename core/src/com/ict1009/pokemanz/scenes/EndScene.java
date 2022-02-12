package com.ict1009.pokemanz.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.huds.WinnerHud;

public class EndScene implements Screen {
    private SpriteBatch batch;
    private WinnerHud winnerHud;

    public EndScene(GameMain game, Player player) {
        this.winnerHud = new WinnerHud(game, player.getPlayerNumber());
        this.batch = game.getBatch();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(new Texture("room/scorescreen.png"), 0, 0);
        batch.end();
        batch.setProjectionMatrix(winnerHud.getStage().getCamera().combined);
        winnerHud.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
