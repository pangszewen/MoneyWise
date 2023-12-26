package com.example.madassignment.scholarship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.madassignment.R;
import com.example.madassignment.forum.Forum_MainActivity;
import com.example.madassignment.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class ScholarshipMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_main);
        bottomNavigationView = findViewById(R.id.bottomHomeNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setSelectedItemId(R.id.iconSnn);
        MenuItem menuItemDisable = bottomNavigationView.getMenu().findItem(R.id.iconSnn);
        menuItemDisable.setEnabled(false);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID==R.id.iconHome) {
                    startActivity(new Intent(ScholarshipMainActivity.this, HomeActivity.class));
                    return true;
                }else if(itemID==R.id.iconSnn) {
                    startActivity(new Intent(ScholarshipMainActivity.this, ScholarshipMainActivity.class));
                    return true;
                }else if(itemID==R.id.iconForum) {
                    startActivity(new Intent(ScholarshipMainActivity.this, Forum_MainActivity.class));
                    return true;
                }else
                    return false;
            }
        });


        // To FindScholarship
        AppCompatButton btnFindMore = findViewById(R.id.btnFindMore);

        btnFindMore.setOnClickListener(view -> {
            Intent intent = new Intent(ScholarshipMainActivity.this, FindScholarshipActivity.class);
            startActivity(intent);
        });

        // To FindNews
        AppCompatButton btnSeeMore = findViewById(R.id.btnSeeMore);

        btnSeeMore.setOnClickListener(view -> {
            Intent intent = new Intent(ScholarshipMainActivity.this, FindNewsActivity.class);
            startActivity(intent);
        });

        // To Bookmark
        ImageView scholarshipMain = findViewById(R.id.imageSave);
        scholarshipMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScholarshipMainActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });

        AppCompatButton btnBookMark = findViewById(R.id.btnBookMark);

        btnBookMark.setOnClickListener(view -> {
            Intent intent = new Intent(ScholarshipMainActivity.this, BookmarkActivity.class);
            startActivity(intent);
        });





    }
}