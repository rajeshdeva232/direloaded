package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cog.Dropinn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactHost extends FragmentActivity {


    public ContactHost() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_contact_host);
    }

}
