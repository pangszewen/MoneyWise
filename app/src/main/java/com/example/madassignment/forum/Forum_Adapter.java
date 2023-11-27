package com.example.madassignment.forum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.Firebase;
import com.example.madassignment.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Forum_Adapter extends RecyclerView.Adapter<Forum_Adapter.Forum_AdapterVH> {
    List<ForumTopic> forumTopics = new ArrayList<>();
    Context context;

    public Forum_Adapter(Context context, List<ForumTopic> forumTopics){
        this.forumTopics = forumTopics;
        this.context = context;
    }
    @androidx.annotation.NonNull
    @Override
    public Forum_AdapterVH onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.forum_topic_row, parent, false);
        return new Forum_AdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull Forum_AdapterVH holder, int position) {
        ForumTopic forumTopic = forumTopics.get(position);
        String topicSubject = forumTopic.getSubject();
        int topicLikes = forumTopic.getLikes();
        LocalDateTime topicDate = forumTopic.getDatePosted();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedTopicDate = topicDate.format(formatter);
        int topicComments = forumTopic.getCommentID().size();

        holder.topicSubject.setText(topicSubject);
        holder.topicLikes.setText(String.valueOf(topicLikes));
        holder.topicDate.setText(formattedTopicDate);
        holder.topicComments.setText(String.valueOf(topicComments));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "clicked");
                Log.d("TAG", forumTopic.getCommentID().toString());
                Intent intent = new Intent(context, Forum_IndividualTopic_Activity.class);
                // pass data from this activity to another activity
                // must be String
                intent.putExtra("topicID", forumTopic.getTopicID());
                intent.putExtra("userID", forumTopic.getUserID());
                intent.putExtra("datePosted", forumTopic.getDatePosted().toString());
                intent.putExtra("subject", forumTopic.getSubject());
                intent.putExtra("description", forumTopic.getDescription());
                intent.putExtra("likes", Integer.toString(forumTopic.getLikes()));
                intent.putExtra("commentID", forumTopic.getCommentID().toString());
                Log.d("TAG", "inserted into intent: " + intent.getStringExtra("commentID"));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return forumTopics.size();
    }

    public static class Forum_AdapterVH extends RecyclerView.ViewHolder{
        TextView topicSubject, topicLikes, topicDate, topicComments;
        public Forum_AdapterVH(@NonNull View itemView) {
            super(itemView);
            topicSubject = itemView.findViewById(R.id.topic_row_subject);
            topicLikes = itemView.findViewById(R.id.topic_row_likes);
            topicDate = itemView.findViewById(R.id.topic_row_date);
            topicComments = itemView.findViewById(R.id.topic_row_comments);
        }
    }

}
