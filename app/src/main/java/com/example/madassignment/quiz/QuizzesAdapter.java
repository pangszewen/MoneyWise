package com.example.madassignment.quiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
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

public class QuizzesAdapter extends RecyclerView.Adapter<QuizzesAdapter.QuizViewHolder> implements Filterable {
    List<Quiz> quizList;
    List<Quiz> quizListFull;
    FirebaseFirestore db;
    FirebaseStorage storage;
    Context context;

    public QuizzesAdapter(Context context, List<Quiz> quizList) {
        this.quizList = quizList;
        this.context = context;
        quizListFull = new ArrayList<>(quizList);
    }

    @NonNull
    @Override
    public QuizzesAdapter.QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_quiz_display, parent, false);
        return new QuizzesAdapter.QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzesAdapter.QuizViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Quiz quiz = quizList.get(position);
        String quizTitle = quiz.getQuizTitle();
        String advisorID = quiz.getAdvisorID();

        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("USER_DETAILS").document(advisorID);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                holder.textViewAuthorName.setText(documentSnapshot.getString("name"));
            }
        });
        holder.imageViewQuizCover.setImageResource(R.drawable.outline_image_grey);
        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference("QUIZ_COVER_IMAGE/" + quiz.getQuizID()+"/");
        storageReference.listAll().addOnCompleteListener(new OnCompleteListener<ListResult>() {
            @Override
            public void onComplete(@NonNull Task<ListResult> task) {
                Log.d("TAG", "complete");
                if (task.isSuccessful()) {
                    Log.d("TAG", "success");
                    List<StorageReference> items = task.getResult().getItems();
                    Log.d("TAG", items.toString());
                    if (!items.isEmpty()) {
                        // Get the first item (image) in the folder
                        items.get(0).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String firstImageUri = uri.toString();
                                if (position == holder.getAdapterPosition()) {
                                    Picasso.get().load(firstImageUri).into(holder.imageViewQuizCover);
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
        holder.textViewQuizTitle.setText(quizTitle);
        holder.numOfQues.setText(quiz.getNumOfQues()+" Questions");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_individual_quiz_page.class);
                intent.putExtra("quizID", quiz.getQuizID());
                intent.putExtra("title", quiz.getQuizID());
                intent.putExtra("numOfQues", quiz.getNumOfQues());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    @Override
    public Filter getFilter() {
        return quizFilter;
    }

    public Filter quizFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Quiz> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(quizListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Quiz quiz : quizListFull){
                    if (quiz.getQuizTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(quiz);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            quizList.clear();
            quizList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewQuizCover;
        TextView textViewQuizTitle;
        TextView textViewAuthorName;
        TextView numOfQues;
        TextView numofPeopleTook;
        ImageButton takeQuizButton;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewQuizCover = itemView.findViewById(R.id.image_quiz_cover);
            textViewQuizTitle = itemView.findViewById(R.id.text_quiz_title);
            textViewAuthorName = itemView.findViewById(R.id.text_author_name);
            numOfQues = itemView.findViewById(R.id.numOfQues);
            numofPeopleTook = itemView.findViewById(R.id.numofPeopleTook);
            takeQuizButton = itemView.findViewById(R.id.takeQuizButton);
        }
    }
}
