package com.marinemammalapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {


    EditText editText_name;
    EditText editText_phNum;
    CheckBox checkBox_terms;
    Button button_next;

    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        preferences = new AppPreferences(RegistrationActivity.this);

        initializeViews();
    }

    public void initializeViews() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView txtTitle =findViewById(R.id.tv_toolbartitle);
        txtTitle.setText(getResources().getString(R.string.title));

        final ImageView imageView_back =  findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    finishAffinity();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        editText_name  =  findViewById(R.id.et_username);
        editText_phNum =  findViewById(R.id.et_phnum);
        checkBox_terms =  findViewById(R.id.checkbox_terms);
        button_next    =  findViewById(R.id.button_next);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validateFields();


            }
        });



    }

    private void validateFields() {
        //Utils.closeKeyBoard(RegistrationActivity.this, button_next);

        if(editText_name.getText().toString() == null ||
                editText_name.getText().toString().equalsIgnoreCase("")){
            editText_name.requestFocus();
            editText_name.setError(getResources().getString(R.string.valid_name));
        }
        else if(editText_phNum.getText().toString() == null ||
                editText_phNum.getText().toString().equalsIgnoreCase("") ||
                editText_phNum.getText().toString().length() < 10){

            editText_phNum.requestFocus();
            editText_phNum.setError(getResources().getString(R.string.valid_phNum));
        }
        else if(checkBox_terms.isChecked() == false){
            checkBox_terms.setError(getResources().getString(R.string.valid_checkbox));
        }
        else {

            preferences.setUserName(editText_name.getText().toString());
            preferences.setPhNum(editText_phNum.getText().toString());

            Intent intent = new Intent(RegistrationActivity.this, InstructionsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }



    @Override
    public void onBackPressed() {
        // do nothing.
    }


    /*
    Username  -- preferences
    mobile number  -- preferences
    image --
    latitude, longitude
    date
    dead - yes/no
    beak - yes/no
    fin - yes/no
    grey - yes/no
    pink - yes/no
     */




}
