package com.example.wseif.malmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        FetchMovieTask movieTask= new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String filter = prefs.getString(getString(R.string.pref_filter_key),getString(R.string.pref_filter_default));
        movieTask.execute(filter);
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
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieTask extends AsyncTask<String,Void,ArrayList<Movie>> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private ArrayList<Movie> getMovieDataFromJson(String moviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOVIE_RESULTS = "results";
            final String MOVIE_ORIGINAL_TITLE = "original_title";
            final String MOVIE_POSTER_PATH  = "poster_path";
            final String MOVIE_POSTER_PATH_BASE_URL  = "http://image.tmdb.org/t/p/";
            final String MOVIE_POSTER_PATH_SIZE  = "w185";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_VOTE_AVERAGE = "vote_average";
            final String MOVIE_RELEASE_DATE = "release_date";

            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray movieJsonArray = moviesJson.getJSONArray(MOVIE_RESULTS);

            ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
            for(int i = 0; i < movieJsonArray.length(); i++) {
                Movie movie = new Movie();

                JSONObject movieDetails = movieJsonArray.getJSONObject(i);
                movie.setOriginalTitle(movieDetails.getString(MOVIE_ORIGINAL_TITLE));
                movie.setPosterPath(MOVIE_POSTER_PATH_BASE_URL + MOVIE_POSTER_PATH_SIZE + movieDetails.getString(MOVIE_POSTER_PATH));
                movie.setOverview(movieDetails.getString(MOVIE_OVERVIEW));
                movie.setVoteAverage(movieDetails.getDouble(MOVIE_VOTE_AVERAGE));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    movie.setReleaseDate(format.parse(movieDetails.getString(MOVIE_RELEASE_DATE)));
                } catch (ParseException e) {
                    Log.e(LOG_TAG ,e.getMessage(),e);
                }
                movieArrayList.add(movie);
            }
            return movieArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            if(result != null){
                movieListAdapter.clear();
                for(Movie movie : result)
                    movieListAdapter.add(movie);
            }

        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String SORT_ORDER_PARAM = ".desc";
                final String APPID_PARAM = "api_key";
                final String APPID_Value = "Enter Your AppID Here";


                Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM,params[0]+SORT_ORDER_PARAM)
                        .appendQueryParameter(APPID_PARAM,APPID_Value)
                        .build();

                URL url = new URL(buildUri.toString());

                Log.v(LOG_TAG,"Build URI " + buildUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    moviesJsonStr = null;
                }
                moviesJsonStr = buffer.toString();

                Log.v(LOG_TAG,"Forecast JSON String: " + moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                moviesJsonStr = null;
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                moviesJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getMovieDataFromJson(moviesJsonStr);
            }
            catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return  null;
        }
    }

}
