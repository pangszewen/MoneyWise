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
import android.widget.Toast;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Forum_CreateTopic_Activity extends AppCompatActivity {
    FirebaseFirestore db;
    CollectionReference collectionReference;
    Random rand = new Random();
    EditText ETTopicSubject, ETTopicDescription;
    Button btn_createTopic;
    ImageButton backButton_createTopic;
    FirebaseAuth auth;
    FirebaseUser user;
    List<ForumTopic> forumData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Forum_MainActivity forumMainActivity = new Forum_MainActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_create_topic);
        db = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        ETTopicSubject = findViewById(R.id.ETTopicSubject);
        ETTopicDescription = findViewById(R.id.ETTopicDescription);
        btn_createTopic = findViewById(R.id.btn_createTopic);
        backButton_createTopic = findViewById(R.id.backButton_createTopic);

        backButton_createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_CreateTopic_Activity.this, Forum_MyTopic_Activity.class);
                startActivity(intent);
            }
        });

        btn_createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TopicSubject = ETTopicSubject.getText().toString();
                String TopicDescription = ETTopicDescription.getText().toString();
                createTopic(TopicSubject, TopicDescription);
                Intent intent = new Intent(Forum_CreateTopic_Activity.this, Forum_MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createTopic(String TopicSubject, String TopicDescription){
        Forum_MainActivity forumMainActivity = new Forum_MainActivity();
        CollectionReference collectionReference = db.collection("FORUM_TOPIC");
        collectionReference.orderBy("datePosted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {;
                List<ForumTopic> forumTopicList = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    ForumTopic topic =  forumMainActivity.convertDocumentToForumTopic(dc);
                    forumTopicList.add(topic);
                }
                String topicID = generateTopicID(forumTopicList);
                String userID = user.getUid();
                ForumTopic newTopic = new ForumTopic(topicID, userID, TopicSubject, TopicDescription);
                insertTopicIntoDatabase(newTopic);
            }
        });
    }

    private void insertTopicIntoDatabase(ForumTopic topic){
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
                }else {
                    Toast.makeText(Forum_CreateTopic_Activity.this, "Topic Failed to Post", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String generateTopicID(List<ForumTopic> topics){
        String newID = null;
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = "T" + String.format("%07d", randomNum); //T0001000
            if(checkDuplicatedTopicID(newID, topics))
                break;
        }
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID, List<ForumTopic> topics){
        for(ForumTopic topic: topics){
            if(newID.equals(topic.getTopicID()))
                return false;
        }
        return true;
    }
}