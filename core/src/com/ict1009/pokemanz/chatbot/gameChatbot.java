package com.ict1009.pokemanz.chatbot;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.scenes.TitleScene;
import java.io.IOException;
import java.lang.Math;
import java.text.DecimalFormat;
import java.util.*;

public class gameChatbot {
    Chatbot chatbot;
    private GameMain game;
    public gameChatbot(GameMain game) {
        this.game = game;
        chatbot = new Chatbot();
        chatbot.chatbot_do_load("init.ini");
    }
    public void run(String newInput) throws IOException {
        chatbot.setUserInput(newInput);
        if (chatbot.chatbot_is_exit()) {

        } else if (chatbot.chatbot_is_exitMenu(game)) {

        } else if (chatbot.chatbot_is_question()) {

        } else if (chatbot.chatbot_is_unansweredQuestion()) {

        } else if (chatbot.chatbot_is_smalltalk()) {
        } else if (chatbot.chatbot_is_reset()) {
            chatbot.chatbot_do_load("init.ini");
        } else if (chatbot.chatbot_is_save()) {

        } else if (chatbot.chatbot_is_ChangeSpawnTime()) {

        } else if (chatbot.chatbot_is_load()) {

        } else if (chatbot.chatbot_is_changeSpeed()) {

        } else if (chatbot.chatbot_is_ChangeDeathTimer()) {

        } else if (chatbot.chatbot_is_StartSuddenDeath()) {

        } else if (chatbot.chatbot_is_Change_Bomb_Count()) {

        } else if (chatbot.chatbot_is_Change_Bomb_Range()) {

        } else if (chatbot.chat_bot_is_load_score_to_game()) {

        } else if (chatbot.chat_bot_is_Update_New_Score()) {

        } else if (chatbot.chat_bot_is_Update_Old_Score()) {

        }

        else {
            chatbot.chatbot_do_not_understand();
        }
    }
}
