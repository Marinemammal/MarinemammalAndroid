package com.marinemammalapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FinRuleActivity extends AppCompatActivity {

    RadioGroup radioGroup_options;
    RadioButton radioButton_noFin;
    RadioButton radioButton_Fin;
    RadioButton radioButton_notSure;
    TextView    tvMammal;

    Button btnNext;

    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_rule);

        preferences = new AppPreferences(FinRuleActivity.this);

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
                    Intent i = new Intent(FinRuleActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        radioGroup_options = findViewById(R.id.fish_appearance_type);
        radioButton_noFin  = findViewById(R.id.option_no_fin);
        radioButton_Fin = findViewById(R.id.option_fin);
        radioButton_notSure   = findViewById(R.id.option_not_sure);
        btnNext               = findViewById(R.id.btn_next);
        tvMammal             =  findViewById(R.id.tv_mammal);





        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_noFin.isChecked()){
                    preferences.setMammalHasFin("no");
                    preferences.setMammalHasBeak("");
                    preferences.setMammalColorGrey("");
                    preferences.setMammalColorPink("");
                    Log.d("On Img click","No fin is selected -- OK");
                    Intent intent = new Intent(FinRuleActivity.this, AdditionalCommentsActivity.class );
                    intent.putExtra("KEY","FinRuleActivity");
                    startActivity(intent);


                }
                else if(radioButton_Fin.isChecked()){
                    preferences.setMammalHasFin("yes");
                    Log.d("On Img click","With fin- Next question--- NEXT");
                    Intent intent = new Intent(FinRuleActivity.this, BeakRuleActivity.class );
                    startActivity(intent);

                }
                else {
                    preferences.setMammalHasFin("maybe");
                    Log.d("On Img click","i dont know**");
                    Intent intent = new Intent(FinRuleActivity.this, BeakRuleActivity.class );
                    startActivity(intent);
                }
            }
        });
    }
}
