package com.example.madassignment.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.madassignment.R;
import com.example.madassignment.forum.Forum_MainActivity;
import com.example.madassignment.login_register.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ImageButton profile;
    TextView welcome;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottomHomeNavigationView);
        bottomNavigationView.setBackground(null);
        MenuItem menuItemDisable = bottomNavigationView.getMenu().findItem(R.id.iconHome);
        menuItemDisable.setEnabled(false);
        welcome=findViewById(R.id.TVWelcome);

        //need set welcome text

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID==R.id.iconHome) {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                    return true;
                }else if(itemID==R.id.iconForum) {
                    startActivity(new Intent(HomeActivity.this, Forum_MainActivity.class));
                    return true;
                }else
                    return false;
            }
        });

        profile=findViewById(R.id.IBProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }

}