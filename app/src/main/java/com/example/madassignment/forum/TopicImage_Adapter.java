package com.example.madassignment.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madassignment.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class TopicImage_Adapter extends RecyclerView.Adapter<TopicImage_Adapter.TopicImage_AdapterVH>{
    ArrayList<String> images = new ArrayList<>();
    Context context;
    public TopicImage_Adapter(Context context, ArrayList<String> images){
        this.images = images;
        this.context = context;
    }
    @androidx.annotation.NonNull
    @Override
    public TopicImage_Adapter.TopicImage_AdapterVH onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.forum_topic_image_row, parent, false);
        return new TopicImage_Adapter.TopicImage_AdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull TopicImage_AdapterVH holder , int position) {
        String image = images.get(position);
        Picasso.get().load(image).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class TopicImage_AdapterVH extends RecyclerView.ViewHolder{
        ImageView imageView;
        public TopicImage_AdapterVH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.IVTopic);
        }
    }
}
