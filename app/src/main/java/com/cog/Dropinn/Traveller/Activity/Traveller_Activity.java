package com.cog.Dropinn.Traveller.Activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.util.Log;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.ExploreFragment;
import com.cog.Dropinn.Traveller.InboxFragment;
import com.cog.Dropinn.Traveller.ProfileTraveller;
import com.cog.Dropinn.Traveller.SavedListingsFragment;
import com.cog.Dropinn.Traveller.TripsFragment;
import com.cog.Dropinn.Utils.BottomNavigationViewHelper;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Traveller_Activity extends AppCompatActivity {
    public static BottomBar Navigation_bar;
    SharedPrefrenceHelper sharedPrefrenceHelper;
    private FontUtility fontUtility;
    private String TAG=Traveller_Activity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigation_bar = (BottomBar)
                findViewById(R.id.navigation);
//        Navigation_bar = (BottomNavigationView) findViewById(R.id.navigation);
//        BottomNavigationViewHelper.disableShiftMode(Navigation_bar);

        sharedPrefrenceHelper = new SharedPrefrenceHelper(Traveller_Activity.this);
        fontUtility = new FontUtility(Traveller_Activity.this);
        sharedPrefrenceHelper.saveString("CURRENT_USER", "TRAVELLER");
        Fragment fragment = new ExploreFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

//        Navigation_bar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment fragment = null;
//                switch (item.getItemId()){
//                    case R.id.navigation_expor:
//                        fragment = new ExploreFragment();
//                        break;
//
//                    case R.id.navigation_save:
//                        fragment = new Saved_fragment();
//                        break;
//
//                    case R.id.navigation_trips:
//                        fragment=new TripsFragment();
//
//                    case R.id.navigation_inbox:
//                        fragment=new Inbox_fragment();
//                        break;
//
//                    case R.id.navigation_profile:
//                        fragment = new Profile_Traveller();
//                       break;
//                }
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, fragment);
//                transaction.commit();
//                return true;
//            }
//        });
//
//    }

        Navigation_bar.setActiveTabColor(getResources().getColor(R.color.bgBottomNavigation));
        Navigation_bar.setTabTitleTypeface(fontUtility.getLatoBold());
        Navigation_bar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                Fragment fragment = null;
                switch (tabId) {
                    case R.id.navigation_expor:
                        fragment = new ExploreFragment();
                        break;
                    case R.id.navigation_save:
                        fragment = new SavedListingsFragment() ;
                        break;
                    case R.id.navigation_trips:
                        fragment = new TripsFragment();
                        break;
                    case R.id.navigation_inbox:
                        fragment = new InboxFragment();
                        break;
                    case R.id.navigation_profile:
                        fragment = new ProfileTraveller();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
    }
}
