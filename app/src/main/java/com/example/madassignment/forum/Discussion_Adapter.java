package com.example.madassignment.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Discussion_Adapter extends RecyclerView.Adapter<Discussion_Adapter.Discussion_AdapterVH> {
    ArrayList<ForumComment> forumComments = new ArrayList<>();
    Context context;
    public Discussion_Adapter(Context context, ArrayList<ForumComment> forumComments){
        this.forumComments = forumComments;
        this.context = context;
    }
    @androidx.annotation.NonNull
    @Override
    public Discussion_AdapterVH onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.forum_discussion_row, parent, false);
        return new Discussion_AdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull Discussion_AdapterVH holder, int position) {
        ForumComment forumComment = forumComments.get(position);
        String username = "Mary";
        LocalDateTime commentDate = forumComment.getDatePosted();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCommentDate = commentDate.format(formatter);
        String content = forumComment.getContent();

        holder.commentUsername.setText(username);
        holder.commentDatePosted.setText(formattedCommentDate);
        holder.commentContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return forumComments.size();
    }

    public static class Discussion_AdapterVH extends RecyclerView.ViewHolder{
        TextView commentUsername, commentDatePosted, commentContent;
        public Discussion_AdapterVH(@NonNull View itemView) {
            super(itemView);
            commentUsername = itemView.findViewById(R.id.discussion_username);
            commentDatePosted = itemView.findViewById(R.id.discussion_datePosted);
            commentContent = itemView.findViewById(R.id.discussion_content);
        }
    }

}
