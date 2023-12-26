package com.example.madassignment.quiz;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class fragment_course_lesson_single extends Fragment {
    VideoView lessonVid;
    TextView lessontext, lessonTime;
    String courseID;
    boolean isPlaying = false;
    int totalDuration = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_lesson_single, container, false);

        lessonVid = view.findViewById(R.id.VVLesson);
        lessontext = view.findViewById(R.id.TVLesson);
        lessonTime = view.findViewById(R.id.TVCourseTime);

//        Bundle bundle = getArguments();
//        if (bundle != null)
//            courseID = bundle.getString("courseID");

        courseID = "C0085050";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("COURSE_LESSONS/" + courseID + "/lesson 1.mp4");

        try {
            final File localFile = File.createTempFile("lesson 1", "mp4");
            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        lessonVid.setVideoURI(Uri.fromFile(localFile));
                        lessonVid.setOnPreparedListener(mp -> {
                            totalDuration = lessonVid.getDuration();
                            int durationSeconds = totalDuration / 1000;
                            int minutes = durationSeconds / 60;
                            int seconds = durationSeconds % 60;
                            lessonTime.setText(String.format("%02d:%02d", minutes, seconds));
                        });
                        lessonVid.requestFocus();
                        lessonVid.setOnClickListener(v -> {
                            if (isPlaying) {
                                lessonVid.pause();
                            } else {
                                lessonVid.start();
                                updateCountdown();
                            }
                            isPlaying = !isPlaying;
                        });
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void updateCountdown() {
        new Thread(() -> {
            while (lessonVid.isPlaying()) {
                getActivity().runOnUiThread(() -> {
                    int currentTime = lessonVid.getCurrentPosition();
                    int remainingTime = totalDuration - currentTime;
                    int seconds = remainingTime / 1000;
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    lessonTime.setText(String.format("%02d:%02d", minutes, seconds));
                });
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
