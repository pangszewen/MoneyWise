package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Create_Quiz extends AppCompatActivity {

    private Map<RadioButton, EditText> radioButtonEditTextMap = new HashMap<>();
    private Map<RadioButton, EditText> radioButtonEditTextMap_initial = new HashMap<>();
    ArrayList<String> quesList = new ArrayList<>();
    ArrayList<ArrayList> optionsList = new ArrayList<>();
    CollectionReference collectionReference;
    Random rand = new Random();
    FirebaseFirestore db;
    String correctAns, quizID;
    Boolean saveState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CQ_MainActivity CQMainActivity = new CQ_MainActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
        db = FirebaseFirestore.getInstance();

        LayoutInflater inflater = getLayoutInflater();
        View questionLayout = inflater.inflate(R.layout.layout_question, null);

        EditText titleInput = findViewById(R.id.title_input);
        EditText questionInput = questionLayout.findViewById(R.id.question_input);

        EditText option1 = questionLayout.findViewById(R.id.option1_text);
        EditText option2 = questionLayout.findViewById(R.id.option2_text);
        EditText option3 = questionLayout.findViewById(R.id.option3_text);
        EditText option4 = questionLayout.findViewById(R.id.option4_text);

        RadioGroup radioGroup = questionLayout.findViewById(R.id.correctAns_radioGroup);
        RadioButton radioButton1 = questionLayout.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = questionLayout.findViewById(R.id.radioButton2);
        RadioButton radioButton3 = questionLayout.findViewById(R.id.radioButton3);
        RadioButton radioButton4 = questionLayout.findViewById(R.id.radioButton4);

        Button saveQuiz = findViewById(R.id.create_quiz_button);
        ImageButton backButton = findViewById(R.id.back_button);
        ImageButton addQues = findViewById(R.id.addQuesButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Quiz.this, CQ_MainActivity.class);
                startActivity(intent);
            }
        });
        addQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ques=questionInput.getText().toString();
                if(!ques.isEmpty())
                    quesList.add(questionInput.getText().toString());
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

                if (selectedRadioButton != null && radioButtonEditTextMap.containsKey(selectedRadioButton)) {
                    EditText selectedEditText = radioButtonEditTextMap.get(selectedRadioButton);
                    System.out.println("Got save correct ans");
                    correctAns = selectedEditText.getText().toString();
                }
                ArrayList<String> currentOptions = new ArrayList<>();
                currentOptions.add(correctAns);
                currentOptions.add(option1.getText().toString());
                currentOptions.add(option2.getText().toString());
                currentOptions.add(option3.getText().toString());
                currentOptions.add(option4.getText().toString());

                optionsList.add(currentOptions);

                clearEditTextFields();
                addQuestionLayout();
            }

            private void clearEditTextFields() {
                questionInput.getText().clear();
                option1.getText().clear();
                option2.getText().clear();
                option3.getText().clear();
                option4.getText().clear();
                radioGroup.clearCheck();
            }

            public void addQuestionLayout() {
                LayoutInflater inflater = getLayoutInflater();
                View questionLayout = inflater.inflate(R.layout.layout_question, null);

                EditText question_input = questionLayout.findViewById(R.id.question_input);
                EditText option1 = questionLayout.findViewById(R.id.option1_text);
                EditText option2 = questionLayout.findViewById(R.id.option2_text);
                EditText option3 = questionLayout.findViewById(R.id.option3_text);
                EditText option4 = questionLayout.findViewById(R.id.option4_text);

                RadioGroup radioGroup = questionLayout.findViewById(R.id.correctAns_radioGroup);
                RadioButton radioButton1 = questionLayout.findViewById(R.id.radioButton1);
                RadioButton radioButton2 = questionLayout.findViewById(R.id.radioButton2);
                RadioButton radioButton3 = questionLayout.findViewById(R.id.radioButton3);
                RadioButton radioButton4 = questionLayout.findViewById(R.id.radioButton4);

                radioButtonEditTextMap_initial.put(radioButton1, option1);
                radioButtonEditTextMap_initial.put(radioButton2, option2);
                radioButtonEditTextMap_initial.put(radioButton3, option3);
                radioButtonEditTextMap_initial.put(radioButton4, option4);

                LinearLayout questionContainer = findViewById(R.id.questionContainer);
                questionContainer.addView(questionLayout);
            }
        });
        saveQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quizTitle = titleInput.getText().toString();
                if (!saveState)
                    addQuiz(quizTitle);
            }

            public void addQuiz(String quizTitle) {
                Log.d("TAG", "createQuiz");
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
                        String advisorID = "A0000001"; // Need to change
                        Quiz newQuiz = new Quiz(quizID, advisorID, quizTitle);
                        insertQuizIntoDatabase(newQuiz);
                    }
                });
            }
            public Quiz convertDocumentToQuiz(QueryDocumentSnapshot dc){
                Quiz quiz = new Quiz();
                quiz.setQuizID(dc.getId());
                quiz.setAdvisorID(dc.get("advisorID").toString());
                quiz.setQuizTitle(dc.get("title").toString());
                return quiz;
            }

            private void insertQuizIntoDatabase(Quiz quiz) {
                Map<String, Object> map = new HashMap<>();
                map.put("advisorID", quiz.getAdvisorID());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDateTime = quiz.getDateCreated().format(formatter);
//        map.put("dateCreated", formattedDateTime);
                map.put("title", quiz.getQuizTitle());
                db.collection("QUIZ").document(quiz.getQuizID()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            saveState = true;
                            Toast.makeText(Create_Quiz.this, "Successfully Saved Quiz", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "uploaded");
                        }else {
                            Toast.makeText(Create_Quiz.this, "Fail to Create Quiz", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "Failed");
                        }
                    }
                });
            }

            private String generateQuizID(List<Quiz> quizes){
                String newID = null;
                while(true) {
                    int randomNum = rand.nextInt(1000000);
                    newID = "Q" + String.format("%07d", randomNum); //Q0001000
                    if(checkDuplicatedTopicID(newID, quizes))
                        break;
                }
                Log.d("TAG", "This is new quizID " + newID);
                return newID;
            }

            private boolean checkDuplicatedTopicID(String newID, List<Quiz> quizes){
                for(Quiz topic: quizes){
                    if(newID.equals(topic.getQuizID()))
                        return false;
                }
                Log.d("TAG", "This is checked topic ID " + newID);
                return true;
            }
    });
}
}
