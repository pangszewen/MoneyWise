package com.example.madassignment.quiz;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
   String correctAns, option1_text, option2_text, option3_text, option4_text;
   Integer ques = 0;
   LinearLayout A, B, C, D;
   ArrayList<String> questionIds = new ArrayList<>();
   int currentQuestionIndex = -1;
   ArrayList<String> options;
   Integer score = 0;
   Double totalScore;

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
        String quizID = "Q0836187"; //Need change
        fetchQuestionIds(quizID); //
        title.setText("Finance Quiz");
        quesNum.setText("Question "+ques);

        // When first option is chosen
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (options.get(0).equals(correctAns)) { // Correct choice
                    A.setBackgroundResource(R.drawable.quiz_button_outline_green);
                    score++;
                }
                else { // Wrong choice
                    A.setBackgroundResource(R.drawable.quiz_button_outline_red);
                    showAns();
                }
                if (ques == questionIds.size()){ // End of quiz
                    calculateScore();
                }
                else showNextQues(quizID);
            }
        });
        // When second option is chosen
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (options.get(1).equals(correctAns)) { // Correct choice
                    B.setBackgroundResource(R.drawable.quiz_button_outline_green);
                    score++;
                }
                else { // Wrong choice
                    B.setBackgroundResource(R.drawable.quiz_button_outline_red);
                    showAns();
                }
                if (ques == questionIds.size()){ // End of quiz
                    calculateScore();
                }
                else showNextQues(quizID);
            }
        });
        // When third option is chosen
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (options.get(2).equals(correctAns)) { // Correct choice
                    C.setBackgroundResource(R.drawable.quiz_button_outline_green);
                    score++;
                }
                else { // Wrong choice
                    C.setBackgroundResource(R.drawable.quiz_button_outline_red);
                    showAns();
                }
                if (ques == questionIds.size()){ // End of quiz
                    calculateScore();
                }
                else showNextQues(quizID);
            }
        });
        // When forth option is chosen
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (options.get(3).equals(correctAns)) { // Correct choice
                    D.setBackgroundResource(R.drawable.quiz_button_outline_green);
                    score++;
                }
                else { // Wrong choice
                    D.setBackgroundResource(R.drawable.quiz_button_outline_red);
                    showAns();
                }
                if (ques == questionIds.size()){ // End of quiz
                    calculateScore();
                }
                else showNextQues(quizID);
            }
        });
    }
    // Calculate the total score of quiz
    private void calculateScore() {
        totalScore = ((double) score / ques) * 100;
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
            Collections.shuffle(questionIds); // Shuffle questions
            showNextQues(quizID);
        });
    }

    private void showNextQues(String quizID) {
        if (currentQuestionIndex < questionIds.size() - 1) {
            ++currentQuestionIndex;
            String questionId = questionIds.get(currentQuestionIndex);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() { // Delay for 1s to show colour
                @Override
                public void run() {
                    fetchQuestionDetails(quizID, questionId);
                }
            }, 1000);
        } else {
        }
    }

    private void fetchQuestionDetails(String quizID, String questionId) {
        A.setBackgroundResource(R.drawable.quiz_button_outline); // Reset colour of option box
        B.setBackgroundResource(R.drawable.quiz_button_outline);
        C.setBackgroundResource(R.drawable.quiz_button_outline);
        D.setBackgroundResource(R.drawable.quiz_button_outline);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference quizDocRef = db.collection("QUIZ").document(quizID);
        DocumentReference questionDocRef = quizDocRef.collection("QUESTION").document(questionId);
        questionDocRef.get().addOnSuccessListener(documentSnapshot -> {
            String questionText = documentSnapshot.getString("quesText");
            option1_text = documentSnapshot.getString("option1");
            option2_text = documentSnapshot.getString("option2");
            option3_text = documentSnapshot.getString("option3");
            option4_text = documentSnapshot.getString("correctAns");
            correctAns = documentSnapshot.getString("correctAns");

            options = new ArrayList<>();
            options.add(option1_text);
            options.add(option2_text);
            options.add(option3_text);
            options.add(option4_text);
            Collections.shuffle(options); // Shuffle the options

            quesText.setText(questionText);
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            Log.d("FirebaseData", "Question: " + questionText);
            Log.d("FirebaseData", "Shuffled Options: " + options);
            Log.d("FirebaseData", "Correct Answer: " + correctAns);

            quesNum.setText("Question " + (++ques));
        });
    }
    // Show the correct ans
    private void showAns(){
        if (options.get(0).equals(correctAns)) A.setBackgroundResource(R.drawable.quiz_button_outline_green);
        else if (options.get(1).equals(correctAns)) B.setBackgroundResource(R.drawable.quiz_button_outline_green);
        else if (options.get(2).equals(correctAns)) C.setBackgroundResource(R.drawable.quiz_button_outline_green);
        else if (options.get(3).equals(correctAns)) D.setBackgroundResource(R.drawable.quiz_button_outline_green);
    }
}