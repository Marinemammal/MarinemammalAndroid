package com.marinemammalapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationListener {

    AppPreferences preferences;

    private static final int CAMERA_REQUEST = 1888;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    LocationManager locationManager;
    private double latitude;
    private double longitude;
    double roundedLatitude;
    double roundedLongitude;
    public static final long MIN_TIME = 1000 * 10;
    public static final float MIN_DISTANCE = 10;

//    TextView textView_Latitude;
//    TextView textView_Longitude;
//    TextView textView_Date;

    ImageView imgView_Captured;
    Button    btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new AppPreferences(MainActivity.this);

        if(preferences.getUserName() == null ||
                preferences.getUserName().equalsIgnoreCase("")||
                preferences.getPhNum()== null||
                preferences.getPhNum().equalsIgnoreCase("")) {

            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else{

            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else {


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }

        setContentView(R.layout.activity_main);

        //textView_Latitude = (TextView) findViewById(R.id.tv_latitude);
        //textView_Longitude = (TextView) findViewById(R.id.tv_longitude);
        //textView_Date = (TextView) findViewById(R.id.tv_date);
        imgView_Captured = (ImageView) findViewById(R.id.image_captured);
        btnNext = (Button) findViewById(R.id.button_next);


        calendar = Calendar.getInstance();


        dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        date = dateFormat.format(calendar.getTime());
        //textView_Date.setText(date);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FinRuleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            Log.d("Location","in here");
            getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgView_Captured.setImageBitmap(photo);

        }
        else{
            Log.d("Camera","Camera exit without any image-Add 'no image' logo later");
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.i("location change", "changeeee");
        try {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            roundedLatitude = (int)Math.round(latitude * 10000)/(double)10000;
            roundedLongitude = (int)Math.round(longitude * 10000)/(double)10000;

            //textView_Latitude.setText(""+roundedLatitude);
            //textView_Longitude.setText(""+roundedLongitude);



            Log.d("latlng", "" + latitude + "," + longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void getLocation() {
        Log.d("Location","in here2");
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissionForLocation();
            Log.d("Location","in here3");
        } else {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);


                Location location;
                location = locationManager
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                roundedLatitude = (int)Math.round(latitude * 10000)/(double)10000;
                roundedLongitude = (int)Math.round(longitude * 10000)/(double)10000;

                //textView_Latitude.setText(""+roundedLatitude);
                //textView_Longitude.setText(""+roundedLongitude);



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void requestPermissionForLocation() {
        Log.d("Location","in here4");
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                200);
    }



}

/*
 1. Movve the data from activity to another.
->      if it is in beak activity and chooses first option then it can be
        determined that rest are no and the mammal has just beak.
 2. Write it into json format for webservice in additional comments activity.
 3. Push all the code to github and heroku.

 4. Store the data into the database.





 */
