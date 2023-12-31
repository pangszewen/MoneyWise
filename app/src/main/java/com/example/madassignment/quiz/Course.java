package com.example.madassignment.quiz;

import android.net.Uri;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Course {
    private String courseID;
    private String courseTitle;
    private String advisorID;
    private LocalDateTime dateCreated;
    private String courseDesc;
    private String courseLevel;
    private String courseLanguage;
    private String courseMode;
    private String advisorName;
//    private Integer courseNumOfStudents;
    private int lessonNum;
    private int lessonProgress;
    private Uri coverImageUri;

    public Course(){
    }

    public Course(String courseID, String advisorID, String courseTitle, String courseDesc, String courseLevel, String courseLanguage, String courseMode){
        this.courseID = courseID;
        this.advisorID = advisorID;
        this.courseTitle = courseTitle;
        this.courseDesc = courseDesc;
        this.courseLevel = courseLevel;
        this.courseLanguage = courseLanguage;
        this.courseMode = "Online";
        this.dateCreated = LocalDateTime.now();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime datePosted) {
        this.dateCreated = datePosted;
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

    public void setDateCreated(String dateCreated) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm")
                .optionalStart()
                .appendPattern(":ss")
                .optionalEnd()
                .toFormatter();
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

    public Uri getCoverImageUri() {
        return coverImageUri;
    }

    public void setCoverImageUri(Uri coverImageUri) {
        this.coverImageUri = coverImageUri;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public int getLessonNum() {return lessonNum;}

    public void setLessonNum(int lessonNum) {this.lessonNum = lessonNum;}

    public int getLessonProgress() {return lessonProgress;}

    public void setLessonProgress(int lessonProgress) {this.lessonProgress = lessonProgress;}
}
