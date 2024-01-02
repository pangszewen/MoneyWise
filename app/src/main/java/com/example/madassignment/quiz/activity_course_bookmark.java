package com.example.madassignment.quiz;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_course_bookmark extends AppCompatActivity {
    ImageButton back;
    ArrayList<Course> courseList;
    ArrayList<Quiz> quizList;
    FirebaseFirestore db;
    String userID;
    RecyclerView recyclerViewCourse;
    RecyclerView recyclerViewQuiz;
    private CoursesAdapter coursesAdapter;
    private QuizzesAdapter quizzesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_bookmark);
        db = FirebaseFirestore.getInstance();

        back = findViewById(R.id.backButton);
        recyclerViewCourse = findViewById(R.id.RVCourse);
        recyclerViewQuiz = findViewById(R.id.RVQuiz);

        userID = "UrymMm91GEbdKUsAIQgj15ZMoOy2"; // Need to change

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        setUpRVCourse();
        setUpRVQuiz();
    }

    public void setUpRVCourse() {
        courseList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("USER_DETAILS").document(userID).collection("COURSE_BOOKMARK");

        collectionReference.orderBy("dateBookmarked", Query.Direction.DESCENDING)
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
                            coursesAdapter = new CoursesAdapter(activity_course_bookmark.this, listOfCourse);
                            prepareRecyclerViewCourse(activity_course_bookmark.this, recyclerViewCourse, listOfCourse);
                        }
                    }
                });
    }

    public void prepareRecyclerViewCourse(Context context, RecyclerView RV, List<Course> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapterCourse(context, RV, object);
    }

    public void preAdapterCourse(Context context, RecyclerView RV, List<Course> object){
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

    private void setUpRVQuiz() {
        quizList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("USER_DETAILS").document(userID).collection("QUIZ_BOOKMARK");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Quiz> listOfQuiz = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    Quiz topic = convertDocumentToListOfQuiz(dc);
                    listOfQuiz.add(topic);
                }
                quizzesAdapter = new QuizzesAdapter(activity_course_bookmark.this, listOfQuiz);
                prepareRecyclerView(activity_course_bookmark.this, recyclerViewCourse, listOfQuiz);
            }
        });
    }

    public void prepareRecyclerView(Context context, RecyclerView RV, List<Quiz> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapter(context, RV, object);
    }

    public void preAdapter(Context context, RecyclerView RV, List<Quiz> object){
        quizzesAdapter = new QuizzesAdapter(context, object);
        RV.setAdapter(quizzesAdapter);
    }

    public Quiz convertDocumentToListOfQuiz(QueryDocumentSnapshot dc){
        Quiz quiz = new Quiz();
        quiz.setQuizID(dc.getId());
        quiz.setQuizTitle(dc.get("title").toString());
        quiz.setAdvisorID(dc.get("advisorID").toString());
        quiz.setNumOfQues(dc.get("numOfQues").toString());
        return quiz;
    }
}