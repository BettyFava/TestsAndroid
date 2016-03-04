package com.example.betty.testsandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.example.betty.testsandroid.R.id.map;

public class MapsFragment extends Fragment {
    ArrayList<LatLng> points = new ArrayList<LatLng>();
    public final LatLng tutorialsPoint = new LatLng(50.6567115 , 3.0788290999999997);
    public final LatLng tutorialsPoint2 = new LatLng(50.66667175292969 , 3.083329916000366);

    private GoogleMap googleMap;

    private MapView mapView;


    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View v = inflater.inflate(R.layout.fragment_test, container, false);
        try {
            if (googleMap == null) {
                mapView = (MapView) v.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                googleMap = mapView.getMap();
            }
            MapsInitializer.initialize(this.getActivity());
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            Marker TP = googleMap.addMarker(new MarkerOptions().
                    position(tutorialsPoint).title("TutorialsPoint"));
            googleMap.addMarker(new MarkerOptions().position(tutorialsPoint2).title("TEST"));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(tutorialsPoint, 10);
            googleMap.animateCamera(cameraUpdate);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}