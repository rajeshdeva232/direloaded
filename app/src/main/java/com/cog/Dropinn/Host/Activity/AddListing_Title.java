package com.cog.Dropinn.Host.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

public class AddListing_Title extends AppCompatActivity {

    private String userChoosenTask;
    private AppCompatButton btnAddPhotos;
    private AppCompatEditText edTitle;
    private TextView tvTitle,tvButton;
    private FontUtility fontUtility;
    private RelativeLayout btnNext;

    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlisting_title);
        fontUtility = new FontUtility(AddListing_Title.this);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        edTitle = (AppCompatEditText) findViewById(R.id.edTitle);
        tvButton=(TextView)findViewById(R.id.text);

        btnNext = (RelativeLayout) findViewById(R.id.btnNext);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);

        doProcess();
    }

    private void doProcess() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddListing_Title.this, Addlisting_Desc.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        tvTitle.setTypeface(fontUtility.getLatoBold());
        tvButton.setTypeface(fontUtility.getLatoRegular());
    }
}