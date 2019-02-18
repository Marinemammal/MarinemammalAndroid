package com.marinemammalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


        radioGroup_options = (RadioGroup)findViewById(R.id.fish_appearance_type);
        radioButton_grey_pink  = (RadioButton)findViewById(R.id.option_grey_pink);
        radioButton_notSure   = (RadioButton)findViewById(R.id.option_not_sure);
        btnNext               = (Button)     findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_grey_pink.isChecked()){
                    preferences.setMammalColorPink("pink");
                    preferences.setMammalColorGrey("grey");
                    Log.d("On Img click","pink and grey -- OK");
                    Intent intent = new Intent(GreyPinkRuleActivity.this, AdditionalCommentsActivity.class );
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
