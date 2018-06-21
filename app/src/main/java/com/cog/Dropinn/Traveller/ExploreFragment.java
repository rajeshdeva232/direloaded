package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cog.Dropinn.Models.ExploreResultModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private static final String TAG = ExploreFragment.class.getSimpleName();
    private FontUtility fontUtility;
    LinearLayout searchtittle;
    public static String CURRENT_PAGE = "EXPLORE";
    CardView search_layout;
    public List<ExploreResultModel> list = new ArrayList<>();
    RelativeLayout frontcontent;
    TextView search_tv;
    private Bundle bundle;
    private String COMING_FROM = "EXPLORE";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explore_traveller, container, false);
        bundle = new Bundle();

        fontUtility = new FontUtility(getActivity());
        search_layout = (CardView) view.findViewById(R.id.searchbar_layout);
        searchtittle = (LinearLayout) view.findViewById(R.id.searchTitle);
        search_tv = (TextView) view.findViewById(R.id.anywhereTxt);
        frontcontent = (RelativeLayout) view.findViewById(R.id.front_content);

        processNext();
        collectData();
        initView();
        return view;
    }

    private void initView() {
        Log.i(TAG, "initView: CURRENT_PAGE "+CURRENT_PAGE);
        if (CURRENT_PAGE.equals("LISTINGS")) {
            bundle = new Bundle();
            bundle.putString("COMING_FROM", "RESTORE");
            changeFragment(new ExploreListings(), false, bundle, true);
        }else{
            CURRENT_PAGE = "EXPLORE";
        }
    }

    public void processNext() {
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString("COMING_FROM", "EXPLORE");
                changeFragment(new SelectLocationDialogFragment(), false, bundle, false);
            }
        });
    }

    private void collectData() {
        if (getArguments() != null) {
            COMING_FROM = getArguments().getString("COMING_FROM");
            Log.i(TAG, "COMING_FROM: " + COMING_FROM);
        }
    }

    void changeFragment(Fragment fragment, boolean addBacktoStack, Bundle bundle, boolean replaceFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        if (replaceFragment) {
            ft.replace(R.id.fragment_container, fragment);
        } else {
            ft.add(R.id.fragment_container, fragment);
        }
        //ft.setTransition(transition);
        if (addBacktoStack) {
            ft.remove(new ExploreFragment());
            ft.addToBackStack(null);
        }
        ft.setCustomAnimations(R.anim.anim_right_to_left, 0);
        ft.commit();
    }
}
