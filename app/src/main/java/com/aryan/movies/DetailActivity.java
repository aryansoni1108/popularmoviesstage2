package com.aryan.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.synopsis_text_view)TextView synopsis_textview;
    @BindView(R.id.place_image)ImageView imageView;
    @BindView(R.id.rating_num_view)TextView rating_text;

    @BindView(R.id.released_date_view)TextView date_view;
    @BindView(R.id.reviews_recyclerview)RecyclerView review_recyclerview;
    @BindView(R.id.videos_recycler_view)RecyclerView videos_recycler;
    @BindView(R.id.collapsing_toolbar)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailtoolbar)

    Toolbar toolbar1;
    @BindView(R.id.titleview)TextView titleview;
    public String MOVIE_ID;
    Context context = DetailActivity.this;
    VideoRecyclerAdapter mAdapter;
    reviewRecyclerAdapter mAdapter2;
    @BindView(R.id.save_fab)FloatingActionButton savefab;
    private MovieDatabase mDb;
    Movies movies;
    public Boolean isThere=true;
    private static final Long DEFAULT_TASK_ID = -1L;
    private Long mTaskId = DEFAULT_TASK_ID;
    public static final String INSTANCE_TASK_ID = "instanceTaskId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar1);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movies=getIntent().getParcelableExtra("parcel");
        collapsingToolbarLayout.setTitleEnabled(false);
        review_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        videos_recycler.setLayoutManager(new LinearLayoutManager(this));

        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.super.onBackPressed();
            }
        });

        Log.e("Title",movies.getTitle());


        mDb = MovieDatabase.getInstance(this);
        synopsis_textview.setText(movies.getOverview());
        Log.e("backdrop","http://image.tmdb.org/t/p/w500"+movies.getBackdrop_path());
        rating_text.setText(movies.getVote_average());
        date_view.setText(movies.getRelease_date());
        titleview.setText(movies.getTitle());
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+movies.getBackdrop_path()).placeholder(R.drawable.placeholderimage).error(R.drawable.no_image).into(imageView);



        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovie_saved().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> moviePojos) {
                Boolean isitthere=false;
                for(int i=0;i<moviePojos.size();i++){
                    if(moviePojos.get(i).getId()== movies.getId()){

                        isitthere=true;


                    }

                }
                if (isitthere==false){
                    savefab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            savefab.setImageResource(R.drawable.ic_favorite_black_24dp);
                            FetchMovieRepo.FavMovie(mDb,context,movies);
                        }
                    });

                }
                else {
                    savefab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    savefab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FetchMovieRepo.deletemoviebyid(mDb,context,movies);
                            savefab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            finish();
                        }
                    });
                }
            }

        });
        onFetchMovieReviews onFetchMovieReviews = new onFetchMovieReviews() {
            @Override
            public void onFetchMovieReviewsCompleted(List<MovieReviews> movieReviews) {
                Log.e("dld","osooso");
                reviewRecyclerAdapter reviewRecyclerAdapter = new reviewRecyclerAdapter(movieReviews);
                review_recyclerview.setAdapter(reviewRecyclerAdapter);

            }
        };
        FetchMovieRepo.getReviews(String.valueOf(movies.getId()),getResources().getString(R.string.apikey),onFetchMovieReviews,DetailActivity.this);
        onFetchMovieVideos onFetchMovieVideos = new onFetchMovieVideos() {
            @Override
            public void onFetchMovieVideosCompleted(List<MovieVideos> moviedetails) {
                VideoRecyclerAdapter videoRecyclerAdapter = new VideoRecyclerAdapter(moviedetails,DetailActivity.this);
                videos_recycler.setAdapter(videoRecyclerAdapter);
            }
        };
        FetchMovieRepo.getVideoDetails(String.valueOf(movies.getId()),getResources().getString(R.string.apikey),onFetchMovieVideos,DetailActivity.this);
    }
}



