package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Create_Lesson extends AppCompatActivity {
    Boolean save = false;
    String courseID, courseTitle, courseDesc, courseLevel, courseMode, courseLanguage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);
        Button saveButton = findViewById(R.id.saveButton);
        Intent intent = getIntent();
        Log.d("TAG", "uploaded");
        if (intent != null){
            Log.d("TAG", "uploadedssss");
            courseTitle = intent.getStringExtra("title");
            courseDesc = intent.getStringExtra("desc");
            courseLevel = intent.getStringExtra("level");
            courseMode = intent.getStringExtra("mode");
            courseLanguage = intent.getStringExtra("language");
            Log.d("TAG", courseTitle);
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!save)
                    createCourse(courseTitle, courseDesc, courseLevel, courseLanguage, courseMode);
                else
                    Toast.makeText(Create_Lesson.this, "Saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createCourse(String title, String description, String level, String language, String mode) {
        Log.d("TAG", "CreateCourse");
        CollectionReference collectionReference = db.collection("COURSE");
        collectionReference.orderBy("dateCreated", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Course> courseList = new ArrayList<>();
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    Course course = convertDocumentToCourse(dc);
                    courseList.add(course);
                }
                courseID = generateCourseID(courseList);
                String advisorID = "A0000001"; // Need to change
                Course newCourse = new Course(courseID, advisorID, title, description, level, language, mode);
                insertTopicIntoDatabase(newCourse);
            }
        });
    }

    private void insertTopicIntoDatabase(Course course) {
        Map<String, Object> map = new HashMap<>();
        map.put("advisorID", course.getAdvisorID());
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//            String formattedDateTime = course.getDateCreated().format(formatter);
//            map.put("dateCreated", formattedDateTime);
        map.put("title", course.getCourseTitle());
        map.put("description", course.getCourseDesc());
        map.put("level", course.getCourseLevel());
        map.put("language", course.getCourseLanguage());
        map.put("mode", course.getCourseMode());
        db.collection("COURSE").document(course.getCourseID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    save = true;
                    Log.d("TAG", "uploaded");
                    Toast.makeText(Create_Lesson.this, "Course Created!", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("TAG", "Failed");
                    Toast.makeText(Create_Lesson.this, "Failed to Create Course", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Course convertDocumentToCourse(QueryDocumentSnapshot dc){
        Course course = new Course();
        course.setCourseID(dc.getId());
        course.setAdvisorID(dc.get("advisorID").toString());
        course.setDateCreated(dc.get("dateCreated").toString());
        course.setCourseTitle(dc.get("title").toString());
        course.setCourseDesc(dc.get("description").toString());
        course.setCourseLevel(dc.get("level").toString());
        course.setCourseLanguage(dc.get("language").toString());
        course.setCourseMode(dc.get("mode").toString());
        return course;
    }

    private String generateCourseID(ArrayList<Course> courses){
        String newID;
        Random rand = new Random();
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = "C" + String.format("%07d", randomNum); //C0001000
            if(checkDuplicatedTopicID(newID, courses))
                break;
        }
        Log.d("TAG", "This is new courseID " + newID);
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID, ArrayList<Course> courses){
        for(Course topic: courses){
            if(newID.equals(topic.getCourseID()))
                return false;
        }
        Log.d("TAG", "This is checked topic ID " + newID);
        return true;
    }
}