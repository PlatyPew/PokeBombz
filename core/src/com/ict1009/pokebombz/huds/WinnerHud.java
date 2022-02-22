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

public class WinnerHud {
    private Stage stage;
    private Viewport gameViewport;

    private int players;

    private Image[] scoreImage;
    private Label[] scoreLabel;

    public WinnerHud(GameMain game, int players) {
        this.players = players;
        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        scoreLabel = new Label[players];
        scoreImage = new Image[players];
        createImages();
        createLabels();

        Table scoreTable = new Table();
        scoreTable.setFillParent(true);
        scoreTable.center();

        scoreTable.row();
        scoreTable.add().padTop(50);
        scoreTable.add().padTop(50);

        for (int i = 0; i < players; i++) {
            scoreTable.row();
            scoreTable.add(scoreImage[i]);
            scoreTable.add(scoreLabel[i]).padTop(GameInfo.PPM / 5).padLeft(10);
        }

        stage.addActor(scoreTable);
    }

    private void createImages() {

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

        for (int i = 0; i < players; i++) {
            scoreLabel[i] = new Label("0", new Label.LabelStyle(font, Color.WHITE));
        }
    }
    public void updateScore(int playerNumber, int score) {
        scoreLabel[playerNumber - 1].setText(score);
    }

    public Stage getStage() {
        return stage;
    }
}
