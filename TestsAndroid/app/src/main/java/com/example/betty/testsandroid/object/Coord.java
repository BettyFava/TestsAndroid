package com.example.betty.testsandroid.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Betty on 06/03/2016.
 */
public class Coord {

    @SerializedName("lon")
    private Double lon;
    @SerializedName("lat")
    private Double lat;

    /**
     *
     * @return
     * The lon
     */
    public Double getLon() {
        return lon;
    }

    /**
     *
     * @param lon
     * The lon
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    /**
     *
     * @return
     * The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }
}
