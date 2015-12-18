package com.example.wseif.malmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wseif on 12/18/2015.
 */
public class MovieListAdapter extends BaseAdapter {
    private final List<Movie> movieItems = new ArrayList<Movie>();
    private final Context mContext;

    public MovieListAdapter(Context context){
        mContext=context;
    }

    public void add(Movie item) {

        movieItems.add(item);
        notifyDataSetChanged();

    }

    // Clears the list adapter of all items.

    public void clear() {

        movieItems.clear();
        notifyDataSetChanged();

    }

    // Returns the number of MovieItems

    @Override
    public int getCount() {

        return movieItems.size();

    }

    // Retrieve the number of MovieItems

    @Override
    public Object getItem(int pos) {

        return movieItems.get(pos);

    }

    // Get the ID for the MovieItem
    // In this case it's just the position

    @Override
    public long getItemId(int pos) {

        return pos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final Movie movieItem = (Movie)getItem(position);

        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.movie_item,null);

            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.movieImage);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        ImageView imageView = holder.imageView;
        Picasso.with(mContext).load(movieItem.getPosterPath()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Movie",movieItem);
                mContext.startActivity(intent);
            }
        });

        // Return the View you just created
        return convertView;
    }

    public static class ViewHolder {
        public ImageView imageView;
    }
}
