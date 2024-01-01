package com.example.madassignment.quiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.madassignment.R;
import com.google.android.material.tabs.TabLayout;

public class activity_complete_continue_course extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_continue_course);

        tabLayout = findViewById(R.id.TLCourse);
        viewPager = findViewById(R.id.VPCourse);

        CourseViewpagerAdapter courseViewpagerAdapter = new CourseViewpagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragment_course_continue fragCon = new fragment_course_continue();
        fragment_course_completed fragComp = new fragment_course_completed();
        courseViewpagerAdapter.addFragment(fragCon, "Continue");
        courseViewpagerAdapter.addFragment(fragComp, "Completed");
        viewPager.setAdapter(courseViewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}