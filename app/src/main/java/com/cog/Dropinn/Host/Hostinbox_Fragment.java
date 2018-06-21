package com.cog.Dropinn.Host;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cog.Dropinn.Host.Adapter.Inbox_Adaptor;
import com.cog.Dropinn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hostinbox_Fragment extends Fragment {

    Inbox_Adaptor inbox_adaptor;
    RecyclerView inbox;
    private List<Inbox_pojo> data;

    public Hostinbox_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_hostinbox, container, false);
        data = fill_with_data();
        inbox=(RecyclerView)v.findViewById(R.id.recyclerview_inbox);
        inbox_adaptor=new Inbox_Adaptor(data, getActivity());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        inbox.setLayoutManager(horizontalLayoutManager);
        inbox.setAdapter(inbox_adaptor);
        return v;
    }

    public List<Inbox_pojo> fill_with_data() {
        List<Inbox_pojo> data = new ArrayList<>();
        data.add(new Inbox_pojo(  R.drawable.man,"Christopher","4 Days ago","Palace","Pending"));
        data.add(new Inbox_pojo(  R.drawable.man_1, "Tyler","10 Days ago","Mansion","Accepted"));
        data.add(new Inbox_pojo( R.drawable.man_2, "Jacob","12 Days ago","Garden","Accepted"));

        return data;
    }


}
