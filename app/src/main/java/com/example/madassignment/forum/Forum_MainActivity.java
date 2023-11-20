package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.madassignment.Firebase;
import com.example.madassignment.MainActivity;
import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Forum_MainActivity extends AppCompatActivity {
    RecyclerView RVForum;
    Forum_Adapter forumAdapter;
    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    Firebase firebase = new Firebase();
    ArrayList<ForumTopic> forumTopics;
    FloatingActionButton fab_add_topic;

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

        fab_add_topic = findViewById(R.id.fab_add_topic);
        fab_add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forum_MainActivity.this, Forum_CreateTopic_Activity.class));
            }
        });

        db = FirebaseFirestore.getInstance();
        RVForum = findViewById(R.id.RVForum);
        forumTopics = new ArrayList<>();

        CollectionReference collectionReference = db.collection("FORUM_TOPIC");
        collectionReference.orderBy("datePosted", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<ForumTopic> forumTopicList = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    Log.d("TAG", dc.getData().toString());
                    ForumTopic topic = convertDocumentToForumTopic(dc);
                    forumTopicList.add(topic);
                    Log.d("TAG", String.valueOf(forumTopicList.size()));
                }
                forumAdapter = new Forum_Adapter(Forum_MainActivity.this, forumTopicList);
                prepareRecyclerView(forumTopicList);
            }
        });
    }

    public void prepareRecyclerView(ArrayList<ForumTopic> object){
        Log.d("TAG", "prepareRecyclerView");
        Log.d("TAG", String.valueOf(object.size()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RVForum.setLayoutManager(linearLayoutManager);
        preAdapter(object);
    }

    public void preAdapter(ArrayList<ForumTopic> object){
        forumAdapter = new Forum_Adapter(this, object);
        RVForum.setAdapter(forumAdapter);
    }

    public List<ForumTopic> setForumTopics(String userID){
        List<ForumTopic> filteredForumTopics = new ArrayList<>();
        for(ForumTopic topic : forumTopics){
            if(topic.getUserID().equals(userID))
                filteredForumTopics.add(topic);
        }
        return filteredForumTopics;
    }

    public ForumTopic convertDocumentToForumTopic(QueryDocumentSnapshot dc){
        String topicID = dc.getId();
        String userID = dc.get("userID").toString();
        String dateString = dc.get("datePosted").toString(); // Replace this with your string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime datePosted = LocalDateTime.parse(dateString, formatter);
        String subject = dc.get("subject").toString();
        String description = dc.get("description").toString();

        // cast the returned Object to Long, then convert it to an int
        Long likesLong = (Long) dc.get("likes");
        int likes = likesLong != null ? likesLong.intValue() : 0;

        // Firestore retrieves List objects as List<Object> and not as ArrayList<String>
        List<Object> commentIDObjects = (List<Object>) dc.get("commentID");
        ArrayList<String> commentID = new ArrayList<>();
        if (commentIDObjects != null) {
            for (Object obj : commentIDObjects) {
                if (obj instanceof String) {
                    commentID.add((String) obj);
                }
            }
        }
        return new ForumTopic(topicID, userID, datePosted, subject, description, likes, commentID);
    }

    public void getForumData(){
        CollectionReference collectionReference = db.collection("FORUM_TOPIC");
        collectionReference.orderBy("datePosted", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.d("Firestore error", Objects.requireNonNull(error.getMessage()));
                    return;
                }
                assert value != null;
                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        forumTopics.add(dc.getDocument().toObject(ForumTopic.class));
                    }
                    forumAdapter.notifyDataSetChanged();
                }
            }
        });
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