package com.example.madassignment.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {
    private List<Lesson> lessonList;
    private Context context;

    public LessonsAdapter(Context context, List<Lesson> lessonList) {
        this.context = context;
        this.lessonList = lessonList;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_lesson_display, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);

        String lessonTitle = "Lesson " + (position + 1);
        holder.TVLessonTitle.setText(lessonTitle);
        VideoView videoView = holder.VVLesson;
        videoView.setVideoPath(lesson.getLessonUrl());
        videoView.setOnPreparedListener(mp -> {
            mp.start();
            int totalDuration = videoView.getDuration(); // Get total duration in milliseconds
            int durationSeconds = totalDuration / 1000;
            int minutes = durationSeconds / 60;
            int seconds = durationSeconds % 60;
            String durationString = String.format("%02d:%02d", minutes, seconds);
            lesson.setLessonDuration(durationString); // Set duration to Lesson object
            holder.TVLessonDuration.setText(durationString); // Update duration in the ViewHolder
        });

        videoView.setOnClickListener(v -> {
        });
    }
    @Override
    public int getItemCount() {
        return lessonList.size();
    }
    public class LessonViewHolder extends RecyclerView.ViewHolder {
        VideoView VVLesson;
        TextView TVLessonTitle;
        TextView TVLessonDuration;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            VVLesson = itemView.findViewById(R.id.VVLesson);
            TVLessonTitle = itemView.findViewById(R.id.TVLessonTitle);
            TVLessonDuration = itemView.findViewById(R.id.TVLessonDuration);
        }
    }
}


