package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {

    FontUtility fontUtility;
    TextView inbox,inboxtxt,messagetxt;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inbox_fragment, container, false);
        fontUtility = new FontUtility(getContext());
        inbox=(TextView)view.findViewById(R.id.Inboxtv);
        inboxtxt=(TextView)view.findViewById(R.id.inbox_tv);
        messagetxt=(TextView)view.findViewById(R.id.message_tv);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        inbox.setTypeface(fontUtility.getLatoBold());
        messagetxt.setTypeface(fontUtility.getLatoBold());
        inboxtxt.setTypeface(fontUtility.getLatoRegular());
    }

}
