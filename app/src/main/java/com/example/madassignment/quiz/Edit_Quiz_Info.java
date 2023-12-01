package com.example.madassignment.quiz;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Edit_Quiz_Info extends AppCompatActivity {

    FirebaseFirestore db;
    RadioButton level, language, mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz_info);

        db = FirebaseFirestore.getInstance();
        Button saveInfoButton = findViewById(R.id.saveInfoButton);

        RadioGroup levelRadioGroup = findViewById(R.id.level_radio_group);
        RadioGroup languageRadioGroup = findViewById(R.id.language_radio_group);
        RadioGroup modeRadioGroup = findViewById(R.id.mode_radio_group);

        saveInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int levelID = levelRadioGroup.getCheckedRadioButtonId();
                level = findViewById(levelID);
                String courseLevel = level.getText().toString();

                int languageID = languageRadioGroup.getCheckedRadioButtonId();
                language = findViewById(languageID);
                String courseLanguage = language.getText().toString();

                int modeID = modeRadioGroup.getCheckedRadioButtonId();
                mode = findViewById(modeID);
                String courseMode = mode.getText().toString();

                Fragment_Course_Lessons fragmentLesson = new Fragment_Course_Lessons();
                Bundle args = new Bundle();
                args.putString("level", courseLevel);
                args.putString("language", courseLanguage);
                args.putString("mode", courseMode);
                fragmentLesson.setArguments(args);
                addInfo(courseLevel, courseLanguage, courseMode);

                Intent intent = new Intent(Edit_Quiz_Info.this, Create_Course.class);
                intent.putExtra("DescFrag", "DescriptionFragment");
                startActivity(intent);


            }
        });
    }

    private void addInfo(String courseLevel, String courseLanguage, String courseMode) {
        Log.d("TAG", "createCourse");
        String courseID = "C0919761"; // Need to change
        DocumentReference docRef = db.collection("COURSE").document(courseID);
        docRef.update("level", courseLevel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       Log.d(TAG, "Level updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating level", e);
                    }
                });
        docRef.update("language", courseLanguage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Language updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating language", e);
                    }
                });
        docRef.update("mode", courseMode)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Mode updated successfully!");
                        Toast.makeText(Edit_Quiz_Info.this, "Info Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating mode", e);
                        Toast.makeText(Edit_Quiz_Info.this, "Failed to Save Info", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}