package com.example.betty.testsandroid.object;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Betty on 15/02/2016.
 */
public class City {


    public String name;

    @SerializedName("id")
    public String id;


    @SerializedName("coord")
    public Coord coord;

  /*  public Main main;*/

    public City() {
    }

    public String getName() {
        return name;
    }
 /*   public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }*/

    public void setName(String name) {
        this.name = name;
    }

/*    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
/*                ", weather=" + weather +*/
                ", coord=" + coord +
                '}';
    }
}
