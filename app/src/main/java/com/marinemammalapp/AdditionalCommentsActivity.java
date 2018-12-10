package com.marinemammalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdditionalCommentsActivity extends AppCompatActivity {

    TextView tvSuccessMsg;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_comments);


        initializeViews();

    }

    public void initializeViews(){



        btnNext               = (Button)     findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(AdditionalCommentsActivity.this, ResultActivity.class );
                startActivity(intent);



            }
        });
    }
}
