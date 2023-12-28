package com.example.madassignment.quiz;

public class Lesson {
    private String lessonTitle;
    private String lessonDuration;
    private String lessonUrl;

    public Lesson(String lessonTitle, String lessonDuration) {
        this.lessonTitle = lessonTitle;
        this.lessonDuration = lessonDuration;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonDuration() {
        return lessonDuration;
    }
//
    public void setLessonDuration(String lessonDuration) {
        this.lessonDuration = lessonDuration;
    }

    public String getLessonUrl() {
        return lessonUrl;
    }

    public void setLessonUrl(String lessonUrl) {
        this.lessonUrl = lessonUrl;
    }
}
