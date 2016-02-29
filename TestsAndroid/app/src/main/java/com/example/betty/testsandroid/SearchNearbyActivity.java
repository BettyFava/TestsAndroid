package com.example.betty.testsandroid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.example.betty.testsandroid.object.City;
import com.example.betty.testsandroid.service.LocationService;
import com.example.betty.testsandroid.itf.CitiesSearch;
import com.example.betty.testsandroid.object.DataSearch;
import com.example.betty.testsandroid.tools.GeoAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchNearbyActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    // LogCat tag
    private static final String TAG = SearchNearbyActivity.class.getSimpleName();
    private CitiesSearch city = null;
    private Map<String, String> parameters = null;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private final Context context = this;
    private Integer number = 15;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    // UI elements
    private TextView lblLocation;
    private ImageButton btnSearchLocation;
    private ListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_nearby);

        lblLocation = (TextView) findViewById(R.id.info);
        btnSearchLocation = (ImageButton) findViewById(R.id.buttonSearchGeo);
        mListView = (ListView) findViewById(R.id.listView);
        city = LocationService.createService(CitiesSearch.class);


        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        btnSearchLocation.setOnClickListener(searchHandler);
        // Show location button click listener
    }

    View.OnClickListener searchHandler = new View.OnClickListener() {
        public void onClick(View v) {
           displayLocation();
        }
    };

    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {

            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            parameters = new LinkedHashMap<String, String>();
            parameters.put("lat",String.valueOf(latitude));
            parameters.put("lon", String.valueOf(longitude));
            parameters.put("cnt", String.valueOf(number));
            parameters.put("APPID", "d39378c6ea40f32a8556e88f0b8381bf");


            try {
                retrofit.Callback<JsonElement> c = new retrofit.Callback<JsonElement>() {

                    @Override
                    public void success(JsonElement s, Response response) {
                        Gson gson = new Gson();
                        DataSearch data = gson.fromJson(s, new TypeToken<DataSearch>() {
                        }.getType());
                        Log.d("SUCESS1", data.toString());
                        ArrayList<City> list = data.getCities();
                        List<String> cities = Stream.of(list).map(c->c.getName()).distinct().collect(Collectors.toList());
                        Log.d("test", cities.toString());
                        GeoAdapter adapter = new GeoAdapter(context, list ) ;
                        mListView.setAdapter(adapter);

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
                        lblLocation.setText(error.getMessage());
                    }
                };
                city.cities(parameters, c);
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        } else {

            lblLocation
                    .setText("Il est impossible de vous localiser.");
        }

    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
//        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
}

