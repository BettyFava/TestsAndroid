package com.example.betty.testsandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.betty.testsandroid.itf.CitiesSearch;
import com.example.betty.testsandroid.itf.CityInformation;
import com.example.betty.testsandroid.object.City;
import com.example.betty.testsandroid.object.DataSearch;
import com.example.betty.testsandroid.object.Weather;
import com.example.betty.testsandroid.service.LocationService;
import com.example.betty.testsandroid.tools.GeoAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class InformationActivity extends Main2Activity{

    private CityInformation city = null;
    private Map<String, String> parameters = null;
    private final Context context = this;
    private TextView lblCityName;
    private TextView lblWeather;
    private TextView lblWind;
    private TextView lblTemp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateView();
    }

    protected void onCreateView() {
        View menuView = this.getLayoutInflater().inflate(R.layout.activity_slide_menu, null);
        View contentView = this.getLayoutInflater().inflate(R.layout.activity_information, null);
        lblCityName = (TextView) contentView.findViewById(R.id.city_name);
        lblWeather = (TextView) contentView.findViewById(R.id.city_weather);
        lblWind = (TextView) contentView.findViewById(R.id.city_wind);
        lblTemp = (TextView) contentView.findViewById(R.id.city_temp);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String idCity = bundle.getString("idCity");
        parameters = new LinkedHashMap<String, String>();
        parameters.put("id", idCity);
        parameters.put("lang", "fr");
        parameters.put("units", "metric");
        parameters.put("APPID", "d39378c6ea40f32a8556e88f0b8381bf");

        city = LocationService.createService(CityInformation.class);

        try {
            retrofit.Callback<City> c = new retrofit.Callback<City>() {

                @Override
                public void success(City s, Response response) {
                    Log.d("TestInformations", s.toString());
                    lblCityName.setText(s.getName());
                    lblWeather.setText(s.getWeather().get(0).getDescription().toString().toUpperCase());
                    lblWind.setText(s.getWind().getSpeed());
                    lblTemp.setText(s.getMain().getTemp());


                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("DEBUG1", error.getUrl());
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle(error.getMessage())
                            .setMessage("Failed to " + error.getUrl())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert);
                    alert.show();

                }
            };
            city.informations(parameters, c);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

