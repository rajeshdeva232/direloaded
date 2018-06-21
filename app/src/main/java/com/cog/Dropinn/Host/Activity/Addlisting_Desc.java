package com.cog.Dropinn.Host.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

public class Addlisting_Desc extends AppCompatActivity {

    private TextView tvDesc;
    private AppCompatEditText edDesc;
    private FontUtility fontUtility;
    private RelativeLayout btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlisting_describe);
        fontUtility = new FontUtility(Addlisting_Desc.this);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        edDesc = (AppCompatEditText) findViewById(R.id.edDesc);
        btnComplete = (RelativeLayout) findViewById(R.id.btnComplete);

        doProcess();
    }

    private void doProcess() {
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addlisting_Desc.this, AddListing_Start.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvDesc.setTypeface(fontUtility.getLatoBold());
        edDesc.setTypeface(fontUtility.getLatoRegular());
    }
}
