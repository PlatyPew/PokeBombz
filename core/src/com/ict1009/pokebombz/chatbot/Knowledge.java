package com.ict1009.pokebombz.chatbot;
class Knowledge {
    protected String question, answer, firstWord;

    public Knowledge(String firstWord, String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.firstWord = firstWord;
    }
}
