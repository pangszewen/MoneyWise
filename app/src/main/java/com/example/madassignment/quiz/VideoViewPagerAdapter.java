package com.example.madassignment.quiz;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.madassignment.R;

import java.util.List;

public class VideoViewPagerAdapter extends FragmentPagerAdapter {
    private List<Uri> videoUris;
    LayoutInflater layoutInflater;

    public VideoViewPagerAdapter(FragmentManager fm, List<Uri> videoUris, LayoutInflater inflater) {
        super(fm);
        this.videoUris = videoUris;
        this.layoutInflater = inflater;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Create a fragment for each video URI
        return VideoFragment.newInstance(videoUris.get(position));
    }

    @Override
    public int getCount() {
        return videoUris.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (layoutInflater != null) {
            View view = layoutInflater.inflate(R.layout.course_viewpager_video, container, false);
            VideoView videoView = view.findViewById(R.id.uploadVideo);
            videoView.setVideoURI(videoUris.get(position));
            container.addView(view);
            return view;
        } else {
            return super.instantiateItem(container, position);
        }
    }
}
