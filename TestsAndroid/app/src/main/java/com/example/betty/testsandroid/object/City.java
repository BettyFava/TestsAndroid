package com.example.betty.testsandroid.object;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Betty on 15/02/2016.
 */
public class City {

    @SerializedName("name")
    public String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }
}