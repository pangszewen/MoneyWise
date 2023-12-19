package com.example.madassignment.quiz;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class VideoViewPagerAdapter extends FragmentPagerAdapter {
//    private Context context;
//    private List<Uri> videoList;
//
//    public VideoViewPagerAdapter(Context context, List<Uri> videoList) {
//        this.context = context;
//        this.videoList = videoList;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View itemView = inflater.inflate(R.layout.course_viewpager_video, container, false);
//
//        VideoView videoView = itemView.findViewById(R.id.uploadVideo);
//        videoView.setVideoURI(videoList.get(position));
//        videoView.start();
//
//        container.addView(itemView);
//
//        return itemView;
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public int getCount() {
//        return videoList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//}
private List<Uri> videoUris;

    public VideoViewPagerAdapter(FragmentManager fm, List<Uri> videoUris) {
        super(fm);
        this.videoUris = videoUris;
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
}
