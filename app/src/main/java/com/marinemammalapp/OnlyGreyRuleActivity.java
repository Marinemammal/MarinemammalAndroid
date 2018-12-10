package com.marinemammalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OnlyGreyRuleActivity extends AppCompatActivity {

    RadioGroup radioGroup_options;
    RadioButton radioButton_grey;
    RadioButton radioButton_grey_pink;
    RadioButton radioButton_notSure;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_grey_rule);


        initializeViews();

    }

    public void initializeViews(){


        radioGroup_options = (RadioGroup)findViewById(R.id.fish_appearance_type);
        radioButton_grey  = (RadioButton)findViewById(R.id.option_grey);
        radioButton_grey_pink = (RadioButton)findViewById(R.id.option_grey_pink);
        radioButton_notSure   = (RadioButton)findViewById(R.id.option_not_sure);
        btnNext               = (Button)     findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_grey.isChecked()){
                    Log.d("On Img click","color grey is selected -- OK");
                    Intent intent = new Intent(OnlyGreyRuleActivity.this, AdditionalCommentsActivity.class );
                    startActivity(intent);


                }
                else if(radioButton_grey_pink.isChecked()){
                    Log.d("On Img click","other colors Next question--- NEXT");
                    Intent intent = new Intent(OnlyGreyRuleActivity.this, GreyPinkRuleActivity.class );
                    startActivity(intent);

                }
                else {
                    Log.d("On Img click","i dont know**");
                    Intent intent = new Intent(OnlyGreyRuleActivity.this, GreyPinkRuleActivity.class );
                    startActivity(intent);
                }
            }
        });
    }
}
