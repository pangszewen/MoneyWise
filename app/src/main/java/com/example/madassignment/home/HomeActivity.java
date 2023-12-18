package com.example.madassignment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.madassignment.MainActivity;
import com.example.madassignment.R;
import com.example.madassignment.forum.Forum_MainActivity;
import com.example.madassignment.quiz.activity_course_display;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    BottomNavigationView bottomNavigationView;
    ImageButton profile;
    TextView welcome;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.DLHome);
        navigationView = findViewById(R.id.nav_overflow);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID==R.id.overflowHome) {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                    return true;
                }else if(itemID==R.id.overflowForum) {
                    startActivity(new Intent(HomeActivity.this, Forum_MainActivity.class));
                    return true;
                }else if(itemID==R.id.overflowExpenses) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    return true;
                }else if(itemID==R.id.overflowCnq){
                    startActivity(new Intent(HomeActivity.this, activity_course_display.class));
                    return true;
                }else
                    return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}