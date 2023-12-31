package com.example.madassignment.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CoursesCompletedAdapter extends RecyclerView.Adapter<CoursesCompletedAdapter.CourseCompletedViewHolder>{
    List<Course> courseList;
    FirebaseFirestore db;
    FirebaseStorage storage;
    Context context;

    public CoursesCompletedAdapter(Context context, List<Course> courseList) {
        this.courseList = courseList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoursesCompletedAdapter.CourseCompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_complete_course, parent, false);
        return new CoursesCompletedAdapter.CourseCompletedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesCompletedAdapter.CourseCompletedViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course course = courseList.get(position);
        String courseTitle = course.getCourseTitle();
        String advisorID = course.getAdvisorID();
        String lessonNum = String.valueOf(course.getLessonNum());

        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("USER_DETAILS").document(advisorID); // Need change
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                holder.textViewAuthorName.setText(documentSnapshot.getString("name"));
            }
        });
        holder.imageViewCourseCover.setImageResource(R.drawable.outline_image_grey);
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference("COURSE_COVER_IMAGE/" + course.getCourseID()+"/");
        storageReference.listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                if (task.isSuccessful()) {
                    List<StorageReference> items = task.getResult().getItems();
                    if (!items.isEmpty()) {
                        items.get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String firstImageUri = uri.toString();
                                if (position == holder.getAdapterPosition()) {
                                    Picasso.get().load(firstImageUri).into(holder.imageViewCourseCover);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure if needed
                            }
                        });
                    }
                }
            }
        });

//        holder.textViewCourseProgress.setText(lessonNum+"/"+lessonNum);
        holder.textViewCourseTitle.setText(courseTitle);
//        holder.courseProgress.setProgress(10);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class CourseCompletedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCourseCover;
        TextView textViewCourseTitle;
        TextView textViewAuthorName;
//        LinearProgressIndicator courseProgress;
//        TextView textViewCourseProgress;

        public CourseCompletedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCourseCover = itemView.findViewById(R.id.image_course_cover);
            textViewCourseTitle = itemView.findViewById(R.id.text_course_title);
            textViewAuthorName = itemView.findViewById(R.id.text_author_name);
//            courseProgress = itemView.findViewById(R.id.lessonProgressBar);
//            textViewCourseProgress = itemView.findViewById(R.id.TVLessonProgress);
        }
    }
}


