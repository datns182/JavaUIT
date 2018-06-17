package model;

import java.util.ArrayList;


public class Question {
    private String content;
    private int questionID;
    private ArrayList<Answer> answers;

    public String getContent() {
        return content;
    }

    public Question(int questionId, String content, ArrayList<Answer> answers) {
        this.content = content;
        this.questionID = questionID;
        this.answers = answers;

    }


    @Override
    public String toString() {
        String temp = this.content + "\n\n";
        String letter = "ABCD";
        int index = 0;
        for (Answer a : answers) {
            temp = temp + letter.charAt(index) + ". " + a.getAnswer()  + "\n";
            index++;
        }
        return temp;
    }


    public ArrayList<Answer> getAnswers() {
        return answers;
    }

}
