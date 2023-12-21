package com.example.madassignment.quiz;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Create_Quiz extends AppCompatActivity {
    String quizID, quesID;
    String quizTitle, advisorID;
    
    String quesText, quesCorrectAns, quesOption1, quesOption2, quesOption3;
    Boolean save = false;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseStorage storage;;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        advisorID = "UrymMm91GEbdKUsAIQgj15ZMoOy2"; // Need to change

        TextInputEditText title = findViewById(R.id.titleInput);
        TextInputEditText QuesquesText = findViewById(R.id.questionTextInput);
        TextInputEditText correctAns = findViewById(R.id.correctAnsInput);
        TextInputEditText option1 = findViewById(R.id.option1Input);
        TextInputEditText option2 = findViewById(R.id.option2Input);
        TextInputEditText option3 = findViewById(R.id.option3Input);
        FloatingActionButton addQues = findViewById(R.id.addQuesButton);
        Button saveButton = findViewById(R.id.saveButton);
        
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!save) {
                    quizTitle = title.getText().toString();
                    quesText = QuesquesText.getText().toString();
                    quesCorrectAns = correctAns.getText().toString();
                    quesOption1 = option1.getText().toString();
                    quesOption2 = option2.getText().toString();
                    quesOption3 = option3.getText().toString();
                    createQuiz(String quizTitle, quesText, quesCorrectAns, quesOption1, quesOption2, quesOption3);
                }
            }
        });
    }

    private void createQuiz(String quizTitle, String quesText, String quesCorrectAns, String quesOption1, String quesOption2, String quesOption3) {
        Log.d("TAG", "CreateQuiz");
        CollectionReference collectionReference = db.collection("QUIZ");
        collectionReference.orderBy("dateCreated", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Quiz> quizList = new ArrayList<>();
                for (QueryDocumentSnapshot dc : task.getResult()) {
                    Quiz quiz = convertDocumentToQuiz(dc);
                    quizList.add(quiz);
                }
                quizID = generateQuizID(quizList);
                Quiz newQuiz = new Quiz(quizID, quizTitle, advisorID);
                insertQuizIntoDatabase(newQuiz);
            }
        });
    }

    private void insertQuizIntoDatabase(Quiz quiz) {
        Map<String, Object> map = new HashMap<>();
        map.put("advisorID", quiz.getAdvisorID());
        map.put("title", quiz.getQuizTitle());
        db.collection("QUIZ").document(quiz.getQuizID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    save = true;
                    Log.d("TAG", "uploaded");
                    Toast.makeText(Create_Quiz.this, "Quiz Created!", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("TAG", "Failed");
                    Toast.makeText(Create_Quiz.this, "Failed to Create Quiz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Quiz convertDocumentToQuiz(QueryDocumentSnapshot dc) {
        Quiz quiz = new Quiz();
        quiz.setQuizID(dc.getId());
        quiz.setAdvisorID(dc.get("advisorID").toString());
        quiz.setQuizTitle(dc.get("title").toString());
        return quiz;
    }

    private String generateQuizID(ArrayList<Quiz> quiz) {
        String newID;
        Random rand = new Random();
        while(true) {
            int randomNum = rand.nextInt(1000000);
            newID = "Q" + String.format("%07d", randomNum); //Q0001000
            if(checkDuplicatedQuizID(newID, quiz))
                break;
        }
        Log.d("TAG", "This is new quizID " + newID);
        return newID;
    }

//    private String generateQuesID(ArrayList<Question> ques) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        int length = 10;
//        Random rand = new Random();
//        StringBuilder newID = new StringBuilder();
//        while (true) {
//            for (int i = 0; i < length; i++) {
//                int index = rand.nextInt(characters.length());
//                newID.append(characters.charAt(index));
//            }
//
//            String generatedID = newID.toString();
//
//            if (checkDuplicatedTopicID(generatedID, ques)) {
//                break;
//            } else {
//                newID.setLength(0);
//            }
//        }
//        Log.d("TAG", "This is new quesID " + newID.toString());
//        return newID.toString();
//    }

    private boolean checkDuplicatedQuizID(String newID, ArrayList<Quiz> quiz){
        for(Quiz topic: quiz){
            if(newID.equals(topic.getQuizID()))
                return false;
        }
        Log.d("TAG", "This is checked topic ID " + newID);
        return true;
    }
}
// quesID = generateQuesID();
//        Question ques = new Question(quesID, quesText, quesCorrectAns, quesOption1, quesOption2, quesOption3);
