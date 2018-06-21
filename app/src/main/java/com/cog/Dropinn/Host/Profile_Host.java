package com.cog.Dropinn.Host;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.both.Fragment.ViewProfile;
import com.cog.Dropinn.both.UI.TravellerSplash_Activity;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Host extends Fragment {

    public boolean isTraveller = false;
    private TextView tvNotifications;
    private TextView tvSwitchHost;
    private TextView tvPayments;
    private TextView tvSettings;
    private TextView tvHelp;
    private FontUtility fontUtility;
    private TextView etViewProfile;
    private TextView tvName;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private RelativeLayout layout_Notification;
    private RelativeLayout layout_Payment;

    public Profile_Host() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_host, container, false);

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
                changeFragment(new ViewProfile(), false);
            }
        });

        tvSwitchHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivity = new Intent(getActivity(), TravellerSplash_Activity.class);
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

        tvName.setTypeface(fontUtility.getLatoRegular());
        etViewProfile.setTypeface(fontUtility.getLatoRegular());
        tvNotifications.setTypeface(fontUtility.getLatoRegular());
        tvSwitchHost.setTypeface(fontUtility.getLatoRegular());
        tvPayments.setTypeface(fontUtility.getLatoRegular());
        tvSettings.setTypeface(fontUtility.getLatoRegular());
        tvHelp.setTypeface(fontUtility.getLatoRegular());
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
