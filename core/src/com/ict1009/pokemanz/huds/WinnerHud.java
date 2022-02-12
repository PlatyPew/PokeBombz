package com.ict1009.pokemanz.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.helper.BoardInfo;
import com.ict1009.pokemanz.helper.GameInfo;

public class WinnerHud {
    private Stage stage;
    private Viewport gameViewport;

    private Label winnerLabel, scoreLabel;

    private int playerNum;

    public WinnerHud(GameMain game, int playerNum) {
        gameViewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        this.playerNum = playerNum;
        createLabels();

        Table table = new Table();
        table.top().center();
        table.setFillParent(true);

        table.add(winnerLabel).padTop(GameInfo.PPM * 5 + 30);
        table.row();
        table.add(scoreLabel).padTop(GameInfo.PPM + 30);

        stage.addActor(table);
    }

    private void createLabels() {
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("font/pokemonsolid.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);

        winnerLabel = new Label(String.format("Player %d Wins!!!", playerNum),
                                new Label.LabelStyle(font, Color.WHITE));

        scoreLabel = new Label(String.format("Score: %d", BoardInfo.playerScore[playerNum - 1]),
                               new Label.LabelStyle(font, Color.WHITE));
    }

    public Stage getStage() {
        return stage;
    }
}
