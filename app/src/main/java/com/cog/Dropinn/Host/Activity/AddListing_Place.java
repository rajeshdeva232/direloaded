package com.cog.Dropinn.Host.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cog.Dropinn.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddListing_Place extends AppCompatActivity {

    private MaterialSpinner typespinner;
    private MaterialSpinner propertyspinner;
    ImageView backbtn;
    RelativeLayout nextbtn;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlisting_place);

        typespinner = (MaterialSpinner) findViewById(R.id.type_spinner);
        propertyspinner = (MaterialSpinner) findViewById(R.id.property_spinner);
        nextbtn = (RelativeLayout) findViewById(R.id.next_btn);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);

        doProcess();
    }

    private void doProcess() {
        typespinner.setItems("Entire place", "Private room", "Shared room");
        typespinner.setPadding(10, 8, typespinner.getPaddingRight(), 12);
        typespinner.setSelectedIndex(0);
        typespinner.setSelected(true);

        propertyspinner.setItems("Flat", "House", "Bed & Breakfast", "Loft", "Villa");
        propertyspinner.setPadding(10, 8, typespinner.getPaddingRight(), 12);
        propertyspinner.setSelectedIndex(0);
        propertyspinner.setSelected(true);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddListing_Place.this, AddListing_Room.class);
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