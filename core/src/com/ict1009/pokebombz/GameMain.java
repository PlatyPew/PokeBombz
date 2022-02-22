package com.ict1009.pokebombz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ict1009.pokebombz.chatbot.TextInputRunner;
import com.ict1009.pokebombz.scenes.TitleScene;

public class GameMain extends Game {
    private SpriteBatch batch;
    private TextInputRunner chatBot;
    private static boolean pause = false;

    public SpriteBatch getBatch() {
        return this.batch;
    }
    public static void toggleState() {
        pause = !pause;
    }
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new TitleScene(this));
        chatBot = new TextInputRunner(this);
        chatBot.create();
    }

    @Override
    public void render() {
        if ((Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))) {
            System.out.println("5pause");
            toggleState();
        }
        if (pause != true) {
            super.render();
            chatBot.changeFocus();
        } else {
            chatBot.activateChatBot();
        }
    }
}
