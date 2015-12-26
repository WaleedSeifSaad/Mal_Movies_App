package com.example.wseif.malmoviesapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by wseif on 12/25/2015.
 */
public class FetchDataTask extends AsyncTask<String, Void, List<JsonHandler>> {

    private final String LOG_TAG = FetchDataTask.class.getSimpleName();
    private JsonHandler jsonHandler;

    public void setJsonHandler(JsonHandler jsonHandler) {
        this.jsonHandler = jsonHandler;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p/>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
//        private ArrayList<JsonHandler> getMoviesJsonFromResults(String moviesJsonStr)
//                throws JSONException {
//
//            // These are the names of the JSON objects that need to be extracted.
//            final String MOVIE_RESULTS = "results";
//
//            JSONObject moviesJson = new JSONObject(moviesJsonStr);
//            JSONArray movieJsonArray = moviesJson.getJSONArray(MOVIE_RESULTS);
//
//            return movieJsonArray.toString();
//            ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
//            for(int i = 0; i < movieJsonArray.length(); i++) {
//                Movie movie = new Movie();
//
//                JSONObject movieDetails = movieJsonArray.getJSONObject(i);
//                movie.setOriginalTitle(movieDetails.getString(MOVIE_ORIGINAL_TITLE));
//                movie.setPosterPath(MOVIE_POSTER_PATH_BASE_URL + MOVIE_POSTER_PATH_SIZE + movieDetails.getString(MOVIE_POSTER_PATH));
//                movie.setOverview(movieDetails.getString(MOVIE_OVERVIEW));
//                movie.setVoteAverage(movieDetails.getDouble(MOVIE_VOTE_AVERAGE));
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    movie.setReleaseDate(format.parse(movieDetails.getString(MOVIE_RELEASE_DATE)));
//                } catch (ParseException e) {
//                    Log.e(LOG_TAG ,e.getMessage(),e);
//                }
//                movieArrayList.add(movie);
//            }
//            return movieArrayList;
//        }
    @Override
    protected List<JsonHandler> doInBackground(String... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        final String MOVIE_RESULTS = "results";

        // Will contain the raw JSON response as a string.
        String jsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            URL url = new URL(params[0]);

            Log.v(LOG_TAG, "Build URI " + params[0]);

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
                jsonStr = null;
            }
            jsonStr = buffer.toString();

            Log.v(LOG_TAG, "Forecast JSON String: " + jsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            jsonStr = null;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            jsonStr = null;
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
            try {
                if (jsonStr != null) {
                    JSONObject moviesJson = new JSONObject(jsonStr);
                    JSONArray movieJsonArray = moviesJson.getJSONArray(MOVIE_RESULTS);
                    return this.jsonHandler.ParseJson(movieJsonArray.toString());
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }
}
