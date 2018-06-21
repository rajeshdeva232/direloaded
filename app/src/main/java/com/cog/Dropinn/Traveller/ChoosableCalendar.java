package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

/**
 * Created by test on 1/2/18.
 */

public class ChoosableCalendar extends Fragment {
    View view;
    private FontUtility fontUtility;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choosable_calendar, container, false);
        fontUtility = new FontUtility(getActivity());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        tvAdventure.setTypeface(fontUtility.getLatoRegular());
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
