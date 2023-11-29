package com.example.madassignment.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Fragment_Course_Lessons extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment__course__lessons, container, false);
            LinearLayout lessonContainer = view.findViewById(R.id.lessonContainer);
            FloatingActionButton addLesson = view.findViewById(R.id.add_lesson_button);

            addLesson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View lessonLayout = inflater.inflate(R.layout.layout_course, null);
                    lessonContainer.addView(lessonLayout);
                }
            });

            return view;
        }
}