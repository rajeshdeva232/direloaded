package com.cog.Dropinn.Host.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cog.Dropinn.R;

public class AddListing_Start extends AppCompatActivity {

    private AppCompatImageView ivBack;
    private RelativeLayout btnEditBasics;
    private AppCompatButton tvSet_the_scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew_listing_start);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);
        btnEditBasics = (RelativeLayout) findViewById(R.id.btnEditBasics);
        tvSet_the_scene = (AppCompatButton) findViewById(R.id.tvSet_the_scene);

        doProcess();
    }

    private void doProcess() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnEditBasics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddListing_Start.this, AddListing_Place.class);
                startActivity(intent);
            }
        });

        tvSet_the_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddListing_Start.this, AddListing_Photo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddListing_Start.this, Host_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("TO_SELECT", 2);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in, R.anim.anim_left_to_right);
        finish();
    }
}