package com.example.madassignment.quiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Course {
    private String courseID;
    private String courseTitle;
    private String advisorID;
    private LocalDateTime dateCreated;
    private String courseDesc;

    public Course(){
    }

    public Course(String courseID, String advisorID, String courseTitle, String courseDesc){
        this.courseID = courseID;
        this.advisorID = advisorID;
        this.courseTitle = courseTitle;
        this.courseDesc = courseDesc;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String description) {
        this.courseDesc = description;
    }
}
