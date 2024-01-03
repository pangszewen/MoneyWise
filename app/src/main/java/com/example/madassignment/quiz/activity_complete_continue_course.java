package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.madassignment.R;
import com.google.android.material.tabs.TabLayout;

public class activity_complete_continue_course extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    SearchView searchView;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_continue_course);

        tabLayout = findViewById(R.id.TLCourse);
        viewPager = findViewById(R.id.VPCourse);
        searchView = findViewById(R.id.search);
        back = findViewById(R.id.backButton);

        CourseViewpagerAdapter courseViewpagerAdapter = new CourseViewpagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragment_course_continue fragCon = new fragment_course_continue();
        fragment_course_completed fragComp = new fragment_course_completed();
        courseViewpagerAdapter.addFragment(fragCon, "Continue");
        courseViewpagerAdapter.addFragment(fragComp, "Completed");
        viewPager.setAdapter(courseViewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fragCon.onSearch(s);
                fragComp.onSearch(s);
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_complete_continue_course.this, main_page.class);
                startActivity(intent);
            }
        });

    }
}