package com.example.madassignment.quiz;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.madassignment.R;

import java.util.List;

public class VideoViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Uri> videoList;

    public VideoViewPagerAdapter(Context context, List<Uri> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.course_viewpager_video, container, false);

        VideoView videoView = itemView.findViewById(R.id.uploadVideo);
        videoView.setVideoURI(videoList.get(position));
        videoView.start();

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
