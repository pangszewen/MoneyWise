package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;

public class Activity_Add_Choose extends AppCompatActivity {
    ImageButton quiz;
    ImageButton course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_choose);

        quiz = findViewById(R.id.quizIcon);
        course = findViewById(R.id.courseIcon);

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Add_Choose.this, Create_Quiz.class);
                startActivity(intent);
            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Add_Choose.this, Create_Course.class);
                startActivity(intent);
            }
        });
    }
}