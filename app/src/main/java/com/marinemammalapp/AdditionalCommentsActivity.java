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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.marinemammalapp.WebServices.APIClient;
import com.marinemammalapp.WebServices.APIInterface;
import com.marinemammalapp.dataObjects.SaveDataResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdditionalCommentsActivity extends AppCompatActivity implements LocationListener {

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




    AppPreferences preferences;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_comments);

        apiService = APIClient.getClient().create(APIInterface.class);


        preferences = new AppPreferences(AdditionalCommentsActivity.this);



        initializeViews();

    }

    public void initializeViews(){




        editTextComments      = (EditText)   findViewById(R.id.edit_text_comments);
        btnNext               = (Button)     findViewById(R.id.btn_next);

        progressBar =  findViewById(R.id.progress_bar);

        calendar = Calendar.getInstance();


        dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        date = dateFormat.format(calendar.getTime());
        //textView_Date.setText(date);

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

        //MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));



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

        // Call<SaveDataResponse> data = apiService.saveDetails(saveData,filePart,"");
        Call<SaveDataResponse> data = apiService.saveDetails(saveData);

        Log.d("Network_call","network call made");
        //Log.i("attr list",Arrays.asList(list).toString());

        data.enqueue(new Callback<SaveDataResponse>() {
            @Override
            public void onResponse(Call<SaveDataResponse> call, Response<SaveDataResponse> response) {

                try {


                    //String data = response.body().toString();

                    if (response.raw().code() == 400) {
                        Log.d("Error code 400",response.errorBody().string());
                    }

                    String raw  = response.raw().toString();
//                    Intent intent = new Intent(AdditionalCommentsActivity.this, ResultActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();

                    Log.d("Network_call","IN __ on_response - ");

                    //if(response!=null) {

                    progressBar.setVisibility(View.GONE);

                    //Log.i("Update profile response", response.body().toString());

                    //if (Integer.parseInt(response.body().statusCode) == 300) {

                    Intent intent = new Intent(AdditionalCommentsActivity.this, ResultActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                    // } else {

                    //}
//                    }
//
//                    else{
//                        progressBar.setVisibility(View.GONE);
//
//                    }
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<SaveDataResponse> call, Throwable t) {

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

            //textView_Latitude.setText(""+roundedLatitude);
            //textView_Longitude.setText(""+roundedLongitude);



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


//    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);
//
//                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
//                String pathToImage =  getRealPathFromURI(tempUri);
//
//                String fileNameSegments[] = pathToImage.split("/");
//                String fileName = fileNameSegments[fileNameSegments.length - 1];
//                String fileFormateSegments[] = fileName.split(".");
//                String fileFormate = "";
//                if (fileFormateSegments.length > 1) {
//                fileFormate = "."
//                + fileNameSegments[fileFormateSegments.length - 1];
//                System.out.println("fileFormate = " + fileFormate);
//                }
//                String finalFileName = "file"+fileFormate;
//
//                Log.d("finalFileName",""+finalFileName);
//                try {
//                File file = new File(mContext.getCacheDir(), finalFileName);
//                file.createNewFile();
//
////Convert bitmap to byte array
//                profile_image.setDrawingCacheEnabled(false);
//
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                thumbnail.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
//                byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//                FileOutputStream fos = new FileOutputStream(file);
//                fos.write(bitmapdata);
//                fos.flush();
//                fos.close();
//                uploadFileToServer(file);
//                } catch (Exception e) {
//                e.printStackTrace();
//                }




