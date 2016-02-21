package com.example.betty.testsandroid.object;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BettyFlop on 19/02/2016.
 */
public class DataSearch {

    @SerializedName("list")
    private ArrayList<City> cities;


    public DataSearch( ArrayList<City> cities) {
        this.cities = cities;
    }

    public  ArrayList<City> getCities() {
        return cities;
    }

    public void setCities( ArrayList<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "DataSearch{" +
                "cities=" + cities +
                '}';
    }
}
