package com.marinemammalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


        radioGroup_options = (RadioGroup)findViewById(R.id.fish_appearance_type);
        radioButton_noBeak  = (RadioButton)findViewById(R.id.option_no_beak);
        radioButton_Beak = (RadioButton)findViewById(R.id.option_beak);
        radioButton_notSure   = (RadioButton)findViewById(R.id.option_not_sure);
        btnNext               = (Button)     findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_noBeak.isChecked()){
                    preferences.setMammalHasBeak("no");
                    preferences.setMammalColorGrey("");
                    preferences.setMammalColorPink("");
                    Log.d("On Img click","No beak is selected -- OK");
                    Intent intent = new Intent(BeakRuleActivity.this, AdditionalCommentsActivity.class );
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
