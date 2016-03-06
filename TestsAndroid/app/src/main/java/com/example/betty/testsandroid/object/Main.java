package com.example.betty.testsandroid.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Betty on 06/03/2016.
 */
public class Main {

/*    @SerializedName("temp")*/
    public String temp ;
/*    @SerializedName("temp_min")*/
    public String temp_min ;
/*    @SerializedName("temp_max")*/
    public String temp_max;

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Main(String temp, String temp_min, String temp_max) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp='" + temp + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", temp_max='" + temp_max + '\'' +
                '}';
    }
}
