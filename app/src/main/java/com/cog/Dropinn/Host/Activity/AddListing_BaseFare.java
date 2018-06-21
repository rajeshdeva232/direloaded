package com.cog.Dropinn.Host.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cog.Dropinn.R;

public class AddListing_BaseFare extends AppCompatActivity {

    private RelativeLayout btnNext;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlisting_basefare);
        btnNext = (RelativeLayout) findViewById(R.id.btnNext);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);

        doProcess();
    }

    private void doProcess() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddListing_BaseFare.this, AddListing_Located.class);
                startActivity(i);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
