package com.cog.Dropinn.Traveller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cog.Dropinn.Host.Settings_fragment;
import com.cog.Dropinn.Host.UI.HostSplash_Activity;
import com.cog.Dropinn.R;
import com.cog.Dropinn.both.Fragment.ViewProfile;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.cog.Dropinn.both.SignInSignUp.Login;

public class ProfileTraveller extends Fragment {

    View view;
    private FontUtility fontUtility;

    private SharedPrefrenceHelper sharedPrefrenceHelper;
    public boolean isTraveller = false;
    private TextView tvName;
    private TextView etViewProfile;
    private TextView tvSwitchHost;
    private TextView tvPayments;
    private TextView tvNotifications;
    private TextView tvSettings;
    private TextView tvHelp;
    private RelativeLayout layout_Notification;
    private RelativeLayout layout_Payment;
    private String TAG = ProfileTraveller.class.getSimpleName();
    private ProgressDialog progressDialog;

    public ProfileTraveller() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_traveller, container, false);
        fontUtility = new FontUtility(getActivity());
        sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());
        tvName = (TextView) view.findViewById(R.id.tvName);
        etViewProfile = (TextView) view.findViewById(R.id.etViewProfile);
        tvSwitchHost = (TextView) view.findViewById(R.id.tvSwitchHost);
        tvPayments = (TextView) view.findViewById(R.id.tvPayments);
        tvNotifications = (TextView) view.findViewById(R.id.tvNotifications);
        tvSettings = (TextView) view.findViewById(R.id.tvSettings);
        tvHelp = (TextView) view.findViewById(R.id.tvHelp);

        layout_Notification = (RelativeLayout) view.findViewById(R.id.layout_Notification);
        layout_Payment = (RelativeLayout) view.findViewById(R.id.layout_Payment);
        doProcess();

        return view;
    }

    private void doProcess() {
        etViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ViewProfile(), true);
            }
        });

        tvSwitchHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity = new Intent(getActivity(), HostSplash_Activity.class);
                startActivity(startActivity);
                getActivity().finish();
            }
        });

        tvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new Settings_fragment(), false);
            }
        });
        tvPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new PaymentTraveller(), true);
            }
        });
        tvNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new NotificationFragment(), true);
            }
        });
    }

    void changeFragment(Fragment fragment, boolean addBacktoStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        //ft.setTransition(transition);
        if (addBacktoStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStartTAG:" + TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
