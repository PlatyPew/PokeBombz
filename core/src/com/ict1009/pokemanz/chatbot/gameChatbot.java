package com.ict1009.pokemanz.chatbot;
import java.util.*;
import java.io.IOException;
import java.lang.Math;
import java.text.DecimalFormat;

public class gameChatbot {
		Chatbot chatbot;
    public gameChatbot() {
         chatbot = new Chatbot();

}
    public void run(String newInput) throws IOException {
    	chatbot.setUserInput(newInput);
        if(chatbot.chatbot_is_exit()){
        	
        }
        else if(chatbot.chatbot_is_question()){

        }
        else if (chatbot.chatbot_is_unansweredQuestion()) {
        	
        }
        else if(chatbot.chatbot_is_smalltalk()){ 
        }
        else if(chatbot.chatbot_is_reset()){
            
        }
        else if(chatbot.chatbot_is_save()){
            
        }
        else if(chatbot.chatbot_is_ChangeSpawnTime()){
            
        }
        else if(chatbot.chatbot_is_load()){
            
        }
        else if (chatbot.chatbot_is_changeSpeed()) {
        	
        }
        else if(chatbot.chatbot_is_ChangeDeathTimer()) {
        	
        }
        else if(chatbot.chatbot_is_StartSuddenDeath()) {
        	
        }
        else{
            chatbot.chatbot_do_not_understand();
        }
        

    }
}