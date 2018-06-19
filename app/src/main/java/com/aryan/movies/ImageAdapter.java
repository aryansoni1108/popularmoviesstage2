package com.aryan.movies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Movies> mMovieurl = new ArrayList<>();


    public ImageAdapter(Context context, List<Movies> movieurl) {
        mContext = context;
        mMovieurl = movieurl;
    }

    @Override
    public int getCount() {
        if (mMovieurl.size() == 0) {
            return -1;
        } else {
            return mMovieurl.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (mMovieurl.size() == 0) {
            return null;
        }

        return mMovieurl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        if (mContext != null) {
            Picasso.with(mContext)
                    .load("http://image.tmdb.org/t/p/w500"+mMovieurl.get(i).getPoster_path())
                    .resize(185, 278)
                    .error(R.drawable.no_image)
                    .placeholder(R.drawable.placeholderimage)
                    .into(imageView);
            Log.e("dkdk9999", mMovieurl.get(i).getPoster_path());
        }

        return imageView;

    }
}





