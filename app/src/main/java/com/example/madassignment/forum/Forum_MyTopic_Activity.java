package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Forum_MyTopic_Activity extends AppCompatActivity {
    Forum_Adapter forumAdapter;
    FirebaseFirestore db;
    RecyclerView RVMyTopics;
    ImageButton backButton_myTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Forum_MainActivity forumMainActivity = new Forum_MainActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_my_topic);

        db = FirebaseFirestore.getInstance();
        RVMyTopics = findViewById(R.id.RVMyTopics);
        backButton_myTopic = findViewById(R.id.backButton_myTopic);

        backButton_myTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forum_MyTopic_Activity.this, Forum_MainActivity.class));
            }
        });

        CollectionReference collectionReference = db.collection("FORUM_TOPIC");
        collectionReference.orderBy("datePosted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<ForumTopic> myTopicList = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    if(dc.get("userID").toString().equals("L0000000")) {
                        ForumTopic topic = forumMainActivity.convertDocumentToForumTopic(dc);
                        myTopicList.add(topic);
                    }
                }
                forumAdapter = new Forum_Adapter(Forum_MyTopic_Activity.this, myTopicList);
                forumMainActivity.prepareRecyclerView(RVMyTopics, myTopicList);
            }
        });
    }
}