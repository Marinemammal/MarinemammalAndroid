package com.marinemammalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView tvSuccessMsg;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        initializeViews();

    }

    public void initializeViews(){


        tvSuccessMsg          = (TextView)     findViewById(R.id.tv_msg);
        btnNext               = (Button)     findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ResultActivity.this, MainActivity.class );
                startActivity(intent);



            }
        });
    }
}
