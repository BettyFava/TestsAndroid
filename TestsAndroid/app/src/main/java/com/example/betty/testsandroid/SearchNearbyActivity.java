package com.example.betty.testsandroid;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betty.testsandroid.Objects.City;
import com.example.betty.testsandroid.Service.GisgraphyService;
import com.example.betty.testsandroid.itf.CitiesSearch;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class SearchNearbyActivity extends AppCompatActivity implements LocationListener {
    private LocationManager lm;

    private double latitude;
    private double longitude;
    private double altitude;
    private Integer number = 15;
    private float accuracy;
    private final Context context = this;
    private ImageButton b1 = null;
    private TextView info = null;
    private CitiesSearch city = null;
    private Map<String, String> parameters = null;
    private static final int PERMISSION_REQUEST_CODE = 100;


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        accuracy = location.getAccuracy();

        String msg = String.format(
                getResources().getString(R.string.new_location), latitude,
                longitude, altitude, accuracy);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onProviderDisabled(String provider) {
        String msg = String.format(
                getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        String msg = String.format(
                getResources().getString(R.string.provider_enabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String newStatus = "";
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                newStatus = "OUT_OF_SERVICE";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                newStatus = "TEMPORARILY_UNAVAILABLE";
                break;
            case LocationProvider.AVAILABLE:
                newStatus = "AVAILABLE";
                break;
        }
        String msg = String.format(getResources().getString(R.string.provider_disabled), provider);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_nearby);
        info = (TextView) findViewById(R.id.info);
        b1 = (ImageButton) findViewById(R.id.buttonSearchGeo);
        city = GisgraphyService.createService(CitiesSearch.class);

        if (info == null || b1 == null) {
            throw new NullPointerException("Widget not found in view !");
        }

        b1.setOnClickListener(searchHandler);
    }

    View.OnClickListener searchHandler = new View.OnClickListener() {
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(SearchNearbyActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchNearbyActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            } else {
                onResume();

            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);

            }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        search(lm);
    }


    private void search(LocationManager lm) throws SecurityException {

        latitude = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        longitude = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
        parameters = new LinkedHashMap<String, String>();
        parameters.put("lat",String.valueOf(latitude));
        parameters.put("lon",String.valueOf(longitude));
        parameters.put("cnt", String.valueOf(number));
        parameters.put("APPID", "d39378c6ea40f32a8556e88f0b8381bf");


        try {
            retrofit.Callback<City> c = new retrofit.Callback<City>() {

                @Override
                public void success(City s, Response response) {
                    Log.d("SUCESS1", s.toString());
                    info.setText(s.toString());
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
                    info.setText(error.getMessage());
                }
            };
            city.cities(parameters, c);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}

