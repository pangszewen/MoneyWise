package com.example.madassignment.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;
import com.google.android.material.textfield.TextInputEditText;

public class Create_Course extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 123;
    private ActivityResultLauncher<String> getContentLauncher;

    String courseDesc, courseLevel, courseLanguage, courseTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
//        TextInputEditText title = findViewById(R.id.titleInput);
//        TextInputEditText desc = findViewById(R.id.descInput);
        AutoCompleteTextView levelSpinner = findViewById(R.id.levelInput);
        AutoCompleteTextView languageSpinner = findViewById(R.id.languageInput);
        Button nextButton = findViewById(R.id.nextButton);

        ArrayAdapter<CharSequence> adapterLevel = ArrayAdapter.createFromResource(getApplicationContext(), R.array.level_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterLanguage = ArrayAdapter.createFromResource(getApplicationContext(), R.array.language_array, android.R.layout.simple_spinner_item);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapterLevel);
        languageSpinner.setAdapter(adapterLanguage);
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseLevel = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseLanguage = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText title = findViewById(R.id.titleInput);
                TextInputEditText desc = findViewById(R.id.descInput);

                courseTitle = title.getText().toString();
                courseDesc = desc.getText().toString();
                courseLevel = levelSpinner.getText().toString(); // Updated here
                courseLanguage = languageSpinner.getText().toString(); // Updated here

                Intent intent = new Intent(Create_Course.this, Create_Lesson.class);
                intent.putExtra("title", courseTitle);
                intent.putExtra("desc", courseDesc);
                intent.putExtra("language", courseLanguage);
                intent.putExtra("level", courseLevel);
                intent.putExtra("mode", "Online");
                startActivity(intent);
            }
        });
    }
}
