package com.example.madassignment.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Fragment_Display_Ques extends Fragment {
    FirebaseFirestore db;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressBar progress;
    TextView quesNum;
    TextView quesText;
    Button option1, option2, option3, option4;
    Button prev_retry;
    Button next_submit_backHome;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__display__ques, container, false);


        return view;
    }
}