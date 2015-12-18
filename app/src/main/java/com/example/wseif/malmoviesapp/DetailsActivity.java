package com.example.wseif.malmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Movie movie = (Movie)getIntent().getSerializableExtra("Movie");

        TextView textViewMovieOriginalTitle = (TextView)findViewById(R.id.textViewMovieOriginalTitle);
        textViewMovieOriginalTitle.setText(movie.getOriginalTitle());

        ImageView movieImage = (ImageView)findViewById(R.id.movieImage);
        Picasso.with(getApplicationContext()).load(movie.getPosterPath()).into(movieImage);

        TextView textViewMovieOverview = (TextView)findViewById(R.id.textViewMovieOverview);
        textViewMovieOverview.setText(movie.getOverview());

        TextView textViewMovieVoteAverage = (TextView)findViewById(R.id.textViewMovieVoteAverage);
        textViewMovieVoteAverage.setText(movie.getVoteAverage().toString());

        TextView textViewMovieReleaseDate = (TextView)findViewById(R.id.textViewMovieReleaseDate);
        textViewMovieReleaseDate.setText(movie.getReleaseDate().toString());

    }

}
