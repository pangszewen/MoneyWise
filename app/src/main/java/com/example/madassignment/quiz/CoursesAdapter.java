package com.example.madassignment.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> implements Filterable {
    List<Course> courseList;
    List<Course> courseListFull;
    FirebaseFirestore db;
    FirebaseStorage storage;
    Context context;

    public CoursesAdapter(Context context, List<Course> courseList) {
        this.courseList = courseList;
        this.context = context;
        courseListFull = new ArrayList<>(courseList);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_course_display, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course course = courseList.get(position);
        String courseTitle = course.getCourseTitle();
        String advisorID = course.getAdvisorID();

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
        holder.textViewCourseTitle.setText(courseTitle);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity_individual_course_page.class);
                intent.putExtra("courseID", course.getCourseID());
                intent.putExtra("title", course.getCourseTitle());
                intent.putExtra("description", course.getCourseDesc());
                intent.putExtra("advisorID", course.getAdvisorID());
                intent.putExtra("language", course.getCourseLanguage());
                intent.putExtra("level", course.getCourseLevel());
                intent.putExtra("mode", course.getCourseMode());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    @Override
    public Filter getFilter() {
        return courseFilter;
    }

    public Filter courseFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Course> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(courseListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Course course : courseListFull){
                    if (course.getCourseTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(course);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            courseList.clear();
            courseList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCourseCover;
        TextView textViewCourseTitle;
        TextView textViewAuthorName;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCourseCover = itemView.findViewById(R.id.image_quiz_cover);
            textViewCourseTitle = itemView.findViewById(R.id.text_course_title);
            textViewAuthorName = itemView.findViewById(R.id.text_author_name);
        }
    }
}
