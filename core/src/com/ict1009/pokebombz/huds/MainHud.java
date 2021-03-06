package com.ict1009.pokebombz.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ict1009.pokebombz.GameMain;
import com.ict1009.pokebombz.helper.BoardInfo;
import com.ict1009.pokebombz.helper.GameInfo;

public class MainHud {
    private Stage stage;
    private Viewport gameViewport;

    private int players;

    private Image timerImage;
    private Label timerLabel;

    private Image[] scoreImage;
    private Label[] scoreLabel;

    public MainHud(GameMain game, int players) {
        this.players = players;
        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        scoreLabel = new Label[players];
        scoreImage = new Image[players];
        createImages();
        createLabels();

        Table timerTable = new Table();
        timerTable.top().left();
        timerTable.setFillParent(true);

        timerTable.add(timerImage)
            .padTop(GameInfo.PPM / 2)
            .padLeft(GameInfo.PPM * 18 + GameInfo.PPM / 4);
        timerTable.add(timerLabel).padTop(GameInfo.PPM / 2).padLeft(10);

        Table scoreTable = new Table();
        scoreTable.setFillParent(true);
        scoreTable.top().left();

        scoreTable.row();
        scoreTable.add().padTop(GameInfo.PPM * 2);
        scoreTable.add().padTop(GameInfo.PPM * 2);

        for (int i = 0; i < players; i++) {
            scoreTable.row();
            scoreTable.add(scoreImage[i])
                .padTop(GameInfo.PPM / 5)
                .padLeft(GameInfo.PPM * 18 + GameInfo.PPM / 4);
            scoreTable.add(scoreLabel[i]).padTop(GameInfo.PPM / 5).padLeft(10);
        }

        stage.addActor(timerTable);
        stage.addActor(scoreTable);
    }

    private void createImages() {
        timerImage = new Image(new Texture("hud/clock.png"));

        for (int i = 0; i < players; i++) {
            scoreImage[i] = new Image(new Texture(String.format("player/%d/downstill.png", i + 1)));
        }
    }

    private void createLabels() {
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("font/pokemonsolid.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);

        timerLabel = new Label(calculateTime(), new Label.LabelStyle(font, Color.WHITE));

        for (int i = 0; i < players; i++) {
            scoreLabel[i] = new Label(String.valueOf(BoardInfo.playerScore[i]), new Label.LabelStyle(font, Color.WHITE));
        }
    }

    private String calculateTime() {
        int totalSeconds = (GameInfo.SUDDEN_DEATH - GameInfo.timeElapsed) / 60;
        int seconds = totalSeconds % 60;
        int minute = totalSeconds / 60;

        if (totalSeconds <= 0)
            return "SD";

        return String.format("%d:%02d", minute, seconds);
    }

    public void updateTime() {
        timerLabel.setText(calculateTime());
    }

    public void updateScore(int playerNumber, int score) {
        scoreLabel[playerNumber - 1].setText(score);
    }

    public Stage getStage() {
        return stage;
    }
}
