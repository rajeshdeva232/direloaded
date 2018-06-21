package com.cog.Dropinn.Host.Activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cog.Dropinn.Host.HostCalendar_Fragment;
import com.cog.Dropinn.Host.HostListings_Fragment;
import com.cog.Dropinn.Host.Hostinbox_Fragment;
import com.cog.Dropinn.Host.Profile_Host;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Host_Activity extends AppCompatActivity {

    static BottomBar Navigation_bar;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    private FontUtility fontUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_activity);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(Host_Activity.this);
        fontUtility = new FontUtility(Host_Activity.this);
        Navigation_bar = (BottomBar)
                findViewById(R.id.navigation);

        sharedPrefrenceHelper.saveString("CURRENT_USER", "HOST");
        Navigation_bar.setTabTitleTypeface(fontUtility.getLatoRegular());
        Navigation_bar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                Fragment fragment = null;
                switch (tabId) {
                    case R.id.navigation_inbox_host:
                        fragment = new Hostinbox_Fragment();
                        break;
                    case R.id.navigation_calender:
                        fragment = new HostCalendar_Fragment();
                        break;

                    case R.id.navigation_listing:
                        fragment = new HostListings_Fragment();
                        break;
                    case R.id.navigation_profile_host:
                        fragment = new Profile_Host();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        Intent intent = getIntent();
        int TO_SELECT = intent.getIntExtra("TO_SELECT", 3);
        Navigation_bar.selectTabAtPosition(TO_SELECT);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
