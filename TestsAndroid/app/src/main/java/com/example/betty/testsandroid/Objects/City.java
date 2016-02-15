package com.example.betty.testsandroid.Objects;

import java.util.ArrayList;

/**
 * Created by Betty on 15/02/2016.
 */
public class City {


    public String name;
    public String cedex;
    public Integer distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getCedex() {
        return cedex;
    }

    public void setCedex(String cedex) {
        this.cedex = cedex;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", cedex='" + cedex + '\'' +
                ", distance=" + distance +
                '}';
    }
}