package com.example.madassignment.quiz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.madassignment.expenses.MainActivity;
import com.example.madassignment.R;
import com.example.madassignment.forum.Forum_MainActivity;
import com.example.madassignment.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class activity_course_display extends AppCompatActivity {
//    activity_course_display binding;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_display);

        bottomNavigationView = findViewById(R.id.bottomCourseNavigationView);
        bottomNavigationView.setBackground(null);
        MenuItem menuItemDisable = bottomNavigationView.getMenu().findItem(R.id.iconExpenses);
        menuItemDisable.setEnabled(false);
        Drawable icon = VectorDrawableCompat.create(getResources(), R.drawable.outline_expenses_white, getTheme());
        icon.setTintList(AppCompatResources.getColorStateList(this, R.color.grey));
        menuItemDisable.setIcon(icon);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemID = item.getItemId();
                if(itemID==R.id.iconHome) {
                    startActivity(new Intent(activity_course_display.this, HomeActivity.class));
                    return true;
                }else if(itemID==R.id.iconForum) {
                    startActivity(new Intent(activity_course_display.this, Forum_MainActivity.class));
                    return true;
                }else if (itemID==R.id.iconCnq) {
                    startActivity(new Intent(activity_course_display.this, activity_course_display.class));
                    return true;
                }else if(itemID==R.id.iconExpenses){
                    startActivity(new Intent(activity_course_display.this, MainActivity.class));
                    return true;
                }
                else
                    return false;
            }
        });

        FloatingActionButton add = findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_course_display.this, Activity_Add_Choose.class);
                startActivity(intent);            }
        });

    }
}