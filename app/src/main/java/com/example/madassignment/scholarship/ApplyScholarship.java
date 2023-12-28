package com.example.madassignment.scholarship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ApplyScholarship extends AppCompatActivity {

    String scholarshipID, institution, title, description, studyLevel, criteria, award, deadline, website;
    boolean saved;
    TextView txtTitle, txtAbout, txtValue, txtCriteria, txtWebsite;
    Button apply;
    FirebaseAuth auth;
    FirebaseUser user;
    String userID;
    FirebaseFirestore db;
    ImageView bookmark;
    boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_scholarship);

        txtTitle = findViewById(R.id.txtTitle);
        txtAbout = findViewById(R.id.txtAbout);
        txtValue = findViewById(R.id.txtValue);
        txtCriteria = findViewById(R.id.txtCriteria);
        txtWebsite = findViewById(R.id.txtWebsite);

        scholarshipID = getIntent().getStringExtra("scholarshipId");
        institution = getIntent().getStringExtra("institution");
        title = getIntent().getExtras().getString("title");
        description = getIntent().getExtras().getString("description");
        studyLevel = getIntent().getExtras().getString("studyLevel");
        criteria = getIntent().getExtras().getString("criteria");
        award = getIntent().getExtras().getString("award");
        deadline = getIntent().getExtras().getString("deadline");
        website = getIntent().getExtras().getString("website");
        isSaved = getIntent().getExtras().getBoolean("saved");

        Scholarship scholarship = new Scholarship(scholarshipID, institution, title, description, studyLevel, criteria, award, deadline, website, saved);

        txtTitle.setText(getIntent().getExtras().getString("title"));
        txtAbout.setText(getIntent().getExtras().getString("description"));
        txtValue.setText(getIntent().getExtras().getString("award"));
        txtCriteria.setText(getIntent().getExtras().getString("criteria"));
        txtWebsite.setText(getIntent().getExtras().getString("website"));


        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        userID = user.getUid();

        // Get the scholarship ID from the intent
        String scholarshipID = getIntent().getStringExtra("scholarshipID");


        //change bookmark according to saved/not saved

        bookmark = findViewById(R.id.imageSave);

        setBookmarkImage(isSaved);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the saved status
                isSaved = !isSaved;

                // Update the saved status in Firestore
                updateSavedStatusInFirestore(isSaved, userID, scholarshipID);

                // Update the bookmark image
                setBookmarkImage(isSaved);

                // Update the isSaved property in the Scholarship object
                scholarship.setSaved(isSaved);
            }
        });



        apply = findViewById(R.id.btnApply);

        // Set an OnClickListener for the TextView
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(getIntent().getExtras().getString("website")));
                startActivity(intent);
            }
        });


        // Header, back to the page before
        ImageView back = findViewById(R.id.imageArrowleft);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the origin activity from the Intent
                String originActivity = getIntent().getStringExtra("originActivity");

                // Handle the click event, navigate to the appropriate activity
                Intent intent;
                if ("FindScholarshipActivity".equals(originActivity)) {
                    intent = new Intent(ApplyScholarship.this, FindScholarshipActivity.class);
                } else if ("BookmarkActivity".equals(originActivity)) {
                    intent = new Intent(ApplyScholarship.this, BookmarkActivity.class);
                } else {
                    // Default to FindScholarshipActivity if not specified
                    intent = new Intent(ApplyScholarship.this, FindScholarshipActivity.class);
                }

                startActivity(intent);
            }
        });

        // press bookmark to add scholarship to saved

        ImageView save = findViewById(R.id.imageSave);

    }

    private void setBookmarkImage(boolean isSaved) {
        if (isSaved) {
            bookmark.setImageResource(R.drawable.img_bookmark_checked);
        } else {
            bookmark.setImageResource(R.drawable.img_bookmark_unchecked);
        }
    }

    private void updateSavedStatusInFirestore(boolean isSaved, String userId, String scholarshipId) {
        // Get the reference to the user_details document
        DocumentReference userRef = db.collection("USER_DETAILS").document(userId);

        // Update the saved_scholarship array
        if (isSaved) {
            // Add scholarship ID to the array
            userRef.update("saved_scholarship", FieldValue.arrayUnion(scholarshipId))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Update successful
                            Log.d("Firestore", "Saved scholarship ID added to user_details");
                            Toast.makeText(getApplicationContext(), "Scholarship added!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle error
                            Log.e("Firestore", "Error adding saved scholarship ID to user_details", e);
                        }
                    });
        } else {
            // Remove scholarship ID from the array
            userRef.update("saved_scholarship", FieldValue.arrayRemove(scholarshipId))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Update successful
                            Log.d("Firestore", "Saved scholarship ID removed from user_details");
                            Toast.makeText(getApplicationContext(), "Scholarship removed!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle error
                            Log.e("Firestore", "Error removing saved scholarship ID from user_details", e);
                        }
                    });
        }
    }

}