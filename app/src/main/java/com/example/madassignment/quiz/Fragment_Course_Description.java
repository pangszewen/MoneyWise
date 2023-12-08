package com.example.madassignment.quiz;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Fragment_Course_Description extends Fragment {
    View view;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__course__description, container, false);
        db = FirebaseFirestore.getInstance();
        Button saveDesc = view.findViewById(R.id.saveDescButton);
        ImageButton editButton = view.findViewById(R.id.edit_button);
        EditText desc = view.findViewById(R.id.description_input);
        saveDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseDesc = desc.getText().toString();
                addDetails(courseDesc);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Edit_Quiz_Info.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void addDetails(String courseDesc) {
        Log.d("TAG", "createCourse");
        String courseID = "C0919761"; // Need to change
        DocumentReference docRef = db.collection("COURSE").document(courseID);
        docRef.update("description", courseDesc)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), "Description Saved", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Document updated successfully!");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to Save Description", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Error updating document", e);
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            String level = args.getString("level");
            String language= args.getString("language");
            String mode = args.getString("mode");
        }
    }
}