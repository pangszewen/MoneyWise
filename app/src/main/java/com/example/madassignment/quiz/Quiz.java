package com.example.madassignment.quiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Quiz {
    private String quizID;
    private String quizTitle;
    private String advisorID;
    private LocalDateTime dateCreated;

    public Quiz(){
    }

    public Quiz(String quizID, String advisorID, String quizTitle){
        this.quizID = quizID;
        this.advisorID = advisorID;
        this.quizTitle = quizTitle;
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

    public String getAdvisorID() {
        return advisorID;
    }

    public void setAdvisorID(String advisorID) {
        this.advisorID = advisorID;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dateCreated = LocalDateTime.parse(dateCreated, formatter);
    }
}
