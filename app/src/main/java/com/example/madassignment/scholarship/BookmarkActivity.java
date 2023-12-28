package com.example.madassignment.scholarship;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser user;

    String userID;

    RecyclerView recyclerView;
    ArrayList<Scholarship> bookmarkArrayList;
    ScholarshipAdapter bookmarkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        userID = user.getUid();

        recyclerView = findViewById(R.id.recyclerBookmarkList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        bookmarkArrayList = new ArrayList<Scholarship>();
        bookmarkAdapter = new ScholarshipAdapter(BookmarkActivity.this, bookmarkArrayList);

        recyclerView.setAdapter(bookmarkAdapter);

        EventChangeListener();

        ImageView scholarshipMain = findViewById(R.id.imageArrowleft);

        scholarshipMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, ScholarshipMainActivity.class);
                startActivity(intent);
            }
        });


    }
    private void EventChangeListener() {
        db.collection("SCHOLARSHIP")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                DocumentSnapshot documentSnapshot = dc.getDocument();

                                // Use the document ID as the scholarship ID
                                String scholarshipID = documentSnapshot.getId();

                                // Create a Scholarship object and set the scholarship ID
                                Scholarship scholarship = documentSnapshot.toObject(Scholarship.class);
                                scholarship.setScholarshipID(scholarshipID);

                                // Check if the scholarship is saved by the user
                                checkIfScholarshipIsSaved(scholarship);
                            }
                        }
                    }
                });
    }


    private void checkIfScholarshipIsSaved(final Scholarship scholarship) {

        db.collection("USER_DETAILS")
                .document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Retrieve the saved_scholarship array from user_details
                            List<String> savedScholarships = (List<String>) documentSnapshot.get("saved_scholarship");

                            // Check if the scholarship ID is in the saved_scholarship array
                            if (savedScholarships != null && savedScholarships.contains(scholarship.getScholarshipID())) {
                                // Set the saved field in the Scholarship object to true
                                scholarship.setSaved(true);

                                // Add the scholarship to the list if it is saved
                                bookmarkArrayList.add(scholarship);

                                // Notify the adapter that the data set has changed
                                bookmarkAdapter.notifyDataSetChanged();
                            } else {
                                // Set the saved field in the Scholarship object to false
                                scholarship.setSaved(false);
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore error", e.getMessage());
                    }
                });
    }

}