package com.example.wseif.malmoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wseif on 12/25/2015.
 */
public class TrailerReviewListAdapter extends BaseAdapter {
    private final List<TrailerReviewAdapterItem> trailerReviewAdapterItems = new ArrayList<TrailerReviewAdapterItem>();
    private final Context mContext;

    public TrailerReviewListAdapter(Context context) {
        mContext = context;
    }

    public void add(TrailerReviewAdapterItem item) {

        trailerReviewAdapterItems.add(item);
        notifyDataSetChanged();

    }

    public void clear() {

        trailerReviewAdapterItems.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return trailerReviewAdapterItems.size();
    }

    @Override
    public TrailerReviewAdapterItem getItem(int position) {
        return trailerReviewAdapterItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        TrailerReviewAdapterItem trailerReviewAdapterItem = getItem(position);

        if (convertView == null ||
                ((ViewHolder) convertView.getTag()).trailerReviewEnum != trailerReviewAdapterItem.getType()) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (trailerReviewAdapterItem.getType() == TrailerReviewEnum.TRAILER_ENUM) {
                convertView = layoutInflater.inflate(R.layout.trailer_item, null);
                holder = new ViewHolder();
                holder.textViewTrailerName = (TextView) convertView.findViewById(R.id.textViewTrailerName);
            } else if (trailerReviewAdapterItem.getType() == TrailerReviewEnum.REVIEW_ENUM) {
                convertView = layoutInflater.inflate(R.layout.review_item, null);
                holder = new ViewHolder();
                holder.textViewReviewAuthorValue = (TextView) convertView.findViewById(R.id.textViewReviewAuthorValue);
                holder.textViewReviewContentValue = (TextView) convertView.findViewById(R.id.textViewReviewContentValue);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (trailerReviewAdapterItem.getType() == TrailerReviewEnum.TRAILER_ENUM) {
            TextView textViewTrailerName = holder.textViewTrailerName;
            textViewTrailerName.setText(((Trailer) trailerReviewAdapterItem).getName());
        } else if (trailerReviewAdapterItem.getType() == TrailerReviewEnum.REVIEW_ENUM) {
            TextView textViewReviewAuthorValue = holder.textViewReviewAuthorValue;
            textViewReviewAuthorValue.setText(((Review) trailerReviewAdapterItem).getAuthor());
            TextView textViewReviewContentValue = holder.textViewReviewContentValue;
            textViewReviewContentValue.setText(((Review) trailerReviewAdapterItem).getContent());
        }

        // Return the View you just created
        return convertView;
    }

    public static class ViewHolder {
        public TextView textViewReviewAuthorValue;
        public TextView textViewReviewContentValue;
        public TextView textViewTrailerName;
        public TrailerReviewEnum trailerReviewEnum;
    }
}
