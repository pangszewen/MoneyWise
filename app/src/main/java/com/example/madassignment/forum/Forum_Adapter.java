package com.example.madassignment.forum;

import android.content.Context;
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
    ArrayList<ForumTopic> forumTopics = new ArrayList<>();
    Context context;

    public Forum_Adapter(Context context, ArrayList<ForumTopic> forumTopics){
        Log.d("TAG", "Forum_Adapter");
        Log.d("TAG", String.valueOf(forumTopics.size()));
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

        holder.topicSubject.setText(topicSubject);
        holder.topicLikes.setText(String.valueOf(topicLikes) + holder.topicLikes.getText());
        holder.topicDate.setText(holder.topicDate.getText() + formattedTopicDate);
    }

    @Override
    public int getItemCount() {
        return forumTopics.size();
    }

    public static class Forum_AdapterVH extends RecyclerView.ViewHolder{
        TextView topicSubject, topicLikes, topicDate;
        public Forum_AdapterVH(@NonNull View itemView) {
            super(itemView);
            topicSubject = itemView.findViewById(R.id.topic_row_subject);
            topicLikes = itemView.findViewById(R.id.topic_row_likes);
            topicDate = itemView.findViewById(R.id.topic_row_date);
        }
    }

}
