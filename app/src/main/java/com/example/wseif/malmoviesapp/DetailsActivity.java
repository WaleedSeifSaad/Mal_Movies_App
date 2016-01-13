package com.example.wseif.malmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    TrailerReviewListAdapter trailerReviewListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//            Bundle args = new Bundle();
//        args.putSerializable("selectedMovie", getIntent().getSerializableExtra("selectedMovie"));
//
//            DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();
//        detailsActivityFragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.fragment_details_activity,detailsActivityFragment)
//                    .commit();

//        final Movie movie = (Movie) getIntent().getSerializableExtra("Movie");
//        trailerReviewListAdapter = new TrailerReviewListAdapter(getApplicationContext());
//
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View headerView = layoutInflater.inflate(R.layout.activity_details_header, null);
//
//
//        TextView textViewMovieOriginalTitle = (TextView) headerView.findViewById(R.id.textViewMovieOriginalTitle);
//        textViewMovieOriginalTitle.setText(movie.getOriginalTitle());
//
//        ImageView movieImage = (ImageView) headerView.findViewById(R.id.movieImage);
//        Picasso.with(getApplicationContext()).load(movie.getPosterPath()).into(movieImage);
//
//        ImageView imageViewFavoriteButton = (ImageView) headerView.findViewById(R.id.imageViewFavoriteButton);
//        imageViewFavoriteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                String favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
//                Type type = new TypeToken<List<Movie>>() {
//                }.getType();
//                ArrayList<Movie> favoriteMoviesList = new Gson().fromJson(favoriteMoviesJson, type);
//                if (favoriteMoviesList == null)
//                    favoriteMoviesList = new ArrayList<Movie>();
//                favoriteMoviesList.add(movie);
//                favoriteMoviesJson = new Gson().toJson(favoriteMoviesList);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putString(getString(R.string.pref_favorite_movies_key), favoriteMoviesJson);
//                editor.commit();
//                favoriteMoviesJson = prefs.getString(getString(R.string.pref_favorite_movies_key), null);
//
//                Context context = getApplicationContext();
//                CharSequence text = movie.getOriginalTitle() + " is added for your favorites successfully!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//            }
//        });
//
//        TextView textViewMovieOverview = (TextView) headerView.findViewById(R.id.textViewMovieOverview);
//        textViewMovieOverview.setText(movie.getOverview());
//
//        TextView textViewMovieVoteAverage = (TextView) headerView.findViewById(R.id.textViewMovieVoteAverage);
//        textViewMovieVoteAverage.setText(movie.getVoteAverage().toString());
//
//        TextView textViewMovieReleaseDate = (TextView) headerView.findViewById(R.id.textViewMovieReleaseDate);
//        textViewMovieReleaseDate.setText(movie.getReleaseDate().toString());
//
//        ListView ListviewTrailerReviews = (ListView) findViewById(R.id.listviewTrailerReviews);
//        ListviewTrailerReviews.addHeaderView(headerView);
//        ListviewTrailerReviews.setAdapter(trailerReviewListAdapter);
//
//        ListviewTrailerReviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TrailerReviewAdapterItem trailerReviewAdapterItem = trailerReviewListAdapter.getItem(position - 1);
//                if (trailerReviewAdapterItem.getType() == TrailerReviewEnum.TRAILER_ENUM) {
//                    clearFavoriteMovies();
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + ((Trailer) trailerReviewAdapterItem).getKey())));
//                }
//            }
//        });
//
//        FetchDataTask trailersTask = new FetchDataTask();
//        trailersTask.setJsonHandler(new Trailer());
//        trailersTask.execute(getTrailersUrl(movie.getId()));
//        try {
//            List<JsonHandler> trailers = trailersTask.get();
//            trailerReviewListAdapter.clear();
//            for (JsonHandler jsonHandler : trailers) {
//                trailerReviewListAdapter.add((Trailer) jsonHandler);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        FetchDataTask reviewsTask = new FetchDataTask();
//        reviewsTask.setJsonHandler(new Review());
//        reviewsTask.execute(getReviewsUrl(movie.getId()));
//        try {
//            List<JsonHandler> reviews = reviewsTask.get();
//            for (JsonHandler jsonHandler : reviews) {
//                trailerReviewListAdapter.add((Review) jsonHandler);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

//    private String getTrailersUrl(int movieId) {
//        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/videos";
//        final String APPID_PARAM = "api_key";
//        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";
//
//        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
//                .appendQueryParameter(APPID_PARAM, APPID_Value)
//                .build();
//
//        return buildUri.toString();
//    }
//
//    private String getReviewsUrl(int movieId) {
//        final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews";
//        final String APPID_PARAM = "api_key";
//        final String APPID_Value = "6e0ea97da6dc8c46962af05d7f806598";
//
//        Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
//                .appendQueryParameter(APPID_PARAM, APPID_Value)
//                .build();
//
//        return buildUri.toString();
//    }
//
//    private void clearFavoriteMovies() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(getString(R.string.pref_favorite_movies_key), null);
//        editor.commit();
//    }
}
