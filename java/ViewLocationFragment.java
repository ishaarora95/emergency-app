package com.example.isha.emergency;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Isha on 4/17/2016.
 */
public class ViewLocationFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,View.OnClickListener

{
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final String GOOGLE_API_KEY="AIzaSyBU5mFfFXORumj4QBPzhLNpZSv_AtJDnf4";
    View rootView;
    public static double currentLatitude;
    public static double currentLongitude;
    static Location location = null;
    private GoogleMap googleMap;
    ImageButton b1, b2;
Fragment fragment=null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_gps, container, false);
        //showSettingsAlert();
        Toast.makeText(rootView.getContext(), "Activated", Toast.LENGTH_LONG).show();
       b1 = (ImageButton) rootView.findViewById(R.id.shareLocation);
       b1.setOnClickListener(this);
        b2 = (ImageButton) rootView.findViewById(R.id.call);
       b2.setOnClickListener(this);
      /*  b3 = (Button) rootView.findViewById(R.id.atm);
        b3.setOnClickListener(this);
        b4 = (Button) rootView.findViewById(R.id.taxi);
        b4.setOnClickListener(this);*/
        mGoogleApiClient = new GoogleApiClient.Builder(rootView.getContext())
                // The next two lis tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();
        //   mGoogleApiClient.connect();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(rootView.getContext());

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 sec*/
        return rootView;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(rootView.getContext(), "in", Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            Toast.makeText(rootView.getContext(), "innn", Toast.LENGTH_LONG).show();
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            initializeMap(location);

            Toast.makeText(rootView.getContext(), currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }

    private void initializeMap(Location location) {
        //showSettingsAlert();Toast.makeText(rootView.getContext(), "Activated", Toast.LENGTH_LONG).show();
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        if (googleMap == null) {
            googleMap = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            MarkerOptions marker = new MarkerOptions().position(latLng).title("Your Location");

            googleMap.addMarker(marker);

            if (googleMap == null) {

                Toast.makeText(rootView.getContext(), "Sorry! unable to create maps", Toast.LENGTH_LONG).show();
            }
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14.0f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.moveCamera(cameraUpdate);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    //@Override
    public void onLocationChanged(Location location) {
        initializeMap(location);
        Toast.makeText(rootView.getContext(), currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

   /* public void on() {


        //Now lets connect to the API
        mGoogleApiClient.connect();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.call:
            /*   FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SelectContactsFragment llf = new SelectContactsFragment();
                ft.replace(R.id.selectContacts, llf);
                ft.commit();*/
                FragmentManager fragmentManager=getFragmentManager();
                android.app.FragmentTransaction ft=fragmentManager.beginTransaction();
                fragment= new SelectContactsFragment();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();

                break;
                case R.id.shareLocation:
                Intent i = new Intent(Intent.ACTION_SEND,Uri.parse("content://com.android.contacts/data/" ));
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "I'm in danger.My location is https://www.google.co.in/maps/@" + ViewLocationFragment.currentLatitude + "," + ViewLocationFragment.currentLongitude + "," + "14z?hl=en&hl=en");
                    startActivity(Intent.createChooser(i, "Share via"));

        }
   /*     String type = ((Button) v).getText().toString().toLowerCase();
    /*    StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + currentLatitude + "," + currentLongitude);
        googlePlacesUrl.append("&radius=" + 5000);
        googlePlacesUrl.append("&types=" + type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key="+GOOGLE_API_KEY);
 switch (v.getId()) {
            case R.id.yes:
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(googlePlacesUrl.toString()));
        startActivity(intent);

           /*     GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
                Object[] toPass = new Object[2];
                toPass[0] = googleMap;
                toPass[1] = googlePlacesUrl.toString();
                googlePlacesReadTask.execute(toPass);*/
      /*     Uri geoLocation=Uri.parse("geo:"+currentLatitude+", "+currentLongitude+"?").buildUpon().
                   appendQueryParameter("q",type).build();
   Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        startActivity(intent);*/
    }
  /* private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
        }*/


}