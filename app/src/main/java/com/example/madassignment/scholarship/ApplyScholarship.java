package com.example.madassignment.scholarship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.madassignment.R;

public class ApplyScholarship extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_scholarship);

        // Header, back to Scholarship Main Page
        ImageView back = findViewById(R.id.imageArrowleft);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event, navigate to another activity
                Intent intent = new Intent(ApplyScholarship.this, FindScholarshipActivity.class);
                startActivity(intent);
            }
        });

        // change info inside when press on either one recyclerview
        // press bookmark to add scholarship to saved
        // press Apply Scholarship, direct to website

    }
}