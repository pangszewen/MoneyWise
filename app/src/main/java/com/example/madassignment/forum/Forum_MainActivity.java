package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

public class Forum_MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    FloatingActionButton fab_add_topic;

    public Forum_MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);
        db = FirebaseFirestore.getInstance();
        fab_add_topic = findViewById(R.id.fab_add_topic);

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

        fab_add_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forum_MainActivity.this, Forum_CreateTopic_Activity.class));
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