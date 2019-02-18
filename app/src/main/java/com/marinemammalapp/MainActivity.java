package com.marinemammalapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.gson.JsonObject;
import com.marinemammalapp.WebServices.APIClient;
import com.marinemammalapp.WebServices.APIInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    AppPreferences preferences;

    APIInterface apiService;

    private static final int CAMERA_REQUEST = 1888;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;

    Bitmap photo;


//    TextView textView_Latitude;
//    TextView textView_Longitude;
//    TextView textView_Date;

    ImageView imgView_Captured;
    Button    btnNext;
    RadioButton btnDead;
    RadioButton btnAlive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = APIClient.getClient().create(APIInterface.class);
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
        btnDead = (RadioButton) findViewById(R.id.dead_btn);
        btnAlive = (RadioButton) findViewById(R.id.alive_btn);






        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnDead.isChecked()){
                    preferences.setMammalAlive("dead");
                }
                else if(btnAlive.isChecked()){
                    preferences.setMammalAlive("alive");
                }

                try {
                    File file = new File(getCacheDir(), "MammalImage");
                    file.createNewFile();

                    //Convert bitmap to byte array


                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                    uploadFileToServer(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            } else {

                if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{Manifest.permission.CAMERA},
                            100);

                }

            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imgView_Captured.setImageBitmap(photo);
            saveToInternalStorage(photo);





        }
        else{
            Log.d("Camera","Camera exit without any image-Add 'no image' logo later");
        }
    }

    public void uploadFileToServer(File file){



        //MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        RequestBody filePart = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), filePart);

        Log.d("Network_call_","In method__ toSend file");


        // Call<SaveDataResponse> data = apiService.saveDetails(saveData,filePart,"");
        Call<ResponseBody> data = apiService.uploadFile(body);

        Log.d("Network_call","network call made");
        //Log.i("attr list",Arrays.asList(list).toString());

        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {




                    //  get the url and save it. the file gets saved into the heroku file system..


                    String data = response.body().string();

                    Log.d("Network_call","IN __ on_response--body"+data);


                    JSONObject jsonObject = new JSONObject(data);

                    String url = jsonObject.getString("url");

                    Log.d("Network_call","IN __ on_response--URL"+url);

                    preferences.setImageUrl(url);
                    Intent intent = new Intent(MainActivity.this, FinRuleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("err");
            }
        });


    }






    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("marinemammal", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"mammalPic.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }



}

