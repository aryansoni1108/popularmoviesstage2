package com.aryan.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aryan Soni on 6/11/2018.
 */

public class reviewRecyclerAdapter extends RecyclerView.Adapter<reviewRecyclerAdapter.MyViewHolder>{
    List<MovieReviews> movieReviews=new ArrayList<>();


    public reviewRecyclerAdapter(List<MovieReviews> movieReviews){

        this.movieReviews=movieReviews;
    }
    @NonNull
    @Override
    public reviewRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewRecyclerAdapter.MyViewHolder holder, int position) {
        holder.author_name_text.setText(movieReviews.get(position).getAuthor());
        holder.review_textView.setText(movieReviews.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return movieReviews.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView author_name_text;TextView review_textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            author_name_text = itemView.findViewById(R.id.author_item_text);
            review_textView = itemView.findViewById(R.id.review_text);
        }

    }

}
