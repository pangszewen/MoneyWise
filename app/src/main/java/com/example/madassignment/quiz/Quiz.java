package com.example.madassignment.quiz;

public class Quiz {
    private String quizID;
    private String quizTitle;
    private String advisorID;
    private Question ques;

    private Integer numOfQues;

    public Quiz(){}
    public Quiz(String quizID, String quizTitle,String adivisorID, Integer numOfQues) {
        this.quizID = quizID;
        this.quizTitle = quizTitle;
        this.advisorID = adivisorID;
        this.numOfQues = numOfQues;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Question getQues() {
        return ques;
    }

    public void setQues(Question ques) {
        this.ques = ques;
    }

    public String getAdvisorID() {
        return advisorID;
    }

    public void setAdvisorID(String advisorID) {
        this.advisorID = advisorID;
    }
    public Integer getNumOfQues() {return numOfQues;}
    public void setNumOfQues(Integer numOfQues) {this.numOfQues = numOfQues;}
}
