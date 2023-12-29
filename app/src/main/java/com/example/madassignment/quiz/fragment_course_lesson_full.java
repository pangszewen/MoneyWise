package com.example.madassignment.quiz;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class fragment_course_lesson_full extends Fragment {
    private RecyclerView recyclerView;
    private LessonsAdapter lessonsAdapter;
    FirebaseFirestore db;
    String courseID;
    List<Lesson> lessonList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_lesson_full, container, false);
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.RVLessons);
        courseID = "C0085050";
        setUpRVLesson();
        return view;
    }

    public void setUpRVLesson() {
        lessonList = new ArrayList<>();
        retrieveVideoUrlsForLessons();
    }

    private void retrieveVideoUrlsForLessons() {
        lessonList = new ArrayList<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("COURSE_LESSONS").child(courseID);
        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    final int lessonNumber = 1;
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            Lesson lesson = new Lesson("Lesson " + lessonNumber, "", uri.toString());
                            lessonList.add(lesson);
                            lessonsAdapter.notifyDataSetChanged();
                        }).addOnFailureListener(exception -> {
                            Log.d("Lesson URL", "Fail to retrieve");
                        });
//                        lessonNumber++;
                    }
                    setAdapter();
                })
                .addOnFailureListener(e -> {
                    Log.d("Lesson URL", "Fail to retrieve");
                });
    }


    private void setAdapter() {
        Context context = getContext();
        if (context != null) {
            lessonsAdapter = new LessonsAdapter(context, lessonList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(lessonsAdapter);
        }
    }
}

