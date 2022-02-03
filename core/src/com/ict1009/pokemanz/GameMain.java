package com.ict1009.pokemanz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ict1009.pokemanz.scenes.MainScene;
import com.ict1009.pokemanz.chatbot.gameChatbot;
import com.ict1009.pokemanz.chatbot.textInputRunner;
import com.ict1009.pokemanz.scenes.TitleScene;

public class GameMain extends Game {
    private SpriteBatch batch;
    private textInputRunner chatBot;
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
        chatBot = new textInputRunner();
		chatBot.create();
    }

    @Override
    public void render() {
       	if((Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))){
    		System.out.println("5pause");
    		toggleState();
    	}
    	if(pause != true) {
    		super.render();
    		chatBot.changeFocus();
    	}
    	else {
    		
    		chatBot.activateChatBot();	
    	}
        
    }
}
