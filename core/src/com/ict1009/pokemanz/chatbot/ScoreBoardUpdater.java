package com.ict1009.pokemanz.chatbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreBoardUpdater {
    private String content, Line, scoreString, newContent;
    private int IDNumber, LatestIdNumber, UpdateIDNumber;
    private int[] allScores = new int[4], currentScore = {4, 3, 5, 6};
    public ScoreBoardUpdater() {
        Line = "";
        IDNumber = 2;
        content = "";
        newContent = "";
        UpdateIDNumber = 5;
        setLatestIdNumber(0);
    }
    private boolean isPatternMatch(String Input, String patternString) {

        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(Input);
        return matcher.find();
    }
    public void setCurrentScore(int [] newScore) {
    	this.currentScore = newScore;
    }
    public int[] uploadScoreBoard() throws FileNotFoundException {
        int Count = 4;
        File file = new File("scoreBoard.ini");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            Line = scan.nextLine();

            if (Count < 4) {
                scoreString = Line;
                int PlayerNum = Count + 1;
                scoreString = Line.replace("Player" + PlayerNum + "=", "");
                int score = Integer.parseInt(scoreString);
                allScores[Count] = score;
                Count++;
            }
            if (isPatternMatch(Line, "^ID=" + IDNumber + "+$")) {
                Count = 0;
            }
            this.content += Line;
            this.content += "\n";
        }
        System.out.println(content);
        return allScores;
    }
    public void ReloadContent() throws FileNotFoundException {
        this.content = "";
        this.setLatestIdNumber(1);

        File file = new File("scoreBoard.ini");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            Line = scan.nextLine();
            this.content += Line;
            this.content += "\n";
            if (isPatternMatch(Line, "^ID=[0-9]+$")) {
                setLatestIdNumber(getLatestIdNumber() + 1);
            }
        }
        System.out.println(content);
    }
	public void saveContent() throws IOException {
		this.newContent = "";
		newContent = String.format("\nID=%d\nPlayer1=%d\nPlayer2=%d\nPlayer3=%d\nPlayer4=%d",
				this.LatestIdNumber,currentScore[0],currentScore[1],currentScore[2],currentScore[3]);
		
		System.out.println(this.content + newContent);
      FileWriter writer = new FileWriter("scoreBoard.ini",false);
      writer.write(this.content + newContent);
      writer.close();
	}
	
	// Update old score to 
	public void saveContent(int IdNum) throws IOException {
		this.newContent = "";
		this.content = "";
		this.UpdateIDNumber=IdNum;
		newContent = String.format("ID=%d\nPlayer1=%d\nPlayer2=%d\nPlayer3=%d\nPlayer4=%d\n",
				this.UpdateIDNumber,currentScore[0],currentScore[1],currentScore[2],currentScore[3]);
		File file = new File("scoreBoard.ini");
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine()) {
	      	Line = scan.nextLine();
	 
	      	if(isPatternMatch(Line,"^ID=" + UpdateIDNumber +"$")) {
	      		this.content += this.newContent;
	      		for(int i=0; i<4; i++) {
	      			scan.nextLine();
	      		}
	      	}
	      	else {
	         	this.content += Line;
	          	this.content += "\n";
      	}
      }
        System.out.println(this.content + newContent);
        FileWriter writer = new FileWriter("scoreBoard.ini", false);
        writer.write(this.content);
        writer.close();
    }

    // Update old score to
    public void updateOldContent() throws IOException {
        this.newContent = "";
        this.content = "";
        newContent = String.format("ID=%d\nPlayer1=%d\nPlayer2=%d\nPlayer3=%d\nPlayer4=%d",
                                   this.UpdateIDNumber, currentScore[0], currentScore[1],
                                   currentScore[2], currentScore[3]);
        File file = new File("scoreBoard.ini");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            Line = scan.nextLine();

            if (isPatternMatch(Line, "^ID=" + UpdateIDNumber + "$")) {
                this.content += this.newContent;
                for (int i = 0; i < 4; i++) {
                    scan.nextLine();
                }
            } else {
                this.content += Line;
                this.content += "\n";
            }
        }
        FileWriter writer = new FileWriter("scoreBoard.ini", false);
        writer.write(this.content);
        writer.close();
        System.out.println(content);
    }

    public int getLatestIdNumber() {
        return LatestIdNumber;
    }
    public void setLatestIdNumber(int latestIdNumber) {
        LatestIdNumber = latestIdNumber;
    }
    public void setIdNumber(int idNum) {
        this.IDNumber = idNum;
    }
    public void setUpdateId(int idNum) {
        UpdateIDNumber = idNum;
    }
}
