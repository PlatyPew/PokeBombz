package com.ict1009.pokemanz.chatbot;
import java.util.*;

public class Player{
    protected String name;
    protected String input;
    public Player(){
        this.name = "Player";
        this.input = "";
    }
    public String getInput(){
        return this.input;
    }
    public void userInput(Scanner sc){
        System.out.printf("%s: ", this.name);
        this.input = sc.nextLine();
    }
    public void userSetinput(String newInput) {
    	this.input =newInput;
    }
}
