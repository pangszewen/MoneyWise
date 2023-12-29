package com.example.madassignment.quiz;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class activity_individual_course_joined extends AppCompatActivity {
    FirebaseFirestore db;
    String courseID, advisorID;
    TextView title, advisor;
    ImageView courseCoverImage;
    Boolean atDesc = true, saved = false;
    int i = 1;
    Fragment fragmentLessonFull, fragmentDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course_joined);

        courseID = "C0085050"; // Assign your course ID here
        db = FirebaseFirestore.getInstance();
        courseCoverImage = findViewById(R.id.courseImage);
        title = findViewById(R.id.TVCourseTitle);
        advisor = findViewById(R.id.TVAdvisorName);
        Button desc_lessonButton = findViewById(R.id.desc_lessonButton);
        Button continueCourse = findViewById(R.id.continueCourseButton);

        fragmentLessonFull = new fragment_course_lesson_full();
        fragmentDesc = new fragment_course_desc();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.FCVSingleCourse, fragmentDesc, "fragDesc")
                .hide(fragmentDesc)
                .add(R.id.FCVSingleCourse, fragmentLessonFull, "fragLesson")
                .commit();

        desc_lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atDesc) {
                    getSupportFragmentManager().beginTransaction()
                            .show(fragmentLessonFull)
                            .hide(fragmentDesc)
                            .commit();
                    atDesc = false;
                    desc_lessonButton.setText("DESCRIPTION");
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .show(fragmentDesc)
                            .hide(fragmentLessonFull)
                            .commit();
                    atDesc = true;
                    desc_lessonButton.setText("LESSON");
                }
            }
        });

        continueCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle what happens when the "Continue Course" button is clicked
            }
        });

        displayCourse();
    }

    private void saveStatusToDatabase() {
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("courseID", courseID);
        courseData.put("progress", "Lesson "+i);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("USER_DETAILS").document(advisorID);

        userRef.collection("COURSES_JOINED").add(courseData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Course ID added to collection!");
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding course ID to collection", e);
                });
    }

    private void displayCourse() {
        db.collection("COURSE").document(courseID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String titleText = document.getString("title");
                            advisorID = document.getString("advisorID");
                            title.setText(titleText);

                            displayAdvisorName(advisorID);
                            displayCoverImage();
                        }
                    }
                });
    }

    private void displayAdvisorName(String advisorID) {
        db.collection("USER_DETAILS").document(advisorID)
                .get()
                .addOnCompleteListener(advisorTask -> {
                    if (advisorTask.isSuccessful() && advisorTask.getResult() != null) {
                        DocumentSnapshot advisorDocument = advisorTask.getResult();
                        if (advisorDocument.exists()) {
                            String advisorName = advisorDocument.getString("name");
                            advisor.setText(advisorName);
                        }
                    }
                });
    }

    private void displayCoverImage() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference("COURSE_COVER_IMAGE/" + courseID + "/");
        storageReference.child("Cover Image.jpeg").getDownloadUrl().addOnSuccessListener(uri -> { // Need remove jpeg
            String imageUri = uri.toString();
            Picasso.get().load(imageUri).into(courseCoverImage);
            Log.d("Cover Image", "Successful");
        }).addOnFailureListener(exception -> {
        });
    }
}