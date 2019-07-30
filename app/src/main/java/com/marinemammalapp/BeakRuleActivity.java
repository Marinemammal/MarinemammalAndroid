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

public class BeakRuleActivity extends AppCompatActivity {

    RadioGroup radioGroup_options;
    RadioButton radioButton_noBeak;
    RadioButton radioButton_Beak;
    RadioButton radioButton_notSure;

    Button btnNext;

    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beak_rule);


        preferences = new AppPreferences(BeakRuleActivity.this);
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
                    Intent i = new Intent(BeakRuleActivity.this, FinRuleActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        radioGroup_options = findViewById(R.id.fish_appearance_type);
        radioButton_noBeak  = findViewById(R.id.option_no_beak);
        radioButton_Beak = findViewById(R.id.option_beak);
        radioButton_notSure   = findViewById(R.id.option_not_sure);
        btnNext               =     findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_noBeak.isChecked()){
                    preferences.setMammalHasBeak("no");
                    preferences.setMammalColorGrey("");
                    preferences.setMammalColorPink("");
                    Log.d("On Img click","No beak is selected -- OK");
                    Intent intent = new Intent(BeakRuleActivity.this, AdditionalCommentsActivity.class );
                    intent.putExtra("KEY","BeakRuleActivity");
                    startActivity(intent);


                }
                else if(radioButton_Beak.isChecked()){
                    preferences.setMammalHasBeak("yes");
                    Log.d("On Img click","With beak- Next question--- NEXT");
                    Intent intent = new Intent(BeakRuleActivity.this, OnlyGreyRuleActivity.class );
                    startActivity(intent);

                }
                else {
                    preferences.setMammalHasBeak("maybe");
                    Log.d("On Img click","i dont know**");
                    Intent intent = new Intent(BeakRuleActivity.this, OnlyGreyRuleActivity.class );
                    startActivity(intent);
                }
            }
        });
    }
}
