package com.marinemammalapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


        radioGroup_options = (RadioGroup)findViewById(R.id.fish_appearance_type);
        radioButton_noFin  = (RadioButton)findViewById(R.id.option_no_fin);
        radioButton_Fin = (RadioButton)findViewById(R.id.option_fin);
        radioButton_notSure   = (RadioButton)findViewById(R.id.option_not_sure);
        btnNext               = (Button)     findViewById(R.id.btn_next);
        tvMammal             = (TextView) findViewById(R.id.tv_mammal);





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
