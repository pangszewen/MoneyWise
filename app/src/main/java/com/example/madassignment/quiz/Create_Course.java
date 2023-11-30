package com.example.madassignment.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Create_Course extends AppCompatActivity {
    FirebaseFirestore db;
    CollectionReference collectionReference;
    Random rand = new Random();
    EditText descInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        db = FirebaseFirestore.getInstance();

        EditText titleInput = findViewById(R.id.course_title_input);
        Button saveButton = findViewById(R.id.save_course_button);

        Button descButton=findViewById(R.id.descButton);
        Button lessonButton=findViewById(R.id.lessonButton);

        descButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descButton.setTextColor(getResources().getColor(R.color.dark_blue));
                lessonButton.setTextColor(getResources().getColor(R.color.blue_grey));
                replaceFragment(new Fragment_Course_Description());
            }
        });

        lessonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descButton.setTextColor(getResources().getColor(R.color.blue_grey));
                lessonButton.setTextColor(getResources().getColor(R.color.dark_blue));
                replaceFragment(new Fragment_Course_Lessons());
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = titleInput.getText().toString();
                String courseDesc = descInput.getText().toString();
                Fragment_Course_Description fragment = (Fragment_Course_Description) getSupportFragmentManager().findFragmentById(R.id.fragment_course);
                System.out.println("Fragment "+fragment);
                view = fragment.getView();
                createCourse(courseTitle, courseDesc);
                descInput = view.findViewById(R.id.description_input);

            }
        });
    }

    private void createCourse(String courseTitle, String courseDesc) {
        Log.d("TAG", "createCourse");
        CollectionReference collectionReference = db.collection("COURSE");
        collectionReference.orderBy("dateCreated", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Course> courseList = new ArrayList<>();
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    Course course = convertDocumentToCourse(dc);
                    courseList.add(course);
                }
                String courseID = generateCourseID(courseList);
                String advisorID = "A0000001";
                Course newCourse = new Course(courseID, advisorID, courseTitle, courseDesc);
                insertTopicIntoDatabase(newCourse);
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
        return course;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void insertTopicIntoDatabase(Course course) {
        Map<String, Object> map = new HashMap<>();
        map.put("advisorID", course.getAdvisorID());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDateTime = course.getDateCreated().format(formatter);
//        map.put("dateCreated", formattedDateTime);
        map.put("title", course.getCourseTitle());
        map.put("description", course.getCourseDesc());
        db.collection("COURSE").document(course.getCourseID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(Create_Course.this, "Successfully Saved Course", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "uploaded");
                }else {
                    Toast.makeText(Create_Course.this, "Fail to Create Course", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "Failed");
                }
            }
        });
    }

    private String generateCourseID(List<Course> courses){
        String newID = null;
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = "C" + String.format("%07d", randomNum); //C0001000
            if(checkDuplicatedTopicID(newID, courses))
                break;
        }
        Log.d("TAG", "This is new courseID " + newID);
        return newID;
    }

    private boolean checkDuplicatedTopicID(String newID, List<Course> courses){
        for(Course topic: courses){
            if(newID.equals(topic.getCourseID()))
                return false;
        }
        Log.d("TAG", "This is checked topic ID " + newID);
        return true;
    }
}
