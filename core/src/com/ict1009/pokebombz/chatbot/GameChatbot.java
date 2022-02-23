package com.ict1009.pokebombz.chatbot;

import java.io.IOException;

import com.ict1009.pokebombz.GameMain;

public class GameChatbot {
    Chatbot chatbot;
    private GameMain game;
    public GameChatbot(GameMain game) {
        this.game = game;
        chatbot = new Chatbot();
        chatbot.chatbot_do_load("../assets/init.ini");
    }
    public void run(String newInput) throws IOException {
        chatbot.setUserInput(newInput);
        if (chatbot.chatbot_is_exit()) {

        } else if (chatbot.chatbot_is_exitMenu(game)) {

        } else if (chatbot.chatbot_is_question()) {

        } else if (chatbot.chatbot_is_unansweredQuestion()) {

        } else if (chatbot.chatbot_is_smalltalk()) {	
        	
        } else if (chatbot.chatbot_is_reset()) {

        } else if (chatbot.chatbot_is_save()) {

        } else if (chatbot.chatbot_is_ChangeSpawnChance()) {

        } else if (chatbot.chatbot_is_load()) {

        } else if (chatbot.chatbot_is_changeSpeed()) {

        } else if (chatbot.chatbot_is_ChangeDeathTimer()) {

        } else if (chatbot.chatbot_is_StartSuddenDeath()) {

        } else if (chatbot.chatbot_is_Change_Bomb_Count()) {

        } else if (chatbot.chatbot_is_Change_Bomb_Range()) {

        } else if (chatbot.chatbot_is_load_score_to_game()) {

        } else if (chatbot.chatbot_is_Update_New_Score()) {

        } else if (chatbot.chatbot_is_Update_Old_Score()) {

        } else if (chatbot.chatbot_is_reset_scores()) {

        } else if (chatbot.chatbot_is_ChangeKickBomb()) {

        } else if (chatbot.chatbot_is_help()) {
        	
        }
        
        else {
            chatbot.chatbot_do_not_understand();
        }
    }
}
