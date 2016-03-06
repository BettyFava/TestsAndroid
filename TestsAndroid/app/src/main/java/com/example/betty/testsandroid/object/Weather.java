package com.example.betty.testsandroid.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Betty on 06/03/2016.
 */
public class Weather {

    @SerializedName("main")
    public String main;

    @SerializedName("description")
    public String description;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
