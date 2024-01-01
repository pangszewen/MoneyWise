package com.example.madassignment.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class main_page extends AppCompatActivity {
    private RecyclerView recyclerViewCourse, recyclerViewQuiz;
    private NewCoursesAdapter newCoursesAdapter;
    private QuizzesAdapter quizzesAdapter;
    FirebaseFirestore db;
    Button seeAllContinue, seeAllCourse, seeAllQuiz;
    ImageView courseImage;
    TextView courseTitle, authorName;
    String userID;
    List<Course> courseList;
    List<Quiz> quizList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        userID = "UrymMm91GEbdKUsAIQgj15ZMoOy2"; // Need to change

        seeAllContinue = findViewById(R.id.BtnSeeAllContinue);
        seeAllCourse = findViewById(R.id.BtnSeeAllCourse);
        seeAllQuiz = findViewById(R.id.BtnSeeAllQuiz);
        courseImage = findViewById(R.id.IVCourseImage);
        courseTitle = findViewById(R.id.TVCourseTitle);
        authorName = findViewById(R.id.TVAuthorName);
        recyclerViewCourse = findViewById(R.id.RVCourse);
        recyclerViewQuiz = findViewById(R.id.RVQuiz);

        seeAllContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_page.this, activity_complete_continue_course.class);
                startActivity(intent);
            }
        });

        seeAllCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_page.this, activity_course_display.class);
                startActivity(intent);
            }
        });

        seeAllQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (main_page.this, activity_quiz_display.class);
                startActivity(intent);
            }
        });
        displayCompleteCourse();
        setUpRVCourse();
        setUpRVQuiz();
    }

    private void setUpRVQuiz() {
        quizList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("QUIZ");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Quiz> listOfQuiz = new ArrayList<>();
                int count = 0;
                for(QueryDocumentSnapshot dc : task.getResult()){
                    if (count < 3) {
                        Quiz topic = convertDocumentToListOfQuiz(dc);
                        listOfQuiz.add(topic);
                        count++;
                    } else {
                        break;
                    }
                }
                quizzesAdapter = new QuizzesAdapter(main_page.this, listOfQuiz);
                prepareRecyclerViewQuiz(main_page.this, recyclerViewQuiz, listOfQuiz);
            }
        });
    }


    public void prepareRecyclerViewQuiz(Context context, RecyclerView RV, List<Quiz> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapterQuiz(context, RV, object);
    }

    public void preAdapterQuiz(Context context, RecyclerView RV, List<Quiz> object){
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

    private void setUpRVCourse() {
        db = FirebaseFirestore.getInstance();
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
                newCoursesAdapter = new NewCoursesAdapter(main_page.this, listOfCourse);
                prepareRecyclerView(main_page.this, recyclerViewCourse, listOfCourse);
            }
        });
    }

    public void prepareRecyclerView(Context context, RecyclerView RV, List<Course> object){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        RV.setLayoutManager(linearLayoutManager);
        preAdapter(context, RV, object);
    }

    public void preAdapter(Context context, RecyclerView RV, List<Course> object){
        newCoursesAdapter = new NewCoursesAdapter(context, object);
        RV.setAdapter(newCoursesAdapter);
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

    private void displayCompleteCourse() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("USER_DETAILS")
                .document(userID)
                .collection("COURSES_JOINED")
                .orderBy("dateJoined", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        String title = documentSnapshot.getString("title");
                        String courseID = documentSnapshot.getId();
                        String advisorID = documentSnapshot.getString("advisorID");
                        displayAdvisorName(advisorID);
                        displayCoverImage(courseID);
                        courseTitle.setText(title);
                    }
                });
    }

    private void displayCoverImage(String courseID) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("COURSE_COVER_IMAGE").child(courseID).child("Cover Image"); // Replace with your image file extension
        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get()
                    .load(uri)
                    .placeholder(R.drawable.course_rectangle_outline_grey) // Replace with a placeholder image while loading
                    .error(R.drawable.course_rectangle_outline_grey) // Replace with an error image if download fails
                    .into(courseImage);
        });
    }
    private void displayAdvisorName(String advisorID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userDocRef = db.collection("USER_DETAILS").document(advisorID);
        userDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("name");
                authorName.setText(name);
            }
        });
    }
}