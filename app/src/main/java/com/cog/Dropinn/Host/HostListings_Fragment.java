package com.cog.Dropinn.Host;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cog.Dropinn.Host.Activity.AddListing_Start;
import com.cog.Dropinn.Host.Adapter.Listings_Adapter;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

import java.util.ArrayList;
import java.util.List;

public class HostListings_Fragment extends Fragment {

    private View view;
    private TextView tvListings;
    private AppCompatImageView ivAdd;
    private FontUtility fontUtility;
    RecyclerView listRecyler;
    private List<Inbox_pojo> arrayList;
    private Listings_Adapter listingsAdapter;
    private TextView tvNoList;
    private AppCompatImageView ivAdd_1;

    public HostListings_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listings, container, false);
        fontUtility = new FontUtility(getActivity());

        fill_with_data();
//        tvListings = (TextView) view.findViewById(R.id.tvListings);
        tvNoList = (TextView) view.findViewById(R.id.tvNoList);
        ivAdd = (AppCompatImageView) view.findViewById(R.id.ivAdd);
        ivAdd_1 = (AppCompatImageView) view.findViewById(R.id.ivAdd_1);

        doProcess();
        return view;
    }

    private void doProcess() {
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddListing_Start.class);
                startActivity(intent);
            }
        });
        ivAdd_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddListing_Start.class);
                startActivity(intent);
            }
        });
    }

    public void fill_with_data() {

        Inbox_pojo inbox_pojo = new Inbox_pojo(R.drawable.inbox_empty, "Dropinn 1111","hellow","going","time");
        List<Inbox_pojo> arrayList = new ArrayList<>();

        inbox_pojo.setAddress("3838 Bates Brothers Road,Grove City,");
        inbox_pojo.setState("OH, Ohio, 43123");
        arrayList.add(0,inbox_pojo);

        listRecyler = (RecyclerView) view.findViewById(R.id.listRecyler);
        listingsAdapter = new Listings_Adapter(arrayList, getActivity());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listRecyler.setLayoutManager(horizontalLayoutManager);
        listRecyler.setAdapter(listingsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        /*tvListings.setTypeface(fontUtility.getLatoRegular());
        tvNoList.setTypeface(fontUtility.getLatoRegular());*/

    }
}
