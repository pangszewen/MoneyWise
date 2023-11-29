package com.example.madassignment.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.madassignment.R;

public class Create_Course extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        Button descButton=findViewById(R.id.descButton);
        Button lessonButton=findViewById(R.id.lessonButton);

        descButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descButton.setTextColor(getResources().getColorStateList(R.color.button_colour));
                lessonButton.setTextColor(getResources().getColorStateList(R.color.button_colour));
                replaceFragment(new Fragment_Course_Description());
            }
        });

        lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descButton.setTextColor(getResources().getColorStateList(R.color.button_colour));
                lessonButton.setTextColor(getResources().getColorStateList(R.color.button_colour));
                replaceFragment(new Fragment_Course_Lessons());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame)
    }
}