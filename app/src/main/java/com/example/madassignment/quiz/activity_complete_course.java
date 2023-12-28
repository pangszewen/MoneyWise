package com.example.madassignment.quiz;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_complete_course extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CoursesCompletedAdapter coursesCompletedAdapter;
    FirebaseFirestore db;
    List<Course> courseList;
//    SwipeRefreshLayout RVForumRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_course);
        db = FirebaseFirestore.getInstance();
//        RVForumRefresh = findViewById(R.id.RVForumRefresh);
        recyclerView = findViewById(R.id.course_recycle_view);
        setUpRVCourse();
    }

    public void setUpRVCourse(){
        courseList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("COURSE");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Course> listOfCourse = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    Course topic = convertDocumentToListOfCourse(dc);
                    listOfCourse.add(topic);
                }
                coursesCompletedAdapter = new CoursesCompletedAdapter(activity_complete_course.this, listOfCourse);
                prepareRecyclerView(activity_complete_course.this, recyclerView, listOfCourse);
            }
        });
    }

    public void prepareRecyclerView(Context context, RecyclerView RV, List<Course> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapter(context, RV, object);
    }

    public void preAdapter(Context context, RecyclerView RV, List<Course> object){
        coursesCompletedAdapter = new CoursesCompletedAdapter(context, object);
        RV.setAdapter(coursesCompletedAdapter);
    }

    public Course convertDocumentToListOfCourse(QueryDocumentSnapshot dc){
        Course course = new Course();
        course.setCourseID(dc.getId());
        course.setCourseTitle(dc.get("title").toString());
        course.setAdvisorID(dc.get("advisorID").toString());
        course.setLessonNum(Integer.parseInt(dc.get("lessonNum").toString()));
        return course;
    }
}