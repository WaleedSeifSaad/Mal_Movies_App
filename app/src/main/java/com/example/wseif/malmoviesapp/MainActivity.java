package com.example.wseif.malmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //clearFavoriteMovies();
        movieListAdapter = new MovieListAdapter(getApplicationContext());

        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(movieListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies(){
        FetchDataTask movieTask = new FetchDataTask();
        movieTask.setJsonHandler(new Movie());
        movieTask.execute(getMoviesUrl());
        try {
            List<JsonHandler> movies = movieTask.get();
            movieListAdapter.clear();
            for (JsonHandler jsonHandler : movies) {
                movieListAdapter.add((Movie) jsonHandler);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.favorites_settings) {
            loadFavoritesMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    private String getMoviesUrl() {
        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String SORT_PARAM = "sort_by";
        final String SORT_ORDER_PARAM = ".desc";
        final String APPID_PARAM = "api_key";
        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String filter = prefs.getString(getString(R.string.pref_filter_key), getString(R.string.pref_filter_default));

        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, filter + SORT_ORDER_PARAM)
                .appendQueryParameter(APPID_PARAM, APPID_Value)
                .build();

        return buildUri.toString();
    }

    private void loadFavoritesMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
        Type type = new TypeToken<List<Movie>>() {
        }.getType();
        ArrayList<Movie> favoriteMoviesList = new Gson().fromJson(favoriteMoviesJson, type);
        movieListAdapter.clear();
        if (favoriteMoviesList != null && favoriteMoviesList.size() > 0) {
            for (Movie movie : favoriteMoviesList) {
                movieListAdapter.add(movie);
            }
        }
    }

    private void clearFavoriteMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.pref_favorite_movies_key), null);
        editor.commit();
    }
}
