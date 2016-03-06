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
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.betty.testsandroid.itf.CitiesSearch;
import com.example.betty.testsandroid.itf.CityInformation;
import com.example.betty.testsandroid.object.City;
import com.example.betty.testsandroid.object.DataSearch;
import com.example.betty.testsandroid.service.LocationService;
import com.example.betty.testsandroid.tools.GeoAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class InformationActivity extends AppCompatActivity {

    private CityInformation city = null;
    private Map<String, String> parameters = null;
    private final Context context = this;
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    String idCity = bundle.getString("city");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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
