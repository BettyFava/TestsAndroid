package com.example.betty.testsandroid.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BettyFlop on 19/02/2016.
 */
public class DataSearch {

    @SerializedName("list")
    private List<City> cities;


    public DataSearch(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "DataSearch{" +
                "cities=" + cities +
                '}';
    }
}
