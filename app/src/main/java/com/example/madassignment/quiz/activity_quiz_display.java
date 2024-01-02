package com.example.madassignment.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class activity_quiz_display extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizzesAdapter quizzesAdapter;
    FirebaseFirestore db;
    List<Quiz> quizList;
    FloatingActionButton createQuiz;
    SwipeRefreshLayout RVQuizRefresh;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_display);

        db = FirebaseFirestore.getInstance();
        RVQuizRefresh = findViewById(R.id.RVQuizRefresh);
        recyclerView = findViewById(R.id.quiz_recycle_view);
        createQuiz = findViewById(R.id.createQuizButton);
        searchView = findViewById(R.id.search);
        setUpRVQuiz();

        createQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_quiz_display.this, activity_create_quiz.class);
                startActivity(intent);
            }
        });

        RVQuizRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUpRVQuiz();
                RVQuizRefresh.setRefreshing(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                quizzesAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void setUpRVQuiz() {
        quizList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("QUIZ");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Quiz> listOfQuiz = new ArrayList<>();
                for(QueryDocumentSnapshot dc : task.getResult()){
                    Quiz topic = convertDocumentToListOfQuiz(dc);
                    listOfQuiz.add(topic);
                }
                quizzesAdapter = new QuizzesAdapter(activity_quiz_display.this, listOfQuiz);
                prepareRecyclerView(activity_quiz_display.this, recyclerView, listOfQuiz);
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