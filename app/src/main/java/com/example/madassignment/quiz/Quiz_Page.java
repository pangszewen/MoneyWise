package com.example.madassignment.quiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class Quiz_Page extends AppCompatActivity {
   ImageButton cancel;
   TextView title;
   TextView quesNum, quesText, option1, option2, option3, option4;
   String correctAns;
   Integer ques = 0;
   LinearLayout A, B, C, D;
   ArrayList<String> questionIds = new ArrayList<>();
   int currentQuestionIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);


        cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.quizTitle);
        quesNum = findViewById(R.id.quesNum);
        quesText = findViewById(R.id.quesText);
        option1 = findViewById(R.id.option1_text);
        option2 = findViewById(R.id.option2_text);
        option3 = findViewById(R.id.option3_text);
        option4 = findViewById(R.id.option4_text);
        A = findViewById(R.id.option1);
        B = findViewById(R.id.option2);
        C = findViewById(R.id.option3);
        D = findViewById(R.id.option4);

//        title.setText(getIntent().getStringExtra("title"));
//        String quizID = getIntent().getStringExtra("quizID");
        String quizID = "Q0836187";
        fetchQuestionIds(quizID);
//        showNextQues(quizID);
        title.setText("Finance Quiz");
        quesNum.setText("Question "+ques);

        A.setOnClickListener(view -> showNextQues(quizID));
        B.setOnClickListener(view -> showNextQues(quizID));
        C.setOnClickListener(view -> showNextQues(quizID));
        D.setOnClickListener(view -> showNextQues(quizID));
    }

    private void fetchQuestionIds(String quizID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference quizDocRef = db.collection("QUIZ").document(quizID);
        CollectionReference questionsRef = quizDocRef.collection("QUESTION");
        questionsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String questionId = documentSnapshot.getId();
                questionIds.add(questionId);
            }
            Collections.shuffle(questionIds);
            Log.d("Question List",questionIds.toString());
            showNextQues(quizID);
        });
    }

    private void showNextQues(String quizID) {
        if (currentQuestionIndex < questionIds.size() - 1) {
            ++currentQuestionIndex;
            String questionId = questionIds.get(currentQuestionIndex);
            fetchQuestionDetails(quizID, questionId);
        } else {
        }
    }

    private void fetchQuestionDetails(String quizID, String questionId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference quizDocRef = db.collection("QUIZ").document(quizID);
        DocumentReference questionDocRef = quizDocRef.collection("QUESTION").document(questionId);
        questionDocRef.get().addOnSuccessListener(documentSnapshot -> {
            String questionText = documentSnapshot.getString("quesText");
            String option1_text = documentSnapshot.getString("option1");
            String option2_text = documentSnapshot.getString("option2");
            String option3_text = documentSnapshot.getString("option3");
            correctAns = documentSnapshot.getString("correctAns");

            ArrayList<String> options = new ArrayList<>();
            options.add(option1_text);
            options.add(option2_text);
            options.add(option3_text);
            Collections.shuffle(options);

            quesText.setText(questionText);
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(correctAns);

            Log.d("FirebaseData", "Question: " + questionText);
            Log.d("FirebaseData", "Shuffled Options: " + options);
            Log.d("FirebaseData", "Correct Answer: " + correctAns);

            quesNum.setText("Question " + (++ques));
        });
    }
}