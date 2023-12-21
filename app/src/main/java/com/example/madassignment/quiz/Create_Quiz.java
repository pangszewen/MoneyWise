package com.example.madassignment.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.madassignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class Create_Quiz extends AppCompatActivity {
    String quesID;
    String quizTitle;
    
    String quesText, quesCorrectAns, quesOption1, quesOption2, quesOption3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

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
                quizTitle = title.getText().toString();
                quesText = QuesquesText.getText().toString();
                quesCorrectAns = correctAns.getText().toString();
                quesOption1 = option1.getText().toString();
                quesOption2 = option2.getText().toString();
                quesOption3 = option3.getText().toString();
                quesID = generateQuesID();
                Question ques = new Question(quesID, quesText, quesCorrectAns, quesOption1, quesOption2, quesOption3);
            }
        });
        
        
    }

    private String generateQuesID() {
    }
}
