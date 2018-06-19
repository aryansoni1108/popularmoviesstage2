package com.aryan.movies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aryan Soni on 6/18/2018.
 */

public class FetchMovieRepo {
    public FetchMovieRepo(){


    }
    public static void fetchMovieDetails(String url, final onFetchTaskCompleted l, final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    List<Movies> moviesList = Arrays.asList(gson.fromJson(String.valueOf(jsonArray),Movies[].class));
                    l.onFetchMoviesTaskCompleted(moviesList);
                    Log.e("testing",""+moviesList.get(0).getTitle());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,context.getString(R.string.volley_error),Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);




    }
    public static void getReviews(String Id, String apikey, final onFetchMovieReviews l, final Context context){
        String url = "https://api.themoviedb.org/3/movie/"+Id+"/reviews?api_key="+apikey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray reviewsarray = jsonObject.getJSONArray("results");

                    List<MovieReviews> movieReviews = Arrays.asList(gson.fromJson(String.valueOf(reviewsarray),MovieReviews[].class));
                    l.onFetchMovieReviewsCompleted(movieReviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,context.getResources().getString(R.string.volley_error),Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);


    }
    public static void getVideoDetails(String id, String apikey, final onFetchMovieVideos l, final Context context){
        String url = "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key="+apikey;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray videodetail_array = jsonObject.getJSONArray("results");
                    List<MovieVideos> movieVideos = Arrays.asList(gson.fromJson(String.valueOf(videodetail_array),MovieVideos[].class));
                    l.onFetchMovieVideosCompleted(movieVideos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,context.getResources().getString(R.string.volley_error),Toast.LENGTH_SHORT).show();
            }


        }
        );

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
    public static Boolean isConnected(Context context){
        Boolean isConnectedflag = false;
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr != null) {
            if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {

                // notify user you are online
                isConnectedflag = true;

            }

            else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                    || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

                // notify user you are not online
                isConnectedflag = false;
            }
        }
        return isConnectedflag;

    }
    public static void FavMovie(final MovieDatabase mDb,Context context, final Movies movies){


        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insertMovie(movies);
            }
        });
    }
    public static void deletemoviebyid(final MovieDatabase mdb,Context context, final Movies movie) {

        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mdb.movieDao().deleteTask(movie);
            }
        });
    }



    public static void loadallmovies(final Context context, final onFetchFavorite onFetchTaskCompleted, FragmentActivity activity){
        final MainViewModel viewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
        viewModel.getMovie_saved().observe(activity, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> movies) {
                viewModel.getMovie_saved().removeObserver(this);
                viewModel.getMovie_saved().removeObserver(this);
                savedmovies savedmovies = new savedmovies();
                savedmovies.setMovies(movies);
                onFetchTaskCompleted.onFetchFavorite(movies);
                if(movies.size()==0){
                    Toast.makeText(context,"No favorite movies",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
