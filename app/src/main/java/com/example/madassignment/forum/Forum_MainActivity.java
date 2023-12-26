package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Forum_MainActivity extends AppCompatActivity {
    RecyclerView RVForum;
    Forum_Adapter forumAdapter;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    List<ForumTopic> forumTopics;
    Button btn_myTopic;
    SwipeRefreshLayout RVForumRefresh;

    public Forum_MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);

        bottomNavigationView = findViewById(R.id.bottomForumNavigationView);
        bottomNavigationView.setBackground(null);   //To eliminate shadow of navigation bar view
        MenuItem menuItemDisable = bottomNavigationView.getMenu().findItem(R.id.iconForum);
        menuItemDisable.setEnabled(false);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID==R.id.iconHome) {
                    startActivity(new Intent(Forum_MainActivity.this, HomeActivity.class));
                    return true;
                }else if(itemID==R.id.iconForum) {
                    startActivity(new Intent(Forum_MainActivity.this, Forum_MainActivity.class));
                    return true;
                }else
                    return false;
            }
        });

        btn_myTopic = findViewById(R.id.btn_myTopic);
        btn_myTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forum_MainActivity.this, Forum_MyTopic_Activity.class));
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        RVForum = findViewById(R.id.RVForum);
        setUpRVForum();

        RVForumRefresh = findViewById(R.id.RVForumRefresh);
        RVForumRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUpRVForum();
                RVForumRefresh.setRefreshing(false);
            }
        });


    }

    public void setUpRVForum(){
        forumTopics = new ArrayList<>();

        CollectionReference collectionReference = db.collection("FORUM_TOPIC");
        collectionReference.orderBy("datePosted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<ForumTopic> forumTopicList = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    ForumTopic topic = convertDocumentToForumTopic(dc);
                    forumTopicList.add(topic);
                }
                forumAdapter = new Forum_Adapter(Forum_MainActivity.this, forumTopicList);
                prepareRecyclerView(RVForum, forumTopicList);
            }
        });

    }

    public void prepareRecyclerView(RecyclerView RV, List<ForumTopic> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapter(RV, object);
    }

    public void preAdapter(RecyclerView RV, List<ForumTopic> object){
        forumAdapter = new Forum_Adapter(this, object);
        RV.setAdapter(forumAdapter);
    }

    public ForumTopic convertDocumentToForumTopic(QueryDocumentSnapshot dc){
        ForumTopic topic = new ForumTopic();
        topic.setTopicID(dc.getId());
        topic.setUserID(dc.get("userID").toString());
        topic.setDatePosted(dc.get("datePosted").toString());
        topic.setSubject(dc.get("subject").toString());
        topic.setDescription(dc.get("description").toString());
        // Firestore retrieves List objects as List<Object> and not as ArrayList<String>
        topic.setLikes((List<String>)dc.get("likes"));
        topic.setCommentID ((List<String>) dc.get("commentID"));

        return topic;
    }

    /*
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.CLForumMain, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

     */
}