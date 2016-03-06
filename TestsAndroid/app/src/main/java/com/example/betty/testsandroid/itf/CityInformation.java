package com.example.betty.testsandroid.itf;

import com.example.betty.testsandroid.object.City;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Betty on 06/03/2016.
 */
public interface CityInformation {

    @GET("/weather")
    void informations(@QueryMap Map<String, String> c,  retrofit.Callback<City> callback);
}
