package com.example.madassignment.quiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
    Boolean atDesc = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_course_page);
        Bundle bundle = new Bundle();
        fragment_course_lesson_single fragLesson = new fragment_course_lesson_single();
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

        desc_lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("courseID", courseID);


                if (atDesc){
                    fragment_course_lesson_single fragLesson = new fragment_course_lesson_single();
                    fragLesson.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.FCVSingleCourse, fragment_course_lesson_single.class, null)
                            .commit();
                    atDesc = false;
                    desc_lessonButton.setText("DESCRIPTION");
                }
                else{
                    fragment_course_desc fragDesc = new fragment_course_desc();
                    fragDesc.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.FCVSingleCourse, fragment_course_desc.class, null)
                            .commit();
                    atDesc = true;
                    desc_lessonButton.setText("LESSON");
                }
            }
        });

        joinCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity_individual_course_page.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to join this course?");
                    
                    builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            joinCourse();
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
        });
    }

    private void joinCourse() {
        saveStatusToDatabase();
//        Intent intent = new Intent (activity_individual_course_page.this, )
    }

    private void saveStatusToDatabase() {
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
        }).addOnFailureListener(exception -> {
        });
    }


}