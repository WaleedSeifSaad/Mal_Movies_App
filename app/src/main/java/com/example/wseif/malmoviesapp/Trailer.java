package com.example.wseif.malmoviesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wseif on 12/26/2015.
 */
public class Trailer implements Serializable, JsonHandler, TrailerReviewAdapterItem {

    private String id;
    private String iso_639_1;
    private String key;
    private String name;
    private String site;
    private Double size;
    private TrailerReviewEnum type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public void setType(TrailerReviewEnum type) {
        this.type = type;
    }

    @Override
    public String getTitle() {
        return getName();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public TrailerReviewEnum getType() {
        return TrailerReviewEnum.TRAILER_ENUM;
    }

    @Override
    public List<JsonHandler> ParseJson(String jsonString) throws JSONException {
        final String TRAILER_ID = "id";
        final String TRAILER_ISO_639_1 = "iso_639_1";
        final String TRAILER_KEY = "key";
        final String TRAILER_NAME = "name";
        final String TRAILER_SITE = "site";
        final String TRAILER_SIZE = "size";
        final String TRAILER_TYPE = "tpye";

        JSONArray TrailerJsonArray = new JSONArray(jsonString);
        ArrayList<JsonHandler> TrailerArrayList = new ArrayList<JsonHandler>();
        for (int i = 0; i < TrailerJsonArray.length(); i++) {
            Trailer trailer = new Trailer();

            JSONObject TrailerDetails = TrailerJsonArray.getJSONObject(i);
            trailer.setId(TrailerDetails.getString(TRAILER_ID));
            trailer.setIso_639_1(TrailerDetails.getString(TRAILER_ISO_639_1));
            trailer.setKey(TrailerDetails.getString(TRAILER_KEY));
            trailer.setName(TrailerDetails.getString(TRAILER_NAME));
            trailer.setSite(TrailerDetails.getString(TRAILER_SITE));
            trailer.setSite(TrailerDetails.getString(TRAILER_SIZE));
            trailer.setType(TrailerReviewEnum.TRAILER_ENUM);
            TrailerArrayList.add(trailer);
        }
        return TrailerArrayList;
    }
}
