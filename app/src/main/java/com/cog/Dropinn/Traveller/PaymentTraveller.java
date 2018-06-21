package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cog.Dropinn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentTraveller extends Fragment {
    ImageView Backbtn;

    public PaymentTraveller() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_payment_traveller, container, false);
        Backbtn=view.findViewById(R.id.ivBack);
        doprogress();
        return view;
    }
    void changeFragment(Fragment fragment, boolean addBacktoStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        //ft.setTransition(transition);
        if (addBacktoStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    public void doprogress(){
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ProfileTraveller(), false);
            }
        });
    }
}
