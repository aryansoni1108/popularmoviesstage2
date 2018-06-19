package com.aryan.movies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aryan Soni on 6/11/2018.
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.MyViewHolder>{
    private List<MovieVideos> videosList;
    private Context context;


    public VideoRecyclerAdapter(List<MovieVideos> videosList,Context context){
        this.videosList=videosList;
        this.context=context;


    }

    @NonNull
    @Override
    public VideoRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(videosList.get(position).getname());

    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.video_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.youtubeappstring) + videosList.get(getAdapterPosition()).getkey()));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.youtubeurl) + videosList.get(getAdapterPosition()).getkey()));

            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }
            }

        }
    }
