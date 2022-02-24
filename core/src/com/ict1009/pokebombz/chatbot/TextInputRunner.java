package com.ict1009.pokebombz.chatbot;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.ict1009.pokebombz.GameMain;
import java.io.IOException;

public class TextInputRunner extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private TextField textField;
    private BitmapFont font;
    private SpriteBatch batch;
    private GlyphLayout glyphLayout;
    private static String aiText;
    private GameChatbot theChatBot;
    private GameMain game;

    public TextInputRunner(GameMain game) {
        aiText = "Enter 'Help' to open a help text file";
        this.game = game;
    }
    public void create() {

        theChatBot = new GameChatbot(game);
        stage = new Stage();
        batch = new SpriteBatch();
        // Get Default skin
        skin = new Skin(Gdx.files.internal("chatbot/uiskin.json"));
        skin.getFont("default-font").getData().setScale(1.5f);
        // Create textField
        textField = new TextField("", skin);
        //
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal("chatbot/pokemonsolid.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40; // font size
        font = generator.generateFont(parameter);
        generator.dispose();
        Gdx.input.setInputProcessor(stage);

        textField.setPosition(20, Gdx.graphics.getHeight() - 200);
        textField.setSize(Gdx.graphics.getWidth(), 50);

        stage.addActor(textField);
        glyphLayout = new GlyphLayout();

        // Add widgets to the table here.
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void activateChatBot() {

        if ((Gdx.input.isKeyJustPressed(Input.Keys.ENTER))) {
            // Code here
            try {
                theChatBot.run(textField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            aiText = theChatBot.chatbot.getBotOutput();
            textField.setText("");
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.setKeyboardFocus(textField);
        // Output Ai Text
        batch.begin();
        printExit();
        printAiText(aiText);
        batch.end();
        // Output TextField
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
    private void printExit() {
        glyphLayout.setText(font, "Type \"Exit\" To Resume");
        float width = glyphLayout.width;
        font.draw(batch, "Type \"Exit\" To Resume", Gdx.graphics.getWidth() / 2 - width / 2,
                  Gdx.graphics.getHeight() - 300);
    }
    private void printAiText(String aiText) {
        glyphLayout.setText(font, aiText);
        // Width to get center
        float width = glyphLayout.width;
        font.draw(batch, aiText, Gdx.graphics.getWidth() / 2 - width / 2,
                  Gdx.graphics.getHeight() - 100);
    }
    public void changeFocus() {
        this.stage.setKeyboardFocus(null);
    }
}
