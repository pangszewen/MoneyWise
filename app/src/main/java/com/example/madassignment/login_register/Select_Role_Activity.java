package com.example.madassignment.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.madassignment.R;

public class Select_Role_Activity extends AppCompatActivity {

    ImageButton btn_back;
    LinearLayout learner,advisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        btn_back=findViewById(R.id.btn_back);
        learner=findViewById(R.id.linearColumnLearner);
        advisor=findViewById(R.id.linearColumnAdvisor);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        learner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LearnerRegisterActivity.class));
                finish();
            }
        });
        advisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdvisorRegisterActivity.class));
                finish();
            }
        });
    }
}