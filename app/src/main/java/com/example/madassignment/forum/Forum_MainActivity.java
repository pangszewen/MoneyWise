package com.example.madassignment.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.madassignment.R;
import com.example.madassignment.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Forum_MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_main);
        bottomNavigationView = findViewById(R.id.bottomForumNavigationView);
        bottomNavigationView.setBackground(null);
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
    }
}