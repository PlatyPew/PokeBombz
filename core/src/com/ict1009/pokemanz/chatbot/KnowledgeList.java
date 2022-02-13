package com.ict1009.pokemanz.chatbot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
class KnowledgeList{
    private Knowledge newKnowledge;
    private String answer;
    private final int totalNumberOfCategory = 3;
    private Knowledge targetKnowledge;
    private ArrayList<Knowledge>[] listOfKnowledge;

    public KnowledgeList(){
        listOfKnowledge = new ArrayList[totalNumberOfCategory];
//        Knowledge newKnowledge = null, targetKnowledge = null;
        for(int i = 0; i < listOfKnowledge.length; i++) {
        	listOfKnowledge[i] = new ArrayList<Knowledge>();
        }
        
        
    }
    public Knowledge getTargetKnowledge(){
        return this.targetKnowledge;
    }
    public int choosePageNumber(String firstWord){
        if(firstWord.toLowerCase().equals("what")){
            return 0;
        }
        else if(firstWord.toLowerCase().equals("who")){
            return 1;
        }
        else if(firstWord.toLowerCase().equals("how")){
            return 2;
        }
        else return -1;
    }
    public int addKnowledge( String firstWord, String entity, String answer){
        
        int page = choosePageNumber(firstWord);
        removeKnowledge( entity,  page);
        if (page == -1){
            System.out.println("ERROR");
            return -1;
        }
        newKnowledge = new Knowledge(firstWord, entity,answer);
        listOfKnowledge[page].add(newKnowledge);
        return 0;
    }
    public void removeKnowledge(String entity, int page){
        int size = listOfKnowledge[page].size();
        for(int i = 0; i < size; i++){
            if(listOfKnowledge[page].get(i).question.equals(entity)){
                listOfKnowledge[page].remove(i);
                size -=1;
            }
        }
    }
    public boolean getKnowledge(String entity, String firstWord){
        int page = choosePageNumber(firstWord);
        if (page == -1){
            return false;
        }
        
        for(int i = 0; i < listOfKnowledge[page].size(); i++){
            if(listOfKnowledge[page].get(i).question.equals(entity)){
                this.targetKnowledge = this.listOfKnowledge[page].get(i);
                return true;
            }
        }
        return false;
    }

    public void clearKnowledge(){
        for(int i=0; i<this.totalNumberOfCategory; i++){
            listOfKnowledge[i].clear();
        }
    }
    public void saveKnowledge() throws IOException{
        String question, answer,content ="";
        
        for(int i=0; i<this.totalNumberOfCategory; i++){
            content += ("[" + saveKnowledgeIntent(i) +"]\n");
            int size = listOfKnowledge[i].size();
            for(int j = 0; j < size; j++){
                question = listOfKnowledge[i].get(j).question;
                answer =listOfKnowledge[i].get(j).answer;
                content += (question + "=" +answer+"\n");
            }
            content += ("\n");
        }
        content = content.trim();
        FileWriter writer = new FileWriter("knowledge.ini",false);
        writer.write(content);
        writer.close();
    }
    public String saveKnowledgeIntent(int page){
        if(page == 0)
            return "what";
        else if(page ==1)
            return "who";
        else return "how";
    }
    public void loadKnowledge() throws IOException{
        File file = new File("knowledge.ini");
        Scanner scan = new Scanner(file);
        String content = "", line = "", firstWord ="";
        String [] questionAndAnswer;
        while(scan.hasNextLine()){
            line = scan.nextLine();
            if(line != ""){
                if(line.equals("[what]")){
                    firstWord = "what";
                }
                else if(line.equals("[who]")){
                    firstWord = "who";
                }
                else if(line.equals("[how]")){
                    firstWord = "how";
                }
                else{
                    questionAndAnswer = line.split("=",2);
                    questionAndAnswer[0] = cleanInput(questionAndAnswer[0]);
                    questionAndAnswer[1] = cleanInput(questionAndAnswer[1]);
                    addKnowledge(firstWord,questionAndAnswer[0].toLowerCase(),questionAndAnswer[1]);
                }
                
            }

            
        }
        scan.close();
    }

    public void loadKnowledge(String input) throws IOException{
        File file = new File(input);
        Scanner scan = new Scanner(file);
        String content = "", line = "", firstWord ="";
        String [] questionAndAnswer;
        while(scan.hasNextLine()){
            line = scan.nextLine();
            if(line != ""){
                if(line.equals("[what]")){
                    firstWord = "what";
                }
                else if(line.equals("[who]")){
                    firstWord = "who";
                }
                else if(line.equals("[how]")){
                    firstWord = "how";
                }
                else{
                    questionAndAnswer = line.split("=",2);
                    questionAndAnswer[0] = cleanInput(questionAndAnswer[0]);
                    questionAndAnswer[1] = cleanInput(questionAndAnswer[1]);
                    addKnowledge(firstWord,questionAndAnswer[0].toLowerCase(),questionAndAnswer[1]);
                }
                
            }

            
        }
        scan.close();
    }
    public String cleanInput(String data){
        return data.replaceAll("[^a-zA-Z0-9 ]","");
    } 
    

}
