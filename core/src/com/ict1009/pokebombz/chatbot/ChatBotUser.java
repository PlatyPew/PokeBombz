package com.ict1009.pokebombz.chatbot;
import java.util.*;

public class ChatBotUser {
    protected String name;
    protected String input;
    public ChatBotUser() {
        this.name = "Player";
        this.input = "";
    }
    public String getInput() {
        return this.input;
    }
    public void userInput(Scanner sc) {
        System.out.printf("%s: ", this.name);
        this.input = sc.nextLine();
    }
    public void userSetinput(String newInput) {
        this.input = newInput;
    }
}
