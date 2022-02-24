package com.ict1009.pokebombz.chatbot;

import com.ict1009.pokebombz.GameMain;
import com.ict1009.pokebombz.helper.BoardInfo;
import com.ict1009.pokebombz.helper.GameInfo;
import com.ict1009.pokebombz.scenes.TitleScene;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Chatbot {
    private Scanner sc = new Scanner(System.in);
    private Bot bot;
    private String input, entity, answer;
    private Knowledge knowledge;
    private KnowledgeList knowledgeList;
    private ChatBotUser player;
    private ScoreBoardUpdater scoreboardupdater;
    private boolean unAnsweredQuestion = false;
    private String[] words, questionWords;

    public Chatbot() {
        bot = new Bot();
        this.player = new ChatBotUser();
        knowledgeList = new KnowledgeList();
        scoreboardupdater = new ScoreBoardUpdater();
        chatbot_do_Reload_scoreUpdater();
    }
    // Load saved knowledge into knowledge.ini in asset folder
    public void chatbot_do_load(String input) {

        try {
            knowledgeList.loadKnowledge(input);
        } catch (IOException e) {
            bot.bot_output("Failed to read!");
            System.out.println(e);
        }
    }
    // Bot output information that doesn't match any patterns
    public void chatbot_do_not_understand() {
        bot.bot_output("I don't understand " + this.input);
    }
    // Check if users wants to change bomb count [player can be specific]
    public boolean chatbot_is_Change_Bomb_Count() {
        if (isPatternMatch(this.input, "^change bomb count [0-9]+ ?P?[1-4]?$")) {
            chatbot_do_Change_Bomb_Count();
            return true;
        }
        return false;
    }
    // Check if users wants to change bomb range [player can be specific]
    public boolean chatbot_is_Change_Bomb_Range() {
        if (isPatternMatch(this.input, "^change bomb range [0-9]+ ?P?[1-4]?$")) {
            chatbot_do_Change_Bomb_Range();
            return true;
        }
        return false;
    }
    // Check if users wants to change death timer
    public boolean chatbot_is_ChangeDeathTimer() {
        try {

            if (isPatternMatch(this.input, "^Change death timer [0-9]+$")) {
                float value = Float.parseFloat(words[3]);
                chatbot_do_changeDeathTimer(value);
                return true;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }
    // Check if users wants to enable player to kick bomb [player can be specific]
    public boolean chatbot_is_ChangeKickBomb() {
        if (isPatternMatch(this.input, "^change Kick Bomb ?P?[1-4]?$")) {
            chatbot_do_ChangeKickBomb();
            return true;
        }
        return false;
    }
    // Check if user wants to change the spawn chance of an item
    public boolean chatbot_is_ChangeSpawnChance() {
        if (isPatternMatch(this.input, "^change spawn chance [0-9]+$")) {
            int SpawnChance = Integer.parseInt(words[3]);
            if (SpawnChance < 0)
                SpawnChance = 0;
            else if (SpawnChance > 100)
                SpawnChance = 100;
            chatbot_do_changeItemSpawn(SpawnChance);
            bot.bot_output("Spawn chance has been changed to " + words[3]);
            return true;
        }
        return false;
    }
    // Check if user wants to change speeed of [player can be specific]
    public boolean chatbot_is_changeSpeed() {
        try {
            if (isPatternMatch(this.input, "^change speed [0-9]+ ?p?[1-4]?$")) {
                float value = Float.parseFloat(words[2]);
                chatbot_do_changeSpeed(value);
                return true;
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }

        return false;
    }
    // Check if user wants to Exit game
    public boolean chatbot_is_exit() {
        if (this.words.length == 1 &&
            (words[0].toLowerCase().equals("exit") || this.words[0].toLowerCase().equals("quit"))) {
            chatbot_do_exit();
            return true;
        } else {
            return false;
        }
    }
    // Check if the user wants to exit main menu
    public boolean chatbot_is_exitMenu(GameMain game) {
        if (isPatternMatch(this.input, "^exit main menu$")) {
            GameInfo.currentMusic.dispose();
            GameInfo.timeElapsed = 0;
            game.setScreen(new TitleScene(game));
            bot.bot_output("Alright you are in menu now");
            return true;
        } else
            return false;
    }
    // Check if users needs help with the commands
    public boolean chatbot_is_help() {
        if (isPatternMatch(this.input, "^help$")) {
            try {
                chatbot_do_help();
                bot.bot_output("Okay opening help txt ");
            } catch (IOException | IllegalArgumentException e) {
                bot.bot_output("Help file either don't exist yet or currently being use.");
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    // Check if users wants to load information
    public boolean chatbot_is_load() throws IOException {
        if (this.input.toLowerCase().equals("load")) {
            chatbot_do_load();
            bot.bot_output("Data Loaded!");
            return true;
        }
        return false;
    }
    // Check if users wants upload a existing score in Scoreboard.ini
    public boolean chatbot_is_load_score_to_game() {
        chatbot_do_Reload_scoreUpdater();
        if (isPatternMatch(this.input, "^upload score ID [0-9]+$")) {
            int idNum = Integer.parseInt(words[3]);
            if (idNum > scoreboardupdater.getLatestIdNumber()) {
                return false;
            }
            chatbot_do_load_Score_To_Game(idNum);
            return true;
        }
        return false;
    }
    // Check if users enters input thats starts with what, who or how
    public boolean chatbot_is_question() {
        if (this.words[0].toLowerCase().equals("what") ||
            this.words[0].toLowerCase().equals("who") ||
            this.words[0].toLowerCase().equals("how")) {
            chatbot_do_question(this.words);
            return true;
        }
        return false;
    }
    // Check if users wants to reset chatbot information
    public boolean chatbot_is_reset() {
        if (this.input.toLowerCase().equals("reset")) {
            chatbot_do_reset();
            return true;
        }
        return false;
    }
    // Check if users wants to reset their score
    public boolean chatbot_is_reset_scores() {
        if (isPatternMatch(this.input, "^Reset score$")) {
            bot.bot_output("Resetted Score");
            BoardInfo.playerScore = new int[] {0, 0, 0, 0};
            return true;
        }
        return false;
    }
    // Check if users wants to save data into knowledge.ini
    public boolean chatbot_is_save() throws IOException {
        if (this.input.toLowerCase().equals("save")) {
            chatbot_do_save();
            bot.bot_output("Data saved!");
            return true;
        }
        return false;
    }
    // Check if users wants a have small talk
    public boolean chatbot_is_smalltalk() {
        String[] smalltalk = {"good", "hello", "hey", "hi", "it", "its", "it's"};
        for (int i = 0; i < smalltalk.length; i++) {
            if (words[0].toLowerCase().equals(smalltalk[i])) {
                chatbot_do_smalltalk(this.words);
                return true;
            }
        }
        return false;
    }
    // Check if users wants to start sudden death
    public boolean chatbot_is_StartSuddenDeath() {
        if (isPatternMatch(this.input, "start sudden death")) {
            startSuddenDeath();
            return true;
        }
        return false;
    }
    // Check if users previously asked a question which is not in the data base.
    public boolean chatbot_is_unansweredQuestion() {
        if (this.unAnsweredQuestion) {
            answer = player.getInput();
            knowledgeList.addKnowledge(questionWords[0], entity, answer);
            this.unAnsweredQuestion = false;
            bot.bot_output("Okay Noted");
            return true;
        }
        return false;
    }
    // Check if users wants update new score into scoreboard.ini
    public boolean chatbot_is_Update_New_Score() {
        if (isPatternMatch(this.input, "^update new score$")) {
            chatbot_do_Update_New_Score();
            return true;
        }
        return false;
    }
    // Check if users wants to update old score
    public boolean chatbot_is_Update_Old_Score() {

        if (isPatternMatch(this.input, "^update score ID [0-9]+$")) {
            int idNum = Integer.parseInt(words[3]);
            if (idNum > scoreboardupdater.getLatestIdNumber()) {
                return false;
            }
            chatbot_do_Update_Old_Score(idNum);
            return true;
        }
        return false;
    }
    // remove all non alphabets and digits
    public String cleanInput(String input) {
        return input.replaceAll("[^a-zA-Z0-9. ]", "");
    }
    // Bot will output information into the text interface.
    public String getBotOutput() {
        return this.bot.getOutput();
    }
    // For terminal usage.
    public void getUserInput() {
        player.userInput(sc);
        this.input = player.getInput();
        this.input = cleanInput(this.input);
        tokenizeInput();
    }
    // Clean and tokenize user input
    public void setUserInput(String newInput) {
        player.userSetinput(newInput);
        this.input = player.getInput();
        this.input = cleanInput(this.input);
        tokenizeInput();
    }
    // Tokenize inputs into WORDS
    public void tokenizeInput() {
        // String temp = this.input.toLowerCase();
        this.words = this.input.split(" ");
    }
    // Do change bomb count according to the input
    private void chatbot_do_Change_Bomb_Count() {
        float bombNum = Float.parseFloat(words[3]);
        int bombNumInt = (int)bombNum;
        try {
            if (isPatternMatch(this.input, "^change bomb count [0-9]+ P[1-4]$")) {
                int playerNum = Integer.parseInt(words[4].substring(1));
                BoardInfo.players.get(playerNum - 1).setMaxBombs(bombNumInt);
                bot.bot_output("Bomb count has been changed to " + bombNumInt + " for player " +
                               playerNum);
            } else {
                for (int i = 0; i < BoardInfo.players.size(); i++) {
                    BoardInfo.players.get(i).setMaxBombs(bombNumInt);
                }
                bot.bot_output("Bomb count has been changed to " + bombNumInt + " for all players");
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < BoardInfo.players.size(); i++) {
                BoardInfo.players.get(i).setMaxBombs(bombNumInt);
            }
            bot.bot_output("Bomb count has been changed to " + bombNumInt + " for all players");
        }
    }
    // Do change bomb range according to the input
    private void chatbot_do_Change_Bomb_Range() {
        float bombRange = Float.parseFloat(words[3]);
        try {

            int playerNum = Integer.parseInt(words[4].substring(1));
            BoardInfo.players.get(playerNum - 1).setBombRange((int)bombRange);
            bot.bot_output("Okay! The bomb range for player " + playerNum + " has been changed");

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            for (int i = 0; i < BoardInfo.players.size(); i++) {
                BoardInfo.players.get(i).setBombRange((int)bombRange);
            }
            bot.bot_output("Okay! The bomb range for all player has been changed");
        }
    }
    // Do change death timer according to the input
    private void chatbot_do_changeDeathTimer(float seconds) {
        // Change Sudden Death Timer
        if (seconds < 50) {
            seconds = 50;
        } else if (seconds > 500) {
            seconds = 500;
        }
        bot.bot_output("Death timer changed!");
        GameInfo.SUDDEN_DEATH = (int)(seconds * GameInfo.FPS);
    }
    // Do change chance of item spawns according to the input
    private void chatbot_do_changeItemSpawn(int SpawnChance) {
        bot.bot_output("Spawn chance has changed!");
        GameInfo.ITEM_SPAWN_CHANCE = SpawnChance;
    }
    // enable user to kick bomb according to the input
    private void chatbot_do_ChangeKickBomb() {

        if (isPatternMatch(this.input, "change Kick Bomb P[1-4]$")) {
            int playerNum = Integer.parseInt(words[3].substring(1));
            BoardInfo.players.get(playerNum - 1).setKick(true);
            bot.bot_output("player " + playerNum + " can kick bombs now");
        } else {
            for (int i = 0; i < BoardInfo.players.size(); i++) {
                BoardInfo.players.get(i).setKick(true);
            }
            bot.bot_output("All Players can kick bombs now");
        }
    }
    // Do change speed according to the input
    private void chatbot_do_changeSpeed(float speedup) {
        try {
            int playerNum = Integer.parseInt(words[3].substring(1));
            BoardInfo.players.get(playerNum - 1).changeSpeed(speedup);
            bot.bot_output("Okay! The speed for player " + playerNum + " has been changed");

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            for (int i = 0; i < BoardInfo.players.size(); i++) {
                BoardInfo.players.get(i).changeSpeed(speedup);
            }
            bot.bot_output("Okay! The speed for all player has been changed");
        }
    }
    // Exit the game
    private void chatbot_do_exit() {

        bot.bot_output("ExITING");
        System.exit(1);
    }
    // Open help dialogue box
    private void chatbot_do_help() throws IOException, IllegalArgumentException {
        File file = new File("help.txt");
        if (System.getProperty("os.name").startsWith("Mac")) {
            new ProcessBuilder("open", file.getAbsolutePath()).start();
            return;
        }

        String longMessage = "", Line = "";
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            Line = scan.nextLine();
            longMessage += Line;
            longMessage += "\n";
        }
        scan.close();
        JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
        System.out.println("open(): entry - With frame");
        frame.setSize(0, 0);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JTextArea textArea = new JTextArea(15, 60);
        textArea.setFont(new Font("Arial Black", Font.TYPE1_FONT, 16));
        textArea.setText(longMessage);
        textArea.setEditable(false);

        // wrap a scrollpane around it
        JScrollPane scrollPane = new JScrollPane(textArea);

        // display them in a message dialog
        JOptionPane.showMessageDialog(frame, scrollPane, "Help Menu",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    // Do load information into chatbot from knowledge.ini
    private void chatbot_do_load() {

        try {
            knowledgeList.loadKnowledge();
            bot.bot_output("Your Score has been loaded in!");
        } catch (IOException e) {
            bot.bot_output("Failed to read!");
            System.out.println(e);
        }
    }
    // Do load score into game
    private void chatbot_do_load_Score_To_Game(int idNum) {
        try {

            scoreboardupdater.setIdNumber(idNum);
            BoardInfo.playerScore = scoreboardupdater.uploadScoreBoard();
            bot.bot_output("Score has been updated into scoreboard ini file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Bot repeats the question and prompt a reply from the user
    private void chatbot_do_question(String[] words) {
        // remove 'is' and 'are' word then input entity into the "entity" variable
        entity = "";
        for (int i = 0; i < words.length; i++) {
            if (i == 0 || (i == 1 && (words[1].toLowerCase().equals("is") ||
                                      words[1].toLowerCase().equals("to") ||
                                      words[1].toLowerCase().equals("are")))) {
                // pass
            } else {
                entity += words[i] + " ";
            }
        }
        entity = entity.trim();
        if (entity.equals("")) {
            System.out.println("Invalid Input");
        } else {
            // Check if it question is in knowledgelist

            if (knowledgeList.getKnowledge(entity, words[0])) {
                this.knowledge = knowledgeList.getTargetKnowledge();
                bot.bot_output(this.knowledge.answer);

            } else {
                questionWords = words;
                bot.bot_output("I don't know " + this.input + "?");
                this.unAnsweredQuestion = true;
            }
        }
    }
    // Do reload scoreboard.ini to find out the highest ID number
    private void chatbot_do_Reload_scoreUpdater() {
        try {
            // Set up latest Number ID
            scoreboardupdater.ReloadContent();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Do reset all information from init.ini file
    private void chatbot_do_reset() {
        bot.bot_output("Data Reset back to default");
        knowledgeList.clearKnowledge();
        chatbot_do_load("chatbot/init.ini");
    }
    // Chatbot uploads all current informatio into knowledge.ini
    private void chatbot_do_save() {
        try {
            knowledgeList.saveKnowledge();
        } catch (IOException e) {
            bot.bot_output("Failed to save!");
            System.out.println(e);
        }
    }
    // Chatbot engages in small talk
    private void chatbot_do_smalltalk(String[] words) {

        if (words[0].toLowerCase().equals("good")) {
            bot.bot_output("Good Day");
        } else if (words[0].toLowerCase().equals("it's") || words[0].toLowerCase().equals("its")) {
            bot.bot_output("Indeed It is");
        } else {
            bot.bot_output("Hello to you too");
        }
    }
    // Do Chatbot and update new score
    private void chatbot_do_Update_New_Score() {
        try {
            scoreboardupdater.setCurrentScore(BoardInfo.playerScore);
            scoreboardupdater.saveContent();
            bot.bot_output("Score Updated in scoreboard ini file!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Do Chatbot update score according to the ID in the paramter
    private void chatbot_do_Update_Old_Score(int idNum) {
        try {
            scoreboardupdater.setUpdateId(idNum);
            scoreboardupdater.setCurrentScore(BoardInfo.playerScore);
            scoreboardupdater.saveContent(idNum);
            bot.bot_output("Score has been updated to ID " + idNum + " !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Check if pattern matches the input and patternString
    private boolean isPatternMatch(String Input, String patternString) {

        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Input);
        return matcher.find();
    }
    // Activate sudden death!!
    private void startSuddenDeath() {
        bot.bot_output("Initiating Sudden deaath!!!");
        GameInfo.timeElapsed = 1055550;
    }
}
