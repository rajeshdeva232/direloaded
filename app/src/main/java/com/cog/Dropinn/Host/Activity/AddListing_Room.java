package com.cog.Dropinn.Host.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;

import com.cog.Dropinn.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddListing_Room extends AppCompatActivity {

    private MaterialSpinner spinnerTotalGuests;
    private MaterialSpinner spinnerNoofBedrooms;
    private MaterialSpinner spinnerNoofBeds;
    java.util.ArrayList<String> strings;
    private RelativeLayout btnNext;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        strings = new java.util.ArrayList<>();

        btnNext = (RelativeLayout) findViewById(R.id.next_btn);
        spinnerTotalGuests = (MaterialSpinner) findViewById(R.id.spinnerTotalGuests);
        spinnerNoofBedrooms = (MaterialSpinner) findViewById(R.id.spinnerNoofBedrooms);
        spinnerNoofBeds = (MaterialSpinner) findViewById(R.id.spinnerNoofBeds);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);
        doProcess();
    }

    private void doProcess() {
        spinnerTotalGuests.setItems("1", "2", "3", "4");
        spinnerTotalGuests.setPadding(10, 8, spinnerTotalGuests.getPaddingRight(), 12);
        spinnerTotalGuests.setSelectedIndex(0);
        spinnerTotalGuests.setSelected(true);

        spinnerNoofBedrooms.setItems("Private Room", "Shared Room", "Entire Place");
        spinnerNoofBedrooms.setPadding(10, 8, spinnerNoofBedrooms.getPaddingRight(), 12);
        spinnerNoofBedrooms.setSelectedIndex(0);
        spinnerNoofBedrooms.setSelected(true);

        spinnerNoofBeds.setItems("1", "2", "3");
        spinnerNoofBeds.setPadding(10, 8, spinnerNoofBeds.getPaddingRight(), 12);
        spinnerNoofBeds.setSelectedIndex(0);
        spinnerNoofBeds.setSelected(true);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddListing_Room.this, AddListing_BaseFare.class);
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