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
import com.ict1009.pokemanz.helper.BoardInfo;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.huds.MainHud;
import com.ict1009.pokemanz.huds.WinnerHud;
import com.ict1009.pokemanz.room.Map;

public class EndScene implements Screen {
    private SpriteBatch batch;
    private WinnerHud winnerHud;
    private GameMain game;

    private static final int ETT_BUTTON_WIDTH=201;
    private static final int ETT_BUTTON_HEIGHT=60;
    private static final int ETT_BUTTON_Y=120;
    private static final int ETT_BUTTON_X=GameInfo.WIDTH/2-ETT_BUTTON_WIDTH/2;
    private static final int GAME_OVER_WIDTH=513;
    private Texture exitToTitleButtonActive;
    private Texture exitToTitleButtonInactive;
    private Texture gameOver;
    private Music endMusic;
    private Animation<TextureRegion> pikachu;
    private Animation<TextureRegion> pokeball;
    private float elapsed;
    private Player winner;
    private int numPlayers;

    public EndScene(GameMain game, int numPlayers) {
        GameInfo.timeElapsed = 0;
        this.winnerHud = new WinnerHud(game, BoardInfo.players.size());
        this.batch = game.getBatch();
        this.game = game;
        this.numPlayers = numPlayers;
        gameOver = new Texture("screen/gameover.png");
        exitToTitleButtonActive = new Texture("screen/exittotitleactive.png");
        exitToTitleButtonInactive = new Texture("screen/exittotitleinactive.png");
        pikachu = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("screen/pikachu-running.gif").read());
        pokeball = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("screen/pokeball-spin.gif").read());
        endMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_end.ogg"));
        endMusic.setLooping(true);
        endMusic.play();
        getScore();
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
        batch.draw(gameOver, GameInfo.WIDTH/2F - GAME_OVER_WIDTH/2, GameInfo.HEIGHT/2F + 110);
        batch.draw(pikachu.getKeyFrame(elapsed), 190f,720/2f-50f,439/2, 321/2);
        batch.draw(pokeball.getKeyFrame(elapsed), 830f,720/2f-115f,480/2, 480/2);
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

    public void getScore() {
        for (int i = 0; i < numPlayers; i++) {
            winnerHud.updateScore(i+1,
                    BoardInfo.playerScore[i]);
        }
        int largest = 0;
        int largestIndex = 0;
        for (int i = 0; i < BoardInfo.playerScore.length; i++) {
            if (BoardInfo.playerScore[i] > largest) {
                largestIndex = i;
                largest = BoardInfo.playerScore[i];
            }
        }
        winner = BoardInfo.players.get(largestIndex);
    }

    public Player getWinner() {
        return winner;
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
