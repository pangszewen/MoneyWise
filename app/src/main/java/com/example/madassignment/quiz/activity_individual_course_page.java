package com.example.madassignment.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class activity_individual_course_page extends AppCompatActivity {
    FirebaseFirestore db;
    String courseID;
    TextView title, advisor;
    ImageView courseCoverImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course_page);

//        courseID = getIntent().getStringExtra("courseID");
        courseID = "C0085050";
        db = FirebaseFirestore.getInstance();
        courseCoverImage = findViewById(R.id.courseImage);
        title = findViewById(R.id.TVCourseTitle);
        advisor = findViewById(R.id.TVAdvisorName);
        Button desc_lessonButton = findViewById(R.id.desc_lessonButton);
        Button joinCourse = findViewById(R.id.joinCourseButton);

        displayCourse();

        desc_lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pass courseID
            }
        });
    }

    private void displayCourse() {
        db.collection("COURSE").document(courseID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String titleText = document.getString("title"); // Replace "title" with your field name
                            String advisorID = document.getString("advisorID"); // Replace "advisor" with your field name
                            title.setText(titleText);

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
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageReference = storage.getReference("COURSE_COVER_IMAGE/" + courseID + "/");
                            storageReference.child("Cover Image.jpeg").getDownloadUrl().addOnSuccessListener(uri -> { // Need remove jpeg
                                String imageUri = uri.toString();
                                Picasso.get().load(imageUri).into(courseCoverImage);
                            }).addOnFailureListener(exception -> {
                            });

                        }
                    }
                });
    }
}