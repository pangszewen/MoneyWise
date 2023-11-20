package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madassignment.Firebase;
import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Forum_CreateTopic_Activity extends AppCompatActivity {
    Firebase firebase = new Firebase();
    FirebaseFirestore db;
    CollectionReference collectionReference;
    Random rand = new Random();
    EditText ETTopicSubject, ETTopicDescription;
    Button btn_createTopic;
    ArrayList<ForumTopic> forumData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_create_topic);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ETTopicSubject = findViewById(R.id.ETTopicSubject);
        ETTopicDescription = findViewById(R.id.ETTopicDescription);
        btn_createTopic = findViewById(R.id.btn_createTopic);

        btn_createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TopicSubject = ETTopicSubject.getText().toString();
                String TopicDescription = ETTopicDescription.getText().toString();
                createTopic(TopicSubject, TopicDescription);
                startActivity(new Intent(Forum_CreateTopic_Activity.this, Forum_MainActivity.class));
            }
        });
    }

    private void createTopic(String TopicSubject, String TopicDescription){
        String topicID = generateTopicID();
        String userID = "L0000000";
        ForumTopic newTopic = new ForumTopic(topicID, userID, TopicSubject, TopicDescription);
        Log.d("TAG", "topic created" + newTopic.getTopicID() + ": " + newTopic.getSubject());
        insertTopicIntoDatabase(newTopic);
    }

    private void insertTopicIntoDatabase(ForumTopic topic){
        db = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put("userID", topic.getUserID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = topic.getDatePosted().format(formatter);
        map.put("datePosted", formattedDateTime);
        map.put("subject", topic.getSubject());
        map.put("description", topic.getDescription());
        map.put("likes", topic.getLikes());
        map.put("commentID", topic.getCommentID());
        db.collection("FORUM_TOPIC").document(topic.getTopicID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Forum_CreateTopic_Activity.this, "Topic Successfully Posted", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "uploaded");
                }else {
                    Toast.makeText(Forum_CreateTopic_Activity.this, "Topic Failed to Post", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "Failed");
                }
            }
        });
    }

    private String generateTopicID(){
        String newID = null;
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = "T" + String.format("%07d", randomNum); //T0001000
            if(checkDuplicatedTopicID(newID))
                break;
        }
        Log.d("TAG", "This is new topicID " + newID);
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID){
        firebase.getForumData();
        forumData = new ArrayList<>(firebase.getForumTopics());
        for(ForumTopic topic: forumData){
            Log.d("TAG", "iterate through documents");
            Log.d("TAG", topic.getTopicID());
            if(newID.equals(topic.getTopicID()))
                return false;
        }
        Log.d("TAG", "This is checked topic ID " + newID);
        return true;
    }


}