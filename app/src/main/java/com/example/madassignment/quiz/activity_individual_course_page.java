package com.example.madassignment.quiz;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class activity_individual_course_page extends AppCompatActivity {
    FirebaseFirestore db;
    String courseID, advisorID;
    TextView title, advisor;
    ImageView courseCoverImage;
    Boolean atDesc = true, saved = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course_page);
        Bundle bundle = new Bundle();
        fragment_course_lesson_single fragLesson = new fragment_course_lesson_single();
        fragment_course_desc fragDesc = new fragment_course_desc();
//        fragment_course_lesson_full fragLessonFull = new fragment_course_lesson_full();

        fragLesson.setArguments(bundle);

//        courseID = getIntent().getStringExtra("courseID");
        courseID = "C0085050";
        db = FirebaseFirestore.getInstance();
        courseCoverImage = findViewById(R.id.courseImage);
        title = findViewById(R.id.TVCourseTitle);
        advisor = findViewById(R.id.TVAdvisorName);
        Button desc_lessonButton = findViewById(R.id.desc_lessonButton);
        Button joinCourse = findViewById(R.id.joinCourseButton);

        displayCourse();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.FCVSingleCourse, fragLesson, "fragLesson")
                .hide(fragLesson)
                .add(R.id.FCVSingleCourse, fragDesc, "fragDesc")
                .commit();
        desc_lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("courseID", courseID);
                if (atDesc) {
                    getSupportFragmentManager().beginTransaction()
                            .show(fragLesson)
                            .hide(fragDesc)
                            .commit();
                    atDesc = false;
                    desc_lessonButton.setText("DESCRIPTION");
                } else if (!atDesc){
                    getSupportFragmentManager().beginTransaction()
                            .show(fragDesc)
                            .hide(fragLesson)
                            .commit();
                    atDesc = true;
                    desc_lessonButton.setText("LESSON");
                }
//                else if (atDesc && saved){
//                    getSupportFragmentManager().beginTransaction()
//                            .show(fragLessonFull)
//                            .hide(fragDesc)
//                            .commit();
//                    atDesc = false;
//                    desc_lessonButton.setText("DESCRIPTION");
//                } else {
//                    getSupportFragmentManager().beginTransaction()
//                            .show(fragDesc)
//                            .hide(fragLessonFull)
//                            .commit();
//                    atDesc = true;
//                    desc_lessonButton.setText("LESSON");
//                }
            }
        });

        joinCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!saved)
                    showDialog();
                else
                    Toast.makeText(activity_individual_course_page.this, "Already Enroll in Course!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity_individual_course_page.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to join this course?");
        builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveStatusToDatabase();
                fragment_course_lesson_full fragFullLesson = new fragment_course_lesson_full();
                Bundle bundle = new Bundle();
                bundle.putString("courseID", courseID);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveStatusToDatabase() {
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("courseID", courseID);
        courseData.put("progress", 0);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("USER_DETAILS").document(advisorID);

        userRef.collection("COURSES_JOINED").add(courseData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Course ID added to collection!");
                    saved = true;
                    Toast.makeText(activity_individual_course_page.this, "Successfully enrolled in course!", Toast.LENGTH_SHORT).show();
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