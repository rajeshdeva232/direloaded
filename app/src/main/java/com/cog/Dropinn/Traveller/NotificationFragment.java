package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cog.Dropinn.Models.NotificationAdapter_Model;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.Adapter.Notification_adaptor;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    Notification_adaptor inbox_adaptor;
    RecyclerView inbox;
    private List<NotificationAdapter_Model> data;
    ImageView back_btn;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);
        data = filldata();
        inbox=(RecyclerView)view.findViewById(R.id.notification);
        back_btn=(ImageView)view.findViewById(R.id.ivBack);
        inbox_adaptor= new Notification_adaptor(data, getActivity());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        inbox.setLayoutManager(horizontalLayoutManager);
        inbox.setAdapter(inbox_adaptor);
        doprogress();

        return view;
    }

     public List<NotificationAdapter_Model> filldata(){

         List<NotificationAdapter_Model> data = new ArrayList<>();

         data.add(new NotificationAdapter_Model(  R.drawable.ic_app_logo,"Add your work email unlock priorrity email. Unlock priority supports and extra points"));
         data.add(new NotificationAdapter_Model(  R.drawable.placeholder,"You got Notification"));
         data.add(new NotificationAdapter_Model(  R.drawable.placeholder,"You got Notification"));

         return data;

     }
    public void doprogress(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ProfileTraveller(), false);
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

}
