package com.example.isha.emergency;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
Fragment fragment=null;
    DBAdapter db;

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    public static final int RESULT_OK = -1;
    private Uri uriContact;
    private String contactID;
    static String contactName = "";
    static String contactNumber = "";
    static ListView list;
    static ArrayList<Econtacts> econtacts = new ArrayList<>();
    CustomAdaptor adapter;
    //View rootView;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final String GOOGLE_API_KEY="AIzaSyBU5mFfFXORumj4QBPzhLNpZSv_AtJDnf4";
    public static double currentLatitude;
    public static double currentLongitude;
    static Location location = null;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //rootView = inflater.inflate(R.layout.fragment_select, container, false);
    /*    list = (ListView)findViewById(R.id.list);
        db = new DBAdapter(this);
        econtacts = db.getAllContacts();
        adapter = new CustomAdaptor(this, econtacts);
        list.setAdapter(adapter);*/
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        db = new DBAdapter(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //   mGoogleApiClient.connect();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

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
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 sec*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home__page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.selectContacts) {
          fragment=new SelectContactsFragment();            // Handle the camera action
        }

         else if (id == R.id.showPlaces) {
             fragment=new ViewPlaces();

        } else if (id == R.id.sendSOS) {
            fragment=new SelectContactsFragment();

        } else if (id == R.id.logout) {
            fragment = new LogoutFragment();

        }
        else if (id == R.id.shareLocation) {
            fragment = new ViewLocationFragment();
        }
        else if (id == R.id.emergencyCall) {
            fragment = new ViewLocationFragment();
        }
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle("");
        }
        FragmentManager fragmentManager=getFragmentManager();
        android.app.FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(Home_Page.this, "Inside", Toast.LENGTH_LONG).show();
        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            // Log.d(TAG, "Response: " + data.toString());
          uriContact = data.getData();

            retrieveContactName();
            Toast.makeText(Home_Page.this, "In Name", Toast.LENGTH_LONG).show();

           retrieveContactNumber();
            Toast.makeText(Home_Page.this, "In Add Contact", Toast.LENGTH_LONG).show();

            add();
            Toast.makeText(Home_Page.this, "Contacted Added", Toast.LENGTH_LONG).show();
            //  retrieveContactPhoto();

        }
    }

    private void retrieveContactNumber() {



        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToNext()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

       // Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToNext()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

        }

        cursorPhone.close();

      //  Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }
    private void retrieveContactName() {



        // querying contact data store
        Cursor cursor =getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToNext()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        }

        cursor.close();

        //  Log.d(TAG, "Contact Name: " + contactName);

    }

    private void add() {
        //Toast.makeText(this, "In Add", Toast.LENGTH_LONG).show();

        try {
            boolean r = db.contactcheck(contactName);
            if (!r) {

                Econtacts e = new Econtacts(contactNumber, contactName);
                SelectContactsFragment.econtacts.add(e);
                db.insertContact(e);

            } else {
                Toast.makeText(Home_Page.this, "Emergeny Contact already exists", Toast.LENGTH_LONG).show();
            }


            // Toast.makeText(this, "ok here", Toast.LENGTH_LONG).show();
            //     Intent i = new Intent(getContext(), ContactList.class);
            //   startActivity(i);
            //Toast.makeText(this, "done", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(Home_Page.this, "add: " +e.getMessage() , Toast.LENGTH_LONG).show();

        }
    }








}



