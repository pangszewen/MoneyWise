package com.example.madassignment.quiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Course {
    private String courseID;
    private String courseTitle;
    private String advisorID;
    private LocalDateTime dateCreated;
    private String courseDesc;
    private String courseLevel;
    private String courseLanguage;
    private String courseMode;
    private Integer courseNumOfStudents;

    public Course(){
    }

    public Course(String courseID, String advisorID, String courseTitle, String courseDesc, String courseLevel, String courseLanguage, String courseMode,Integer courseNumOfStudents){
        this.courseID = courseID;
        this.advisorID = advisorID;
        this.courseTitle = courseTitle;
        this.courseNumOfStudents = courseNumOfStudents;
        this.courseDesc = courseDesc;
        this.courseLevel = courseLevel;
        this.courseLanguage = courseLanguage;
        this.courseMode = "Online";
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

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseLanguage() {
        return courseLanguage;
    }

    public void setCourseLanguage(String courseLanguage) {
        this.courseLanguage = courseLanguage;
    }

    public String getCourseMode() {
        return courseMode;
    }

    public void setCourseMode(String courseMode) {
        this.courseMode = courseMode;
    }

    public Integer getCourseNumOfStudents() {
        return courseNumOfStudents;
    }

    public void setCourseNumOfStudents(Integer courseNumOfStudents) {
        this.courseNumOfStudents = getCourseNumOfStudents()+1;
    }
}
