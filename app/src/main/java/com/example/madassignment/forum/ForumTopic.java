package com.example.madassignment.forum;

import android.util.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForumTopic {
    private String topicID;
    private String userID;
    private LocalDateTime datePosted;
    private String subject;
    private String description;
    private int likes;
    private ArrayList<String> commentID;

    public ForumTopic(){

    }

    public ForumTopic(String topicID, String userID, String subject, String description) {
        this.topicID = topicID;
        this.userID = userID;
        this.datePosted = LocalDateTime.now();
        this.subject = subject;
        this.description = description;
        this.likes = 0;
        this.commentID = new ArrayList<>();
    }

    public ForumTopic(String topicID, String userID, LocalDateTime datePosted, String subject, String description, int likes, ArrayList<String> commentID) {
        this.topicID = topicID;
        this.userID = userID;
        this.datePosted = datePosted;
        this.subject = subject;
        this.description = description;
        this.likes = likes;
        this.commentID = commentID;
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
    public void setDatePosted(String datePosted){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.datePosted = LocalDateTime.parse(datePosted, formatter);
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

    public void setLikes(Long likes){
        this.likes = likes != null ? likes.intValue() : 0;
    }

    public ArrayList<String> getCommentID() {
        return commentID;
    }

    public void setCommentID(ArrayList<String> commentID) {
        this.commentID = commentID;
    }

    public void setCommentID(List<Object> commentIDObjects){
        ArrayList<String> commentID = new ArrayList<>();
        if (commentIDObjects != null) {
            for (Object obj : commentIDObjects) {
                if (obj instanceof String) {
                    commentID.add((String) obj);
                }
            }
        }
        this.commentID = commentID;
    }

    public void setCommentID(String commentString){
        commentString = commentString.substring(1,commentString.length()-1);
        String[] commentArray = commentString.split(",");
        this.commentID = new ArrayList<>(Arrays.asList(commentArray));
    }
}
