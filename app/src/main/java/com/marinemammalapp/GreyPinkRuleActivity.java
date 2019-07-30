package com.marinemammalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GreyPinkRuleActivity extends AppCompatActivity {

    RadioGroup radioGroup_options;
    RadioButton radioButton_grey_pink;
    RadioButton radioButton_notSure;

    Button btnNext;

    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grey_pink_rule);

        preferences = new AppPreferences(GreyPinkRuleActivity.this);

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
                    Intent i = new Intent(GreyPinkRuleActivity.this, OnlyGreyRuleActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        radioGroup_options = findViewById(R.id.fish_appearance_type);
        radioButton_grey_pink  = findViewById(R.id.option_grey_pink);
        radioButton_notSure   = findViewById(R.id.option_not_sure);
        btnNext               =  findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_grey_pink.isChecked()){
                    preferences.setMammalColorPink("pink");
                    preferences.setMammalColorGrey("grey");
                    Log.d("On Img click","pink and grey -- OK");
                    Intent intent = new Intent(GreyPinkRuleActivity.this, AdditionalCommentsActivity.class );
                    intent.putExtra("KEY","GreyPinkRuleActivity");
                    startActivity(intent);


                }

                else {
                    preferences.setMammalColorPink("maybe");
                    Log.d("On Img click","i dont know**");
                    Intent intent = new Intent(GreyPinkRuleActivity.this, AdditionalCommentsActivity.class );
                    startActivity(intent);
                }
            }
        });
    }
}
