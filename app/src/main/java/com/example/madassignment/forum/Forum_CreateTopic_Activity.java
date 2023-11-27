package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Forum_CreateTopic_Activity extends AppCompatActivity {
    FirebaseFirestore db;
    CollectionReference collectionReference;
    Random rand = new Random(1000000);
    EditText ETTopicSubject, ETTopicDescription;
    Button btn_createTopic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_create_topic);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        db.collection("FORUM_TOPIC");

        ETTopicSubject = findViewById(R.id.title_input);
        ETTopicDescription = findViewById(R.id.question_input);
        btn_createTopic = findViewById(R.id.create_quiz_button);

        btn_createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TopicSubject = ETTopicSubject.getText().toString();
                String TopicDescription = ETTopicDescription.getText().toString();
                if(createTopic(TopicSubject, TopicDescription)) {
                    Toast.makeText(Forum_CreateTopic_Activity.this, "Topic Successfully Posted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forum_CreateTopic_Activity.this, Forum_MainActivity.class));
                }else
                    Toast.makeText(Forum_CreateTopic_Activity.this, "Topic Failed to Post", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean createTopic(String TopicSubject, String TopicDescription){
        String topicID = generateTopicID();
        String userID = "L0000000";
        ForumTopic newTopic = new ForumTopic(topicID, userID, TopicSubject, TopicDescription);

        return insertTopicIntoDatabase(newTopic);
    }

    private boolean insertTopicIntoDatabase(ForumTopic topic){
        final AtomicBoolean result = new AtomicBoolean(false);
        Map<String, Object> map = new HashMap<>();
        map.put("userID", topic.getUserID());
        map.put("datePosted", topic.getDatePosted());
        map.put("subject", topic.getSubject());
        map.put("description", topic.getDescription());
        map.put("likes", topic.getLikes());
        map.put("commentID", topic.getCommentID());
        db.collection("FORUM_TOPIC").document(topic.getTopicID()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                result.set(true);
            }
        });
        return result.get();
    }

    private String generateTopicID(){
        String newID = null;
        while(true) {
            int randomNum = rand.nextInt();
            newID = "T" + String.format("%07d", randomNum); //T0001000
            if(checkDuplicatedTopicID(newID))
                break;
        }
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID){
        ArrayList<DocumentSnapshot> documentData = getDocumentData();
        for(DocumentSnapshot document : documentData){
            if(newID.equals(document.getId()))
                return false;
        }
        return true;
    }

    private ArrayList<DocumentSnapshot> getDocumentData(){
        ArrayList<DocumentSnapshot> documentData = new ArrayList<>();
        // Specify the collection reference
        collectionReference = db.collection("FORUM_TOPIC");
        // Retrieve all documents in the collection
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        documentData.add(document);
                    }
                }
            }
        });
        return documentData;
    }
}