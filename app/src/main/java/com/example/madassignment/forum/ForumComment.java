package com.example.madassignment.forum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForumComment {
    private String commentID;
    private String topicID;
    private LocalDateTime datePosted;
    private String content;

    public ForumComment(){}

    public ForumComment(String commentID, String topicID, String content){
        this.commentID = commentID;
        this.topicID = topicID;
        this.datePosted = LocalDateTime.now();
        this.content = content;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
