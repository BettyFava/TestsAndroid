package com.example.betty.testsandroid.tools;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betty.testsandroid.InformationActivity;
import com.example.betty.testsandroid.R;
import com.example.betty.testsandroid.SearchNearbyActivity;
import com.example.betty.testsandroid.object.City;

import java.util.ArrayList;

/**
 * Created by Betty on 29/02/2016.
 */
public class GeoAdapter extends ArrayAdapter<City> {
    public GeoAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the item data for this position
        City city = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);
        }
        // Lookup view for data population
        Button cityName = (Button) convertView.findViewById(R.id.cityName);
        // Populate the data into the template view using the data object
        cityName.setText(city.getName());
        // Return the completed view to render on screen

        cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", "string: " + city.getName());

                Toast.makeText(parent.getContext(), "view clicked: " + city.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parent.getContext(), InformationActivity.class);

                //On affecte à l'Intent le Bundle que l'on a créé
                intent.putExtra("city", city.toString());
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}

