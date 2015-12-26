package com.example.wseif.malmoviesapp;

import org.json.JSONException;

import java.util.List;

/**
 * Created by wseif on 12/25/2015.
 */
public interface JsonHandler {
    List<JsonHandler> ParseJson(String jsonString) throws JSONException;
}
