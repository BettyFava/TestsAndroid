package com.example.betty.testsandroid.object;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Betty on 15/02/2016.
 */
public class City {

    @SerializedName("name")
    public String name;

    @SerializedName("id")
    public String id;

    public City(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}