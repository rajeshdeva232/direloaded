package com.cog.Dropinn.Host.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.Activity.Listing_details;


public class AddListing_Located extends AppCompatActivity {
    private RelativeLayout btnComplete;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlist_located);

        btnComplete = (RelativeLayout) findViewById(R.id.btnComplete);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);
        doProcess();
    }

    private void doProcess() {
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddListing_Located.this,AddListing_Start.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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