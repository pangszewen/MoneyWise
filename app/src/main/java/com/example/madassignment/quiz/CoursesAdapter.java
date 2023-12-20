package com.example.madassignment.quiz;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {
    List<Course> courseList = new ArrayList<>();
    FirebaseFirestore db;
    FirebaseStorage storage;
    Context context;

    public CoursesAdapter(Context context, List<Course> courses) {
        this.courseList = courses;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_course_display, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        String courseTitle = course.getCourseTitle();
        System.out.println("title "+ courseTitle);
        String advisorID = course.getAdvisorID();
        System.out.println(advisorID);
        db = FirebaseFirestore.getInstance();
        if (advisorID != null) {
            DocumentReference ref = db.collection("USER_DETAILS").document(advisorID);
            ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        holder.textViewAuthorName.setText(documentSnapshot.getString("name"));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("Adapter", "Failed to retrieve advisor name: " + e.getMessage());
                }
            });
        } else {
            Log.e("Adapter", "Advisor ID is null");
        }
        holder.imageViewCourseCover.setImageResource(R.drawable.outline_image_grey);
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference("COURSE_COVER_IMAGE/" + course.getCourseID());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUri = uri.toString();
                Picasso.get().load(imageUri).into(holder.imageViewCourseCover);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Adapter", "Failed to load image: " + e.getMessage());
            }
        });
        holder.textViewCourseTitle.setText(courseTitle);
        holder.textViewAuthorName.setText("Poh Sharon");
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCourseCover;
        TextView textViewCourseTitle;
        TextView textViewAuthorName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCourseCover = itemView.findViewById(R.id.image_course_cover);
            textViewCourseTitle = itemView.findViewById(R.id.text_course_title);
            textViewAuthorName = itemView.findViewById(R.id.text_author_name);
        }
    }
}

