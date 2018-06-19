package com.aryan.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.gridview)GridView gridView;
    @BindView(R.id.main_activity_toolbar)
    android.support.v7.widget.Toolbar toolbar;
    ImageAdapter imageAdapter;
    Context context = MainActivity.this;
    MovieDatabase mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mdb = MovieDatabase.getInstance(this);
        getSortMethod();
        Log.e("Favorite","yes"+getSortMethod());


        if(savedInstanceState==null&& FetchMovieRepo.isConnected(context)) {
            Log.e("no","fav");
                fetchmovies(getSortMethod(), context);
        }
        else if(savedInstanceState==null&& !FetchMovieRepo.isConnected(context)){
            setupViewModel();

        }
        else{
            setupViewModel();
            Parcelable[] parcelable = new Parcelable[0];
            if (savedInstanceState != null) {
                parcelable = savedInstanceState.
                        getParcelableArray(getString(R.string.saved_instance_movies));
            }

            int numMovieObjects = 0;
            if (parcelable != null) {
                numMovieObjects = parcelable.length;
            }
            Movies[] MoviePojo = new Movies[numMovieObjects];
            ArrayList<Movies> save = new ArrayList<>();
            for (int i = 0; i < numMovieObjects; i++) {
                MoviePojo[i] = (Movies) parcelable[i];
                save.add(i,MoviePojo[i]);

            }

            gridView.setAdapter(new ImageAdapter(this, save));
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movies movies = (Movies) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("parcel", movies);

                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cataegory, menu);
        return true;

    }

    public String getSortedUrl(String sortMethod){
        String url = "https://api.themoviedb.org/3/movie/" +
                sortMethod +
                "?api_key=" +
                getResources().getString(R.string.apikey);
        return url;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popularMovies_menu_item:
                updateSharedPrefs(getString(R.string.popular_sort_mthd));


                fetchmovies(getSortMethod(), context);
                updateSharedPrefs1(0);

                break;
            case R.id.highest_rated_menu_item:

                updateSharedPrefs(getString(R.string.top_rated_sort_mthd));
                fetchmovies(getSortMethod(), context);
                updateSharedPrefs1(0);


                break;
            case R.id.favorites_menu_item:
                updateSharedPrefs1(1);
                setupViewModel();
                removeviewmodel();


        }
        return super.onOptionsItemSelected(item);
    }
    public void fetchmovies(String sortmethod, final Context context){
        onFetchTaskCompleted onFetchTaskCompleted = new onFetchTaskCompleted() {
            @Override
            public void onFetchMoviesTaskCompleted(List<Movies> movies) {
                populateui(movies);
            }
        };
        FetchMovieRepo.fetchMovieDetails(getSortedUrl(sortmethod),onFetchTaskCompleted,MainActivity.this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Log.v(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        int numMovieObjects = gridView.getCount();
        if (numMovieObjects > 0) {
            Movies[] MoviePojo = new Movies[numMovieObjects];
            for (int i = 0; i < numMovieObjects; i++) {
                // Get Movie objects from gridview
                MoviePojo[i] = (Movies) gridView.getItemAtPosition(i);
            }

            // Save Movie objects to bundle
            outState.putParcelableArray(getString(R.string.saved_instance_movies), MoviePojo);
        }
    }
    private void setupViewModel() {

        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovie_saved().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> moviePojos) {

                if(getSortMethod1()==1) {
                    populateui(moviePojos);
                }




            }
        });

    }

    private void populateui(@Nullable List<Movies> moviePojos) {
        ImageAdapter imageAda = new ImageAdapter(context,moviePojos);
        imageAda.notifyDataSetChanged();
        gridView.setAdapter(imageAda);
    }

    private void updateSharedPrefs(String sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_sort_method_key), sortMethod);
        editor.apply();
    }
    private void updateSharedPrefs1(int sortMethod) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.pref_sort_method_fav), sortMethod);
        editor.apply();
    }
    private String getSortMethod() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        return prefs.getString(getString(R.string.pref_sort_method_key),
                "popular");
    }
    private int getSortMethod1() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        return prefs.getInt(getString(R.string.pref_sort_method_fav),0);
    }
    public void removeviewmodel(){
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovie_saved().observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> moviePojos) {

                viewModel.getMovie_saved().removeObserver(this);



            }
        });
    }



}






