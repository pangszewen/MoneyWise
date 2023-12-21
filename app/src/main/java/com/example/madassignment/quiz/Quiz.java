package com.example.madassignment.quiz;

public class Quiz {
    private String quizID;
    private String quizTitle;
    private String advisorID;
    private Question ques;

    public Quiz(){}
    public Quiz(String quizID, String quizTitle,String adivisorID) {
        this.quizID = quizID;
        this.quizTitle = quizTitle;
//        this.ques = ques;
        this.advisorID = adivisorID;
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
}
