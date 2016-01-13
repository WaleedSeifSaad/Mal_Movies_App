package com.example.wseif.malmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by wseif on 1/2/2016.
 */
public class DetailsActivityFragment extends Fragment {

    TrailerReviewListAdapter trailerReviewListAdapter;

    public static DetailsActivityFragment newInstance(int index) {
        DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        detailsActivityFragment.setArguments(args);

        return detailsActivityFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Movie movie = new Movie();

//        if(container == null) {
//            return null;
//        }

        trailerReviewListAdapter = new TrailerReviewListAdapter(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_details_activity, container, false);

//        if (getActivity().getIntent() == null || getActivity().getIntent().getData() == null)
//            return null;
//        else {
        Bundle args = getArguments();
        if (args != null) {
            movie = (Movie) args.getSerializable("selectedMovie");
        } else {
            movie = (Movie) getActivity().getIntent().getSerializableExtra("selectedMovie");
        }
        final Movie movieToAdd = movie;

        trailerReviewListAdapter = new TrailerReviewListAdapter(getActivity());

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = layoutInflater.inflate(R.layout.activity_details_header, null);

        if (movie != null) {

            TextView textViewMovieOriginalTitle = (TextView) headerView.findViewById(R.id.textViewMovieOriginalTitle);
            textViewMovieOriginalTitle.setText(movie.getOriginalTitle());

            ImageView movieImage = (ImageView) headerView.findViewById(R.id.movieImage);
            Picasso.with(getActivity()).load(movie.getPosterPath()).into(movieImage);

            final ImageView imageViewFavoriteButton = (ImageView) headerView.findViewById(R.id.imageViewFavoriteButton);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
            Type type = new TypeToken<List<Movie>>() {
            }.getType();
            ArrayList<Movie> favoriteMoviesList = new Gson().fromJson(favoriteMoviesJson, type);
            if (favoriteMoviesList == null)
                favoriteMoviesList = new ArrayList<Movie>();

            if (movieExistsInFavorites(favoriteMoviesList, movie))
                imageViewFavoriteButton.setImageResource(R.drawable.favoritebutton);
            else
                imageViewFavoriteButton.setImageResource(R.drawable.unfavoritebutton);

            imageViewFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
                    Type type = new TypeToken<List<Movie>>() {
                    }.getType();
                    ArrayList<Movie> favoriteMoviesList = new Gson().fromJson(favoriteMoviesJson, type);
                    if (favoriteMoviesList == null)
                        favoriteMoviesList = new ArrayList<Movie>();
                    int count = favoriteMoviesList.size();
                    favoriteMoviesList = addMovieToList(favoriteMoviesList, movieToAdd);

                    if (favoriteMoviesList.size() > count)
                        imageViewFavoriteButton.setImageResource(R.drawable.favoritebutton);
                    else
                        imageViewFavoriteButton.setImageResource(R.drawable.unfavoritebutton);

                    //favoriteMoviesList.add(movieToAdd);
                    favoriteMoviesJson = new Gson().toJson(favoriteMoviesList);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(getString(R.string.pref_favorite_movies_key), favoriteMoviesJson);
                    editor.commit();
                    //favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
                }
            });

            TextView textViewMovieOverview = (TextView) headerView.findViewById(R.id.textViewMovieOverview);
            textViewMovieOverview.setText(movie.getOverview());

            TextView textViewMovieVoteAverage = (TextView) headerView.findViewById(R.id.textViewMovieVoteAverage);
            textViewMovieVoteAverage.setText(movie.getVoteAverage().toString());

            TextView textViewMovieReleaseDate = (TextView) headerView.findViewById(R.id.textViewMovieReleaseDate);
            textViewMovieReleaseDate.setText(movie.getReleaseDate().toString());

        }
        ListView ListviewTrailerReviews = (ListView) rootView.findViewById(R.id.listviewTrailerReviews);

        ListviewTrailerReviews.addHeaderView(headerView, null, false);

        ListviewTrailerReviews.setAdapter(trailerReviewListAdapter);

        ListviewTrailerReviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    TrailerReviewAdapterItem trailerReviewAdapterItem = trailerReviewListAdapter.getItem(position - 1);
                    if (trailerReviewAdapterItem.getType() == TrailerReviewEnum.TRAILER_ENUM) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + ((Trailer) trailerReviewAdapterItem).getKey())));
                    }

                }
            }
        });

        return rootView;
        //}
    }

    private ArrayList<Movie> addMovieToList(ArrayList<Movie> favorites, final Movie movieToAdd) {
        Context context = getActivity();
        CharSequence text = movieToAdd.getOriginalTitle() + " is added for your favorites successfully!";
        int duration = Toast.LENGTH_SHORT;

        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId() == movieToAdd.getId()) {
                favorites.remove(i);
                text = movieToAdd.getOriginalTitle() + "is removed from your favorites.";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return favorites;
            }
        }

        favorites.add(movieToAdd);
        text = movieToAdd.getOriginalTitle() + "is added to your favorites.";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return favorites;
    }

    private boolean movieExistsInFavorites(ArrayList<Movie> favorites, final Movie movieToAdd) {
        if (favorites.size() > 0) {
            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).getId() == movieToAdd.getId()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieDetails();
    }

    private void updateMovieDetails() {
        Movie movie;

        Bundle args = getArguments();
        if (args != null) {
            movie = (Movie) args.getSerializable("selectedMovie");
        } else {
            movie = (Movie) getActivity().getIntent().getSerializableExtra("selectedMovie");
        }

        if (movie != null) {
            FetchDataTask trailersTask = new FetchDataTask();
            trailersTask.setJsonHandler(new Trailer());
            trailersTask.execute(getTrailersUrl(movie.getId()));
            try {
                List<JsonHandler> trailers = trailersTask.get();
                trailerReviewListAdapter.clear();
                for (JsonHandler jsonHandler : trailers) {
                    trailerReviewListAdapter.add((Trailer) jsonHandler);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            FetchDataTask reviewsTask = new FetchDataTask();
            reviewsTask.setJsonHandler(new Review());
            reviewsTask.execute(getReviewsUrl(movie.getId()));
            try {
                List<JsonHandler> reviews = reviewsTask.get();
                for (JsonHandler jsonHandler : reviews) {
                    trailerReviewListAdapter.add((Review) jsonHandler);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTrailersUrl(int movieId) {
        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/videos";
        final String APPID_PARAM = "api_key";
        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";

        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(APPID_PARAM, APPID_Value)
                .build();

        return buildUri.toString();
    }

    private String getReviewsUrl(int movieId) {
        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews";
        final String APPID_PARAM = "api_key";
        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";

        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(APPID_PARAM, APPID_Value)
                .build();

        return buildUri.toString();
    }

    private void clearFavoriteMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.pref_favorite_movies_key), null);
        editor.commit();
    }
}
