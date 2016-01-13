package com.example.wseif.malmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private boolean mTwoPane;

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILACTIVITYFRAGMENT_TAG = "DFTAG";

//    MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_details_activity) != null) {

            mTwoPane = true;

//            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.mainFragment);
//            if(mainActivityFragment != null){
//                mainActivityFragment.setCallbackHandler(this);
//            }

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_details_activity, new DetailsActivityFragment(), DETAILACTIVITYFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainFragment);
        if (mainActivityFragment != null) {
            mainActivityFragment.setCallbackHandler(this);
        }

        DetailsActivityFragment detailsActivityFragment = (DetailsActivityFragment) getSupportFragmentManager()
                .findFragmentByTag(DETAILACTIVITYFRAGMENT_TAG);
        if (detailsActivityFragment != null) {

        }

        //clearFavoriteMovies();
//        movieListAdapter = new MovieListAdapter(getApplicationContext());
//
//        GridView gridView = (GridView)findViewById(R.id.gridview);
//        gridView.setAdapter(movieListAdapter);
    }

    @Override
    public void OnItemSelected(Movie movie, boolean auto) {
        if (mTwoPane) {

            Bundle args = new Bundle();
            args.putSerializable("selectedMovie", movie);

            DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();
            detailsActivityFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_details_activity, detailsActivityFragment, DETAILACTIVITYFRAGMENT_TAG)
                    .commit();
        } else {
            if (!auto) {
                Intent intent = new Intent(this, DetailsActivity.class).putExtra("selectedMovie", movie);
                startActivity(intent);
            }
        }
    }

    //    @Override
//    public void onStart() {
//        super.onStart();
//        updateMovies();
//    }

//    private void updateMovies(){
//        FetchDataTask movieTask = new FetchDataTask();
//        movieTask.setJsonHandler(new Movie());
//        movieTask.execute(getMoviesUrl());
//        try {
//            List<JsonHandler> movies = movieTask.get();
//            movieListAdapter.clear();
//            for (JsonHandler jsonHandler : movies) {
//                movieListAdapter.add((Movie) jsonHandler);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainFragment);
        if (mainActivityFragment != null) {
            mainActivityFragment.setCallbackHandler(this);
        }

        DetailsActivityFragment detailsActivityFragment = (DetailsActivityFragment) getSupportFragmentManager()
                .findFragmentByTag(DETAILACTIVITYFRAGMENT_TAG);
        if (detailsActivityFragment != null) {

        }
    }

//    private String getMoviesUrl() {
//        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
//        final String SORT_PARAM = "sort_by";
//        final String SORT_ORDER_PARAM = ".desc";
//        final String APPID_PARAM = "api_key";
//        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";
//
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String filter = prefs.getString(getString(R.string.pref_filter_key), getString(R.string.pref_filter_default));
//
//        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
//                .appendQueryParameter(SORT_PARAM, filter + SORT_ORDER_PARAM)
//                .appendQueryParameter(APPID_PARAM, APPID_Value)
//                .build();
//
//        return buildUri.toString();
//    }

//    private void loadFavoritesMovies() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
//        Type type = new TypeToken<List<Movie>>() {
//        }.getType();
//        ArrayList<Movie> favoriteMoviesList = new Gson().fromJson(favoriteMoviesJson, type);
//        movieListAdapter.clear();
//        if (favoriteMoviesList != null && favoriteMoviesList.size() > 0) {
//            for (Movie movie : favoriteMoviesList) {
//                movieListAdapter.add(movie);
//            }
//        }
//    }

//    private void clearFavoriteMovies() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(getString(R.string.pref_favorite_movies_key), null);
//        editor.commit();
//    }
}
