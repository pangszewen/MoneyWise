package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.RandomAccessFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Forum_IndividualTopic_Activity extends AppCompatActivity {
    FirebaseFirestore db;
    Random rand = new Random();
    TextView TVSubject, TVAuthor, TVDatePosted, TVDescription;
    EditText ETComment;
    Button btn_comment;
    ImageButton backButton_individualTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG","IndividualTopic");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_individual_topic);
        db = FirebaseFirestore.getInstance();
        TVSubject = findViewById(R.id.TVSubject);
        TVAuthor = findViewById(R.id.TVAuthor);
        TVDatePosted = findViewById(R.id.TVDatePosted);
        TVDescription = findViewById(R.id.TVDescription);
        ETComment = findViewById(R.id.ETComment);
        btn_comment = findViewById(R.id.btn_postComment);
        backButton_individualTopic = findViewById(R.id.backButton_individualTopic);
        ForumTopic topic = new ForumTopic();

        Log.d("TAG", getIntent().getStringExtra("topicID"));
        topic.setTopicID(getIntent().getStringExtra("topicID"));
        topic.setUserID(getIntent().getStringExtra("userID"));
        topic.setDatePosted(getIntent().getStringExtra("datePosted"));
        topic.setSubject(getIntent().getStringExtra("subject"));
        topic.setDescription(getIntent().getStringExtra("description"));
        topic.setLikes(Integer.parseInt(getIntent().getStringExtra("likes")));
        topic.setCommentID(getIntent().getStringExtra("commentID"));

        Log.d("TAG", topic.getSubject());

        TVSubject.setText(topic.getSubject());
        TVAuthor.setText(TVAuthor.getText() + "Mary");
        DateTimeFormatter formatterString = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedTopicDate = topic.getDatePosted().format(formatterString);
        TVDatePosted.setText(TVDatePosted.getText() + formattedTopicDate);
        TVDescription.setText(topic.getDescription());

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ETComment.getText()!=null){
                    String comment = ETComment.getText().toString();
                    createComment(topic, comment);
                }
            }
        });

        backButton_individualTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_IndividualTopic_Activity.this, Forum_MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createComment(ForumTopic topic, String content){
        CollectionReference collectionReference = db.collection("FORUM_COMMENT");
        collectionReference.orderBy("datePosted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<ForumComment> forumCommentList = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    ForumComment comment =  convertDocumentToForumComment(dc);
                    forumCommentList.add(comment);
                }
                String commentID = generateCommentID(topic, forumCommentList);
                ForumComment forumComment = new ForumComment(commentID, topic.getTopicID(), content);
                insertCommentIntoDatabase(forumComment);
            }
        });
    }

    private void insertCommentIntoDatabase(ForumComment comment){
        db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("commentID", comment.getCommentID());
        map.put("topicID", comment.getTopicID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = comment.getDatePosted().format(formatter);
        map.put("datePosted", formattedDateTime);
        map.put("content", comment.getContent());
        db.collection("FORUM_COMMENT").document(comment.getCommentID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Forum_IndividualTopic_Activity.this, "Comment Successfully Posted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Forum_IndividualTopic_Activity.this, "Comment Failed to Post", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateCommentInTopic(comment);
    }

    public void updateCommentInTopic(ForumComment comment){
        DocumentReference ref = db.collection("FORUM_TOPIC").document(comment.getTopicID());
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot dc = task.getResult();
                ForumTopic topic = convertDocumentToForumTopic(dc);
                ArrayList<String> commentID = topic.getCommentID();
                commentID.add(comment.getCommentID());
                ref.update("comment", commentID);
            }
        });

    }

    private String generateCommentID(ForumTopic topic, ArrayList<ForumComment> comments){
        String newID = null;
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = topic.getTopicID() + "_" + String.format("%07d", randomNum); //T0001000
            if(checkDuplicatedTopicID(newID, comments))
                break;
        }
        Log.d("TAG", "This is new commentID " + newID);
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID, ArrayList<ForumComment> comments){
        for(ForumComment comment : comments){
            if(newID.equals(comment.getCommentID()))
                return false;
        }
        Log.d("TAG", "This is checked topic ID " + newID);
        return true;
    }

    public ForumComment convertDocumentToForumComment(QueryDocumentSnapshot dc){
        ForumComment comment = new ForumComment();
        comment.setCommentID(dc.getId());
        comment.setTopicID(dc.get("topicID").toString());
        comment.setDatePosted(dc.get("datePosted").toString());
        comment.setContent(dc.get("content").toString());

        return comment;
    }

    public ForumTopic convertDocumentToForumTopic(DocumentSnapshot dc){
        ForumTopic topic = new ForumTopic();
        topic.setTopicID(dc.getId());
        topic.setUserID(dc.get("userID").toString());
        topic.setDatePosted(dc.get("datePosted").toString());
        topic.setSubject(dc.get("subject").toString());
        topic.setDescription(dc.get("description").toString());

        // cast the returned Object to Long, then convert it to an int
        topic.setLikes((Long)dc.get("likes"));

        // Firestore retrieves List objects as List<Object> and not as ArrayList<String>
        topic.setCommentID ((List<Object>) dc.get("commentID"));

        return topic;
    }
}