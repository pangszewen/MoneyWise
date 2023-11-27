package com.example.madassignment.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.madassignment.R;

public class Create_Quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        // Add Question Logic
        ImageButton addQues = findViewById(R.id.addQuesButton);
        addQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestionLayout();
            }
        });

//        // RadioGroup Selection and Deselection Logic
//        RadioGroup options = findViewById(R.id.correctAns_radioGroup);
//        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                // Handle the checked state change of RadioButtons in the RadioGroup
//                if (checkedId == R.id.radioButton1){
//
//                }
//                else if (checkedId == R.id.radioButton2){
//
//                }
//                else if (checkedId == R.id.radioButton3){
//
//                }
//                else {
//
//                }
//            }
//        });
//
//        // Handle deselection by clicking on a selected RadioButton again
//        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                if (checkedId == -1) {
//                    // No RadioButton is checked, do something here if needed
//                } else {
//                    RadioButton checkedRadioButton = findViewById(checkedId);
//                    checkedRadioButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            options.clearCheck(); // Clear the checked RadioButton
//                        }
//                    });
//                }
//            }
//        });
    }

    public void addQuestionLayout() {
        LayoutInflater inflater = getLayoutInflater();
        View questionLayout = inflater.inflate(R.layout.layout_question, null);

        EditText questionEditText = questionLayout.findViewById(R.id.question_input);
        EditText option1EditText = questionLayout.findViewById(R.id.option1_text);
        EditText option2EditText = questionLayout.findViewById(R.id.option2_text);
        EditText option3EditText = questionLayout.findViewById(R.id.option3_text);
        EditText option4EditText = questionLayout.findViewById(R.id.option4_text);

        RadioGroup optionsRadioGroup = questionLayout.findViewById(R.id.correctAns_radioGroup);
        RadioButton radioButton1 = questionLayout.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = questionLayout.findViewById(R.id.radioButton2);
        RadioButton radioButton3 = questionLayout.findViewById(R.id.radioButton3);
        RadioButton radioButton4 = questionLayout.findViewById(R.id.radioButton4);

        // Set unique IDs or other properties if needed for these elements

        LinearLayout questionContainer = findViewById(R.id.questionContainer);
        questionContainer.addView(questionLayout);
    }


}