package com.example.betty.testsandroid;

import android.app.Activity;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Activity {



    ArrayList<LatLng> points = new ArrayList<LatLng>();
    public final LatLng tutorialsPoint = new LatLng(50.6567115 , 3.0788290999999997);
    public final LatLng tutorialsPoint2 = new LatLng(50.66667175292969 , 3.083329916000366);

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            Marker TP = googleMap.addMarker(new MarkerOptions().
                    position(tutorialsPoint).title("TutorialsPoint"));
            googleMap.addMarker(new MarkerOptions().position(tutorialsPoint2).title("TEST"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}