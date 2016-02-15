package com.example.betty.testsandroid.itf;

import com.example.betty.testsandroid.Objects.City;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Betty on 15/02/2016.
 */
public interface CitiesSearch {
    @GET("/find")
    void cities(@QueryMap Map<String, String> c, retrofit.Callback<City> callback);
}