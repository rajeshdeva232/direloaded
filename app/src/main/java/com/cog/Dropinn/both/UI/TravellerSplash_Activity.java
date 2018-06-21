package com.cog.Dropinn.both.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.Activity.Traveller_Activity;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.NetworkUtils;

public class TravellerSplash_Activity extends AppCompatActivity {

    private FontUtility fontUtility;
    private TextView ivSwitchingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveller_splash);
        fontUtility = new FontUtility(TravellerSplash_Activity.this);
        ivSwitchingImage = (TextView) findViewById(R.id.ivSwitchingImage);
        initView();
    }

    private void initView() {
        TravellerSplash_Activity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!NetworkUtils.isNetworkAvailable(TravellerSplash_Activity.this)) {
                    showDialog(true, "Close");
                } else {
                    resumeLogin();
                }
            }
        }, 3000);
    }

    void showDialog(final boolean close, String text) {
        new AlertDialog.Builder(TravellerSplash_Activity.this)
                .setTitle("internet Required")
                .setMessage("cannot Access Internet Connection..")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(text, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (close) {

                        }
                    }
                }).show();
    }

    private void resumeLogin() {
        Intent startActivity = new Intent(TravellerSplash_Activity.this, Traveller_Activity.class);
        startActivity(startActivity);
        TravellerSplash_Activity.this.finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        ivSwitchingImage.setTypeface(fontUtility.getLatoRegular());
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
