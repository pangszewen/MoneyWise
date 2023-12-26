package com.example.madassignment.scholarship;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScholarshipAdapter extends RecyclerView.Adapter<ScholarshipAdapter.ScholarshipViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Context context;
    private ArrayList<Scholarship> scholarshipArrayList;

    public ScholarshipAdapter(Context context, ArrayList<Scholarship> list) {
        this.context = context;
        this.scholarshipArrayList = list;
    }

    public void setFilteredList(ArrayList<Scholarship> filteredList){
        this.scholarshipArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScholarshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.scholarship_item, parent, false);
        return new ScholarshipViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarshipViewHolder holder, int position) {
        Scholarship scholarship = scholarshipArrayList.get(position);
        holder.title.setText(scholarship.getTitle() + " | " + scholarship.getInstitution());
        holder.deadline.setText(scholarship.getDeadline());
        holder.image.setImageResource(R.drawable.img_image201);

        holder.scholarshipCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ApplyScholarship.class);
                intent.putExtra("institution",scholarship.getInstitution());
                intent.putExtra("title", scholarship.getTitle());
                intent.putExtra("award", scholarship.getAward());
                intent.putExtra("deadline", scholarship.getDeadline());
                intent.putExtra("studyLevel", scholarship.getStudyLevel());
                intent.putExtra("description", scholarship.getDescription());
                intent.putExtra("criteria", scholarship.getCriteria());
                v.getContext().startActivity(intent);
            }
        });
//        boolean isSaved = scholarship.isSaved();
//        if (isSaved) {
//            holder.bookmarkImageView.setImageResource(R.drawable.img_bookmark_long_coloured);
//        } else {
//            holder.bookmarkImageView.setImageResource(R.drawable.img_bookmark_long_hollow);
//        }

//        holder.bookmarkImageView.setOnClickListener(view -> {
//            // Perform the add or remove operation based on the saved status
//            if (isSaved) {
//                removeFromSaved("L9250029", scholarship);
//            } else {
//                addToSaved("L9250029", scholarship);
//            }
//            if (isSaved) {
//                holder.bookmarkImageView.setImageResource(R.drawable.img_bookmark_long_coloured);
//            } else {
//                holder.bookmarkImageView.setImageResource(R.drawable.img_bookmark_long_hollow);
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return scholarshipArrayList.size();
    }

    public static class ScholarshipViewHolder extends RecyclerView.ViewHolder {

        TextView title,deadline;

        ImageView image;
        CardView scholarshipCard;

        public ScholarshipViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageImage201);
            title = itemView.findViewById(R.id.txtTitle);
            deadline = itemView.findViewById(R.id.txtDeadline);
            scholarshipCard = itemView.findViewById(R.id.scholarshipCard);
        }
    }


    private void addToSaved(String userID, Scholarship scholarship) {
        // Get the current list of saved scholarship IDs
        db.collection("SAVED_SCHOLARSHIP")
                .document(userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        List<String> savedScholarships;

                        if (document.exists()) {
                            savedScholarships = (List<String>) document.get("scholarship_id");
                        } else {
                            savedScholarships = new ArrayList<>();
                        }

                        // Check if the scholarship is not already in the saved list
                        if (!savedScholarships.contains(scholarship.getScholarshipID())) {
                            // Add the new scholarship ID to the list
                            savedScholarships.add(scholarship.getScholarshipID());

                            // Update the document with the modified list
                            Map<String, Object> data = new HashMap<>();
                            data.put("scholarship_id", savedScholarships);

                            // Update the document with the modified list
                            db.collection("SAVED_SCHOLARSHIP")
                                    .document(userID)
                                    .update("scholarship_id", savedScholarships)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(context, "Added to saved scholarships", Toast.LENGTH_SHORT).show();

                                        // Update the isSaved status of the scholarship
                                        scholarship.setSaved(true);

                                        // Notify the adapter that the dataset has changed
                                        notifyDataSetChanged();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed to update saved scholarships", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            // The scholarship is already in the saved list
                            Toast.makeText(context, "Scholarship is already saved", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("Firestore error", "Error retrieving saved scholarships", task.getException());
                        Toast.makeText(context, "Failed to retrieve saved scholarships", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void removeFromSaved(String userID, Scholarship scholarship) {
        db.collection("SAVED_SCHOLARSHIP")
                .document("L9250029")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> savedScholarships = (List<String>) document.get("scholarship_id");

                            // Check if the scholarship is in the saved list
                            if (savedScholarships != null && savedScholarships.contains(scholarship.getScholarshipID())) {
                                // Remove the scholarship ID from the list
                                savedScholarships.remove(scholarship.getScholarshipID());

                                // Update the document with the modified list
                                db.collection("SAVED_SCHOLARSHIP")
                                        .document(userID)
                                        .update("scholarship_id", savedScholarships)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(context, "Removed from saved scholarships", Toast.LENGTH_SHORT).show();

                                            // Update the isSaved status of the scholarship
                                            scholarship.setSaved(false);

                                            // Notify the adapter that the dataset has changed
                                            notifyDataSetChanged();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(context, "Failed to update saved scholarships", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        }
                    } else {
                        Log.e("Firestore error", "Error retrieving saved scholarships", task.getException());
                        Toast.makeText(context, "Failed to retrieve saved scholarships", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
