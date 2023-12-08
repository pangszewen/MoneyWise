package com.example.madassignment.quiz;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.madassignment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CQ_Home_Page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CQ_Home_Page extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__home__page, container, false);
        TextView new_course_text = view.findViewById(R.id.new_courses_text);
        TextView continue_learning_text = view.findViewById(R.id.continue_learning_text);
        TextView quizzes_text = view.findViewById(R.id.quizes_text);
        new_course_text.setPaintFlags(new_course_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        continue_learning_text.setPaintFlags(continue_learning_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        quizzes_text.setPaintFlags(quizzes_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return view;
    }
}