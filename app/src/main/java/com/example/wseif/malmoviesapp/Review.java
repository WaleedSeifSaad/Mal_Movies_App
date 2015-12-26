package com.example.wseif.malmoviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wseif on 12/25/2015.
 */
public class Review implements Serializable, JsonHandler, TrailerReviewAdapterItem {
    private String id;
    private String author;
    private String content;
    private String url;
    private final String LOG_TAG = Review.class.getSimpleName();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getTitle() {
        return getAuthor();
    }

    @Override
    public String getDescription() {
        return getContent();
    }

    @Override
    public TrailerReviewEnum getType() {
        return TrailerReviewEnum.REVIEW_ENUM;
    }

    @Override
    public List<JsonHandler> ParseJson(String jsonString) throws JSONException {
        final String REVIEW_ID = "id";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";
        final String REVIEW_URL = "url";

        JSONArray ReviewJsonArray = new JSONArray(jsonString);
        ArrayList<JsonHandler> ReviewArrayList = new ArrayList<JsonHandler>();
        for (int i = 0; i < ReviewJsonArray.length(); i++) {
            Review Review = new Review();

            JSONObject ReviewDetails = ReviewJsonArray.getJSONObject(i);
            Review.setId(ReviewDetails.getString(REVIEW_ID));
            Review.setAuthor(ReviewDetails.getString(REVIEW_AUTHOR));
            Review.setContent(ReviewDetails.getString(REVIEW_CONTENT));
            Review.setUrl(ReviewDetails.getString(REVIEW_URL));
            ReviewArrayList.add(Review);
        }
        return ReviewArrayList;
    }
}
