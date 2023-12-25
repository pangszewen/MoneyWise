package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;

public class activity_quiz_show_result extends AppCompatActivity {
    String quizTitle, quizID;
    double score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_show_result);

        Intent intent = getIntent();
        if (intent != null) {
            quizTitle = intent.getStringExtra("title");
            score = intent.getDoubleExtra("score", 0.0);
            quizID = intent.getStringExtra("quizID");
        }

        TextView display_text = findViewById(R.id.TVdisplay);
        TextView score_text = findViewById(R.id.score_text);
        ImageView image = findViewById(R.id.image);
        Button retryButton = findViewById(R.id.retryButton);

        if (score >= 80){
            display_text.setText("Congratulations!");
            score_text.setText("Your Score: "+score+"%");
            image.setImageResource(R.drawable.trophy_remix_by_monsterbraingames);
        } else if (score >= 50) {
            display_text.setText("Good Job!");
            score_text.setText("Your Score: "+score+"%");
            image.setImageResource(R.drawable.keep_it_up_vector_image);
        } else {
            display_text.setText("Don't Give Up!");
            score_text.setText("Your Score: "+score+"%");
            image.setImageResource(R.drawable.practice_makes_perfect_image_vector);
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_quiz_show_result.this, activity_individual_quiz_page.class);
                intent.putExtra("title", quizTitle);
                intent.putExtra("quizID", quizID);
                startActivity(intent);
            }
        });
    }
}