package com.ict1009.pokemanz.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.huds.WinnerHud;

public class EndScene implements Screen {
    private SpriteBatch batch;
    private WinnerHud winnerHud;
    private GameMain game;

    private static final int ETT_BUTTON_WIDTH=201;
    private static final int ETT_BUTTON_HEIGHT=60;
    private static final int ETT_BUTTON_Y=70;
    private static final int ETT_BUTTON_X=GameInfo.WIDTH/2-ETT_BUTTON_WIDTH/2;
    private Texture exitToTitleButtonActive;
    private Texture exitToTitleButtonInactive;
    private Music endMusic;
    private Animation<TextureRegion> pikachu;
    private Animation<TextureRegion> pokeball;
    private float elapsed;

    public EndScene(GameMain game, Player player) {
        this.winnerHud = new WinnerHud(game, player.getPlayerNumber());
        this.batch = game.getBatch();
        this.game = game;
        exitToTitleButtonActive = new Texture("screen/exittotitleactive.png");
        exitToTitleButtonInactive = new Texture("screen/exittotitleinactive.png");
        pikachu = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("screen/pikachu-running.gif").read());
        pokeball = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("screen/pokeball-spin.gif").read());
        endMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_end.ogg"));
        endMusic.setLooping(true);
        endMusic.play();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(new Texture("room/scorescreen.png"), 0, 0);
        batch.draw(pikachu.getKeyFrame(elapsed), 170f,720/2f-50f,439/2, 321/2);
        batch.draw(pokeball.getKeyFrame(elapsed), 850f,720/2f-115f,480/2, 480/2);
        if (Gdx.input.getX() < ETT_BUTTON_X + ETT_BUTTON_WIDTH  && Gdx.input.getX() > ETT_BUTTON_X - ETT_BUTTON_WIDTH && GameInfo.HEIGHT - Gdx.input.getY() < ETT_BUTTON_HEIGHT + ETT_BUTTON_Y && GameInfo.HEIGHT - Gdx.input.getY() > ETT_BUTTON_Y) {
            batch.draw(exitToTitleButtonActive,ETT_BUTTON_X,ETT_BUTTON_Y);
            if (Gdx.input.justTouched()) {
                this.dispose();
                endMusic.dispose();
                game.setScreen(new TitleScene(game));
            }
        } else {
             batch.draw(exitToTitleButtonInactive,ETT_BUTTON_X,ETT_BUTTON_Y);
        }
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
