package com.cog.Dropinn.Host.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.PermissionUtility;

public class AddListing_Photo extends AppCompatActivity {

    private String userChoosenTask;
    private AppCompatButton btnAddPhotos;
    private AppCompatButton btnPhotoLater;
    private AppCompatImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlisting_photo);

        btnAddPhotos = (AppCompatButton) findViewById(R.id.btnAddPhotos);
        btnPhotoLater = (AppCompatButton) findViewById(R.id.btnPhotoLater);
        ivBack = (AppCompatImageView) findViewById(R.id.ivBack);
        doProcess();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        Log.d("ONE", "onRequestPermissionsResult() called with: requestCode = [" + requestCode + "], permissions = [" + permissions + "], grantResults = [" + grantResults + "]");
                    else if (userChoosenTask.equals("Choose from Library"))
                        Log.d("TWO", "onRequestPermissionsResult() called with: requestCode = [" + requestCode + "], permissions = [" + permissions + "], grantResults = [" + grantResults + "]");
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void doProcess() {
        btnAddPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoto();
            }
        });
        btnPhotoLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddListing_Photo.this, AddListing_Title.class);
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

    void showPhoto() {
        final CharSequence[] items = {"CAMERA", "GALLERY"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddListing_Photo.this);
        builder.setTitle("Take Picture");
        builder.setMessage("Take a new photo or select one from your photo library");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                boolean result = PermissionUtility.checkPermission(AddListing_Photo.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) {
                        Log.d("TWOONE", "onClick() called with: dialog = [" + dialog + "], item = [" + item + "]");
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result) {
                        Log.d("TWOTWO", "onClick() called with: dialog = [" + dialog + "], item = [" + item + "]");
                    }
                }
            }
        });
        builder.show();
    }
}