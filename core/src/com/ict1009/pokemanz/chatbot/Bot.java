package com.ict1009.pokemanz.chatbot;

public class Bot{
    private String name, output;
    
    public Bot(){
        this.name = "AI";
        this.output = "";
    }
    public String getName(){
        return this.name;
    }
    public void bot_output(String message){
        System.out.printf("%s: %s\n", this.getName(), message);
        this.output = String.format("%s: %s\n", this.getName(), message);  
    }
    public String getOutput() {
    	return this.output;
    }
}
