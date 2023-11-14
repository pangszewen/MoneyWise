package com.example.madassignment.forum;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ForumTopic {
    private String topicID;
    private String userID;
    private LocalDateTime datePosted;
    private String subject;
    private String description;
    private int likes;
    private ArrayList<String> commentID;

    public ForumTopic(String topicID, String userID, String subject, String description) {
        this.topicID = topicID;
        this.userID = userID;
        this.datePosted = LocalDateTime.now();
        this.subject = subject;
        this.description = description;
        this.likes = 0;
        this.commentID = new ArrayList<>();
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getCommentID() {
        return commentID;
    }

    public void setCommentID(ArrayList<String> commentID) {
        this.commentID = commentID;
    }
}
