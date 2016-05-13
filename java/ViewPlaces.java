package com.example.isha.emergency;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Isha on 5/7/2016.
 */
public class ViewPlaces extends Fragment implements View.OnClickListener{
        Button b1, b2, b3, b4;
View rootView;

public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_places, container, false);
        //showSettingsAlert();
        Toast.makeText(rootView.getContext(), "Activated", Toast.LENGTH_LONG).show();
        b1 = (Button) rootView.findViewById(R.id.hospitals);
        b1.setOnClickListener(this);

        b2 = (Button) rootView.findViewById(R.id.police);
        b2.setOnClickListener(this);
        b3 = (Button) rootView.findViewById(R.id.atm);
        b3.setOnClickListener(this);
        b4 = (Button) rootView.findViewById(R.id.taxi);
        b4.setOnClickListener(this);
    return rootView;
}

    @Override
    public void onClick(View v) {



            String type = ((Button) v).getText().toString().toLowerCase();
        Uri geoLocation=Uri.parse("geo:"+ViewLocationFragment.currentLatitude+", "+ViewLocationFragment.currentLongitude+"?").buildUpon().
                appendQueryParameter("q",type).build();
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        startActivity(intent);
    }
}