package com.example.moneywise.forum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moneywise.R;
import com.example.moneywise.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Forum_MyTopic_Activity extends AppCompatActivity {
    MyTopic_Adapter myTopicAdapter;
    FirebaseAuth auth;
    FirebaseUser user;

    Firebase_Forum firebase = new Firebase_Forum();
    String userID;
    RecyclerView RVMyTopics;
    ImageButton backButton_myTopic, btn_createTopic;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Forum_MainActivity forumMainActivity = new Forum_MainActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_my_topic);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //userID = user.getUid();
        userID = "Zqa2pZRzccPx13bEjxZho9UVlT83";
        RVMyTopics = findViewById(R.id.RVMyTopics);
        backButton_myTopic = findViewById(R.id.backButton_myTopics);
        btn_createTopic = findViewById(R.id.btn_createTopic);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        setUpRVMyTopics();

        backButton_myTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_MyTopic_Activity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        btn_createTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum_MyTopic_Activity.this, Forum_CreateTopic_Activity.class);
                intent.putExtra("class", Forum_MyTopic_Activity.class.toString());
                startActivity(intent);
                finish();
            }
        });
    }

    public void setUpRVMyTopics(){
        firebase.getForumTopicsInOrder(new Firebase_Forum.ForumTopicInOrderCallback() {
            @Override
            public void onForumTopicsReceived(ArrayList<ForumTopic> forumTopics) {
                ArrayList<ForumTopic> myTopicList = new ArrayList<>();
                for(ForumTopic topic : forumTopics){
                    if(topic.getUserID().equals(userID)) {
                        myTopicList.add(topic);
                    }
                }
                prepareRecyclerView(Forum_MyTopic_Activity.this, RVMyTopics, myTopicList);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void prepareRecyclerView(Context context, RecyclerView RV, List<ForumTopic> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapter(context, RV, object);
    }

    public void preAdapter(Context context, RecyclerView RV, List<ForumTopic> object){
        myTopicAdapter = new MyTopic_Adapter(context, object);
        RV.setAdapter(myTopicAdapter);
    }
}