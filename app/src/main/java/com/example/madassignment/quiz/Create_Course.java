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
    public String courseID;
    CollectionReference collectionReference;
    Random rand = new Random();
    Boolean saveState = false;
    String savedTitleText;
    EditText titleInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        db = FirebaseFirestore.getInstance();

        titleInput = findViewById(R.id.course_title_input);
        Button descButton=findViewById(R.id.descButton);
        Button lessonButton=findViewById(R.id.lessonButton);

        String fragmentTag = getIntent().getStringExtra("DescFrag");

        if (fragmentTag != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Fragment fragmentToShow;

            if (fragmentTag.equals("DescriptionFragment")) {
                fragmentToShow = new Fragment_Course_Description();
                fragmentTransaction.replace(R.id.frameLayout, fragmentToShow);
                fragmentTransaction.commit();
            }
        }
//
//        if (savedInstanceState != null) {
//            savedTitleText = savedInstanceState.getString("savedTitleText", "");
//            titleInput.setText(savedTitleText);
//        }

        descButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = titleInput.getText().toString();
                if (!courseTitle.isEmpty() && !saveState)
                    createCourse(courseTitle);
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
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        savedTitleText = titleInput.getText().toString();
//        outState.putString("savedTitleText", savedTitleText);
//    }

    private void createCourse(String courseTitle) {
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
                courseID = generateCourseID(courseList);
                String advisorID = "A0000001"; // Need to change
                Course newCourse = new Course(courseID, advisorID, courseTitle, 0);
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
        course.setCourseLevel(dc.get("level").toString());
        course.setCourseLanguage(dc.get("language").toString());
        course.setCourseMode(dc.get("mode").toString());
        course.setCourseNumOfStudents(Integer.parseInt(dc.get("num of students").toString()));
        return course;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, "DescriptionFragment");
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
        map.put("level", course.getCourseLevel());
        map.put("language", course.getCourseLanguage());
        map.put("mode", course.getCourseMode());
        map.put("num of students", course.getCourseNumOfStudents());
        db.collection("COURSE").document(course.getCourseID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    saveState = true;
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
