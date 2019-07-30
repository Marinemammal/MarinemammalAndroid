package com.marinemammalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.marinemammalapp.WebServices.APIClient;
import com.marinemammalapp.WebServices.APIInterface;
import com.marinemammalapp.dataObjects.SaveDataResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdditionalCommentsActivity extends AppCompatActivity implements LocationListener {

    TextView tvMammalDescMalay;
    TextView tvMammalDescEng;
    TextView tvSuccessMsg;
    EditText editTextComments;
    Button btnNext;

    APIInterface apiService;

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

    String backClickRedirect ="";


    AppPreferences preferences;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_comments);

        apiService = APIClient.getClient().create(APIInterface.class);


        preferences = new AppPreferences(AdditionalCommentsActivity.this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            backClickRedirect = extras.getString("KEY");
        }

        initializeViews();

    }

    public void initializeViews(){

        //Defining toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ImageView imageView_back =  findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(backClickRedirect.equalsIgnoreCase("FinRuleActivity")){
                        Intent i = new Intent(AdditionalCommentsActivity.this, FinRuleActivity.class);
                        startActivity(i);
                        finish();
                    }else if(backClickRedirect.equalsIgnoreCase("BeakRuleActivity")){
                        Intent i = new Intent(AdditionalCommentsActivity.this, BeakRuleActivity.class);
                        startActivity(i);
                        finish();
                    }else if(backClickRedirect.equalsIgnoreCase("OnlyGreyRuleActivity")){
                        Intent i = new Intent(AdditionalCommentsActivity.this, OnlyGreyRuleActivity.class);
                        startActivity(i);
                        finish();
                    }else if(backClickRedirect.equalsIgnoreCase("GreyPinkRuleActivity")){
                        Intent i = new Intent(AdditionalCommentsActivity.this, GreyPinkRuleActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Intent i = new Intent(AdditionalCommentsActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        tvMammalDescMalay          =   findViewById(R.id.mammal_description_malay);
        tvMammalDescEng          =   findViewById(R.id.mammal_description_eng);
        editTextComments      =    findViewById(R.id.edit_text_comments);
        btnNext               =     findViewById(R.id.btn_next);

        progressBar =  findViewById(R.id.progress_bar);

        calendar = Calendar.getInstance();


        dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        date = dateFormat.format(calendar.getTime());



        if(backClickRedirect.equalsIgnoreCase("FinRuleActivity")){
            tvMammalDescMalay.setText(getResources().getString(R.string.str_fin_malay));
             tvMammalDescEng.setText(getResources().getString(R.string.str_fin_eng));
        }else if(backClickRedirect.equalsIgnoreCase("BeakRuleActivity")){
            tvMammalDescMalay.setText(getResources().getString(R.string.str_beak_malay));
            tvMammalDescEng.setText(getResources().getString(R.string.str_beak_eng));
        }else if(backClickRedirect.equalsIgnoreCase("OnlyGreyRuleActivity")){
            tvMammalDescMalay.setText(getResources().getString(R.string.str_grey_malay));
            tvMammalDescEng.setText(getResources().getString(R.string.str_grey_eng));
        }else if(backClickRedirect.equalsIgnoreCase("GreyPinkRuleActivity")){
            tvMammalDescMalay.setText(getResources().getString(R.string.str_pink_grey_malay));
            tvMammalDescEng.setText(getResources().getString(R.string.str_pink_grey_eng));
        }else{
            tvMammalDescMalay.setText(getResources().getString(R.string.str_fin_malay));
            tvMammalDescEng.setText(getResources().getString(R.string.str_fin_eng));
        }



        try {
            Log.d("Location","in here");
            getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressBar.setVisibility(View.VISIBLE);
                Log.d("Network_call","Button clicked");
                sendDataToServer();

            }
        });
    }




    public void sendDataToServer(){

        HashMap<String, Object> saveData = new HashMap<>();

        Log.d("Network_call","In method");


        saveData.put("username",preferences.getUserName().toString());
        saveData.put("phone_number",preferences.getPhNum().toString());
        saveData.put("created_date", date);
        saveData.put("latitude", Double.toString(roundedLatitude));
        saveData.put("longitude",Double.toString(roundedLongitude));
        saveData.put("alive_status",preferences.getMammalAlive());
        saveData.put("fin",preferences.getMammalHasFin());
        saveData.put("beak",preferences.getMammalHasBeak());
        saveData.put("color1",preferences.getMammalColorGrey().toString());
        saveData.put("color2",preferences.getMammalColorPink());
        saveData.put("comments",editTextComments.getText().toString());
        saveData.put("imageurl",preferences.getImageUrl());

        Call<SaveDataResponse> data = apiService.saveDetails(saveData);

        Log.d("Network_call","network call made");
        //Log.i("attr list",Arrays.asList(list).toString());

        data.enqueue(new Callback<SaveDataResponse>() {
            @Override
            public void onResponse(Call<SaveDataResponse> call, Response<SaveDataResponse> response) {

                try {

                    if (response.raw().code() == 400) {
                        Log.d("Error code 400",response.errorBody().string());
                    }

                    Log.d("Network_call","IN __ on_response - ");

                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(AdditionalCommentsActivity.this, ResultActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AdditionalCommentsActivity.this, "Please check your internet connection and try again.",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<SaveDataResponse> call, Throwable t) {

                Toast.makeText(AdditionalCommentsActivity.this, "Please check your internet connection and try again.",
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("location change", "changeeee");
        try {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            roundedLatitude = (int)Math.round(latitude * 10000)/(double)10000;
            roundedLongitude = (int)Math.round(longitude * 10000)/(double)10000;

            Log.d("latlng", "" + latitude + "," + longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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