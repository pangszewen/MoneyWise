package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;

public class main_page extends AppCompatActivity {
    Button seeAllContinue, seeAllCourse, seeAllQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        seeAllContinue = findViewById(R.id.BtnSeeAllContinue);
        seeAllCourse = findViewById(R.id.BtnSeeAllCourse);
        seeAllQuiz = findViewById(R.id.BtnSeeAllQuiz);

        seeAllContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_page.this, activity_complete_continue_course.class);
                startActivity(intent);
            }
        });

        seeAllCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_page.this, activity_course_display.class);
                startActivity(intent);
            }
        });

        seeAllQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_page.this, activity_quiz_display.class);
                startActivity(intent);
            }
        });
    }
}