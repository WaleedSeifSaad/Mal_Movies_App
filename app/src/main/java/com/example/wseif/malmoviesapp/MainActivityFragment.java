package com.example.wseif.malmoviesapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivityFragment extends Fragment {

    MovieListAdapter movieListAdapter;
    private Callback mCallback;

    public void setCallbackHandler(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {
        void OnItemSelected(Movie movie, boolean auto);
    }

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.mainfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favorites_settings) {
            loadFavoritesMovies();
        }
        if (id == R.id.all_movies_settings) {
            updateMovies();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main_activity, container, false);
        movieListAdapter = new MovieListAdapter(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(movieListAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = (Movie) parent.getItemAtPosition(position);
                if (selectedMovie != null) {
                    mCallback.OnItemSelected(selectedMovie, false);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
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
        if (movieListAdapter.getCount() > 0 && mCallback != null) {
            mCallback.OnItemSelected((Movie) movieListAdapter.getItem(0), true);
        } else {
            mCallback.OnItemSelected(new Movie(), true);
        }
    }

    private String getMoviesUrl() {
        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String SORT_PARAM = "sort_by";
        final String SORT_ORDER_PARAM = ".desc";
        final String APPID_PARAM = "api_key";
        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String filter = prefs.getString(getString(R.string.pref_filter_key), getString(R.string.pref_filter_default));

        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, filter + SORT_ORDER_PARAM)
                .appendQueryParameter(APPID_PARAM, APPID_Value)
                .build();

        return buildUri.toString();
    }

    private void loadFavoritesMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
}
