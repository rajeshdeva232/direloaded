package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

/**
 * Created by test on 1/2/18.
 */

public class TripsFragment extends Fragment {
    View view;
    Animation slideUpAnimation;
    ImageView autochanger;
    Handler handler;
    Runnable runnable;
    final int[] imageArray = {
            R.drawable.anim_home,
            R.drawable.anim_bicycle,
            R.drawable.anim_plane,
            R.drawable.anim_glass,
            R.drawable.anim_leaf
    };
    private TextView tvAdventure;
    private TextView tvAdventure1;
    private TextView tvStartExploring;
    private FontUtility fontUtility;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trips_traveller, container, false);
        fontUtility = new FontUtility(getActivity());
        autochanger = (ImageView) view.findViewById(R.id.ivAutochange);
        slideUpAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.activity_in);
        tvAdventure = (TextView) view.findViewById(R.id.tvAdventure); //Regular
        tvAdventure1 = (TextView) view.findViewById(R.id.tvAdventure1); //Regular
        tvStartExploring = (TextView) view.findViewById(R.id.tvStartExploring);
        doProcess();
        return view;
    }

    private void doProcess() {
        tvStartExploring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation_bar.selectTabAtPosition(0);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        startAnimate();
        tvAdventure.setTypeface(fontUtility.getLatoRegular());
        tvStartExploring.setTypeface(fontUtility.getLatoRegular());
        tvAdventure1.setTypeface(fontUtility.getLatoRegular());
    }

    void startAnimate() {
        handler = new Handler();
        runnable = new Runnable() {
            int i = 0;

            public void run() {
                autochanger.setImageResource(imageArray[i]);
                slideUpAnimation.setDuration(1300);
                autochanger.startAnimation(slideUpAnimation);
                //autochanger.animate().translationY(4);
                i++;
                if (i > imageArray.length - 1) i = 0;
                handler.postDelayed(this, 1200);  //for interval...
            }
        };
        handler.postDelayed(runnable, 1000); //for initial delay..
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
