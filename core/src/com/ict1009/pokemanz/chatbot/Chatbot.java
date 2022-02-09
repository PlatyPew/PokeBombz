package com.ict1009.pokemanz.chatbot;
import java.io.IOException;
import java.util.*;
public class Chatbot {
    protected Bot bot;
    protected Player player;
    protected String[] words, questionWords;
    protected String input, entity,answer;
    public Scanner sc = new Scanner(System.in);
    private boolean unAnsweredQuestion = false;
    KnowledgeList knowledgeList;
    Knowledge knowledge;
    public Chatbot(){
        bot = new Bot();
        player = new Player();
        knowledgeList = new KnowledgeList();
    }
    public void getUserInput(){
        player.userInput(sc);
        this.input = player.getInput();
        this.input = cleanInput(this.input);
        tokenizeInput();
    }
    public void setUserInput(String newInput) {
    	player.userSetinput(newInput);
    	this.input = player.getInput();
        this.input = cleanInput(this.input);
        tokenizeInput();

    }
    public String cleanInput(String input){
        return input.replaceAll("[^a-zA-Z0-9 ]","");
    }
    public void tokenizeInput(){
        // String temp = this.input.toLowerCase();
        this.words = this.input.split(" ");
    }
    public boolean chatbot_is_smalltalk(){
        String[] smalltalk = {"good", "hello", "hey", "hi", "it", "its", "it's"};
        for(int i = 0; i < smalltalk.length;i++) {
            if(words[0].toLowerCase().equals(smalltalk[i])){
                chatbot_do_smalltalk(this.words);
                return true;
            }
        }
        return false;
    }
    public void  chatbot_do_smalltalk(String[] words){
        
        if(words[0].toLowerCase().equals("good")){
            bot.bot_output("Good Day");
        }
        else if(words[0].toLowerCase().equals("it's") || words[0].toLowerCase().equals("its")){
            bot.bot_output("Indeed It is");
        }
        else{
            bot.bot_output("Hello to you too");
        }
    } 
    public boolean chatbot_is_exit(){
        if(this.words[0].toLowerCase().equals("exit") || this.words[0].toLowerCase().equals("quit")){
            chatbot_do_exit();
            return true;
        }
        else{
            return false;
        }
    }
    public void  chatbot_do_exit(){
        bot.bot_output("ExITING");
    }
    public boolean chatbot_is_question(){
        if (this.words[0].toLowerCase().equals("what") || this.words[0].toLowerCase().equals("who")
         || this.words[0].toLowerCase().equals("where"))
        {
            chatbot_do_question(this.words);
            return true;
        }
        return false;
    }
    public void chatbot_do_question(String[] words){
        // remove 'is' and 'are' word then input entity into the "entity" variable
        entity = "";
        for (int i = 0; i <words.length; i++){
            if(i==0 || (i==1 && (words[1].toLowerCase().equals("is") || words[1].toLowerCase().equals("are")))){
                //pass
            }
            else{
                entity += words[i]+" ";
            }
        }
        entity = entity.trim();
        if(entity.equals("")){
            System.out.println("Invalid Input");
        }
        else{
            //Check if it question is in knowledgelist
        	
            if(knowledgeList.getKnowledge(entity,words[0])){
                this.knowledge =  knowledgeList.getTargetKnowledge();
                bot.bot_output(this.knowledge.answer);
                
            }
            else{
                questionWords = words;
                bot.bot_output("I don't know "+ this.input + "?");
                this.unAnsweredQuestion = true;
                
            }

        }
    }
    public boolean chatbot_is_unansweredQuestion(){
        if(this.unAnsweredQuestion){
            answer = player.getInput();
            knowledgeList.addKnowledge(questionWords[0],entity,answer);
            this.unAnsweredQuestion = false;
            bot.bot_output("Okay Noted");
            return true;
        }
        return false;
    }
    public void chatbot_do_not_understand(){
        bot.bot_output("I don't understand\t" + this.input);
    }
    public boolean chatbot_is_reset(){
        if(this.input.toLowerCase().equals("reset")){
            chatbot_do_rest();
            return true;
        }
        return false;
    }
    public void chatbot_do_rest(){
        knowledgeList.clearKnowledge();
    }
    public boolean chatbot_is_save() throws IOException{
        if(this.input.toLowerCase().equals("save")){
            chatbot_do_save();
            bot.bot_output("Data saved!");
            return true;
        }
        return false;
    }
    public void chatbot_do_save() throws IOException{
        knowledgeList.saveKnowledge();
    }
    public boolean chatbot_is_load() throws IOException{
        if(this.input.toLowerCase().equals("load")){
            chatbot_do_load();
            bot.bot_output("Data Loaded!");
            return true;
        }
        return false;
    }
    public void chatbot_do_load() throws IOException{
        knowledgeList.loadKnowledge();
    }
}






