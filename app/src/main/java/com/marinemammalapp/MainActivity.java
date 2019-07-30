package com.marinemammalapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

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

    ImageView imgView_Captured;
    Button    btnNext;
    RadioButton btnDead;
    RadioButton btnAlive;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = APIClient.getClient().create(APIInterface.class);
        preferences = new AppPreferences(MainActivity.this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = String.valueOf(pInfo.getLongVersionCode());

            if (!preferences.getVersionCode().equalsIgnoreCase(version)) {

                preferences.clearPreferences();

                preferences.setVersionCode(version);
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
            else{
                if(preferences.getUserName() == null ||
                        preferences.getUserName().equalsIgnoreCase("")||
                        preferences.getPhNum()== null||
                        preferences.getPhNum().equalsIgnoreCase("")) {

                    System.out.println("failing here");

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
            }
        }catch(Throwable t) {
            // update failed, or cancelled
        }



        setContentView(R.layout.activity_main);

        //Defining toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ImageView imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        imgView_Captured =  findViewById(R.id.image_captured);
        btnNext =  findViewById(R.id.button_next);
        btnDead =  findViewById(R.id.dead_btn);
        btnAlive =  findViewById(R.id.alive_btn);

        progressBar =  findViewById(R.id.progress_bar);


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
                    if(photo!=null) {
                        photo.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        //write the bytes in file
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();

                        progressBar.setVisibility(View.VISIBLE);

                        uploadFileToServer(file);
                    }
                    else{
                        showAlertDialog("Image Required", "Please take a picture to continue",false,"OK");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("testing","In here1");

        boolean allPermissionsGranted = true;
        if(grantResults.length>0){
            for(int grantResult: grantResults){
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    Log.d("testing","In here2");
                    allPermissionsGranted = false;
                    break;
                }
            }
        }


        if(!allPermissionsGranted){
            boolean somePermissionsForeverDenied = false;
            for(String permission: permissions){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                    //denied
                    Log.e("denied", permission);
                    if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(
                                this,
                                new String[]{Manifest.permission.CAMERA},
                                100);
                        Log.d("testing","Rejecting permission");
                        Log.d("testing","In here3");
                    }
                }else{
                    if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
                        //allowed
                        Log.e("allowed", permission);
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        Log.d("testing","In here4");
                    } else{
                        //set to never ask again
                        Log.e("set to never ask again", permission);
                        somePermissionsForeverDenied = true;
                        Log.d("testing","In here5");
                    }
                }
            }
            if(somePermissionsForeverDenied) {
                showAlertDialog("Permissions Required", "You have forcefully denied some of the required permissions " +
                        "for this action. Please open settings, go to permissions and allow them.", true,"Settings");
            }
        } else {
            Log.d("testing","In here6 - permission accepted first time");
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }

//        if (requestCode == 100) {
//
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//
//            } else {
//
//                if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
//                    ActivityCompat.requestPermissions(
//                            this,
//                            new String[]{Manifest.permission.CAMERA},
//                            100);
//                    Log.d("testing","Rejecting permission");
//                }
//                else{
//                    Log.d("testing","Showing alert dialog");
//                    showAlertDialog("Permissions Required","You have forcefully denied some of the required permissions \" +\n" +
//                            "                        \"for this action. Please open settings, go to permissions and allow them.");
//                }
//
//            }
//        }

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

                    progressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(MainActivity.this, FinRuleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Please check your internet connection and try again.",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("err");
                Toast.makeText(MainActivity.this, "Please check your internet connection and try again.",
                        Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
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



    public class CustomTypefaceSpan extends TypefaceSpan {
        private final Typeface newType;

        public CustomTypefaceSpan(String family, Typeface type) {
            super(family);
            newType = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyCustomTypeFace(ds, newType);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyCustomTypeFace(paint, newType);
        }

        private void applyCustomTypeFace(Paint paint, Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }

    public void showAlertDialog(String title, String message, final boolean btnType, String btnName) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(btnName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(btnType) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            dialog.dismiss();
                        }
                    }
                })

                .setCancelable(false)
                .create()
                .show();
    }
}

