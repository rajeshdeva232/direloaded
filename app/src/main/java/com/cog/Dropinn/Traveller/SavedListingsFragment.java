package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cog.Dropinn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedListingsFragment extends Fragment {
    private String TAG = SavedListingsFragment.class.getSimpleName();

    public SavedListingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_fragment, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored: "+TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: "+TAG);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
