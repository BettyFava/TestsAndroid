package com.example.betty.testsandroid.itf;

import com.example.betty.testsandroid.object.City;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Betty on 15/02/2016.
 */
public interface CitiesSearch {
    @GET("/find")
    void cities(@QueryMap Map<String, String> c, Callback<JsonElement> callback);

    @GET("/weather")
    void informations(@QueryMap Map<String, String> c,  retrofit.Callback<City> callback);
}