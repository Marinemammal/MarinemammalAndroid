package com.marinemammalapp;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ImageDescriptionActivity extends AppCompatActivity {

    RadioGroup radioGroup_options;
    RadioButton radioButton_noFin;
    RadioButton radioButton_noBeak;
    RadioButton radioButton_grey;
    RadioButton radioButton_pink_grey;
    RadioButton radioButton_notSure;

    Button    btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_description);


        initializeViews();

    }

    public void initializeViews(){


        radioGroup_options = (RadioGroup)findViewById(R.id.fish_appearance_type);
        radioButton_noFin  = (RadioButton)findViewById(R.id.option_no_fin);
        radioButton_noBeak = (RadioButton)findViewById(R.id.option_no_beak);
        radioButton_grey   = (RadioButton)findViewById(R.id.option_grey);
        radioButton_pink_grey = (RadioButton)findViewById(R.id.option_pink_grey);
        radioButton_notSure   = (RadioButton)findViewById(R.id.option_not_sure);
        btnNext               = (Button)     findViewById(R.id.btn_next);

//
        radioButton_noFin.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.no_fin,0);
        radioButton_noBeak.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.no_beak,0);
        radioButton_grey.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.color_grey,0);
        radioButton_pink_grey.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.color_pink_grey,0);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioButton_noFin.isChecked()){
                    Log.d("On Img click","Image 1 selected -- OK");
                }
                else if(radioButton_noBeak.isChecked()){
                    Log.d("On Img click","Image 2 selected--- NEXT");

                }
                else {
                    Log.d("On Img click","i dont know**");
                }
            }
        });
    }
}
