package com.example.madassignment.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_course_display extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CoursesAdapter coursesAdapter;
    FirebaseFirestore db;
    List<Course> courseList;
    FloatingActionButton createCourse;
    SwipeRefreshLayout RVCourseRefresh;
    ImageButton backButton;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_display);
        db = FirebaseFirestore.getInstance();
        RVCourseRefresh = findViewById(R.id.RVCourseRefresh);
        recyclerView = findViewById(R.id.course_recycle_view);
        createCourse = findViewById(R.id.createCourseButton);
        backButton = findViewById(R.id.backButton);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        setUpRVCourse();

        createCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_course_display.this, activity_create_course.class);
                startActivity(intent);
            }
        });

        RVCourseRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUpRVCourse();
                RVCourseRefresh.setRefreshing(false);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                coursesAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public void setUpRVCourse() { // Not in latest sequence
        courseList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("COURSE");

        collectionReference.orderBy("dateCreated", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Course> listOfCourse = new ArrayList<>();
                            for (QueryDocumentSnapshot dc : task.getResult()) {
                                Course topic = convertDocumentToListOfCourse(dc);
                                listOfCourse.add(topic);
                            }
                            coursesAdapter = new CoursesAdapter(activity_course_display.this, listOfCourse);
                            prepareRecyclerView(activity_course_display.this, recyclerView, listOfCourse);
                            coursesAdapter.loadBookmarkedCourses();
                        }
                    }
                });
    }

    public void prepareRecyclerView(Context context, RecyclerView RV, List<Course> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapter(context, RV, object);
    }

    public void preAdapter(Context context, RecyclerView RV, List<Course> object){
        coursesAdapter = new CoursesAdapter(context, object);
        RV.setAdapter(coursesAdapter);
    }

    public Course convertDocumentToListOfCourse(QueryDocumentSnapshot dc){
        Course course = new Course();
        course.setCourseID(dc.getId());
        course.setCourseTitle(dc.get("title").toString());
        course.setAdvisorID(dc.get("advisorID").toString());
        course.setCourseDesc(dc.get("description").toString());
        course.setCourseLanguage(dc.get("language").toString());
        course.setCourseLevel(dc.get("level").toString());
        course.setCourseMode(dc.get("mode").toString());
        return course;
    }
}
