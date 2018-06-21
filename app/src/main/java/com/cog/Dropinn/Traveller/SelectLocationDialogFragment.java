package com.cog.Dropinn.Traveller;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cog.Dropinn.Models.PlaceAutoComplete;
import com.cog.Dropinn.Models.PlacePredictions;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.Activity.selectLocation_Activity;
import com.cog.Dropinn.Traveller.Adapter.AutoCompleteAdapter;
import com.cog.Dropinn.Utils.AppController;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.cog.Dropinn.Utils.VolleyJSONRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.cog.Dropinn.Traveller.ExploreFragment.CURRENT_PAGE;


public class SelectLocationDialogFragment extends DialogFragment implements Response.Listener<String>, Response.ErrorListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ImageView ivClose;
    EditText etSearch;
    private String GETPLACESHIT = "places_hit";
    private VolleyJSONRequest request;
    private Handler handler;
    ListView searchresult;
    private PlacePredictions predictions;
    TextView btnReset;
    double latitude, longitude;
    private String preFilledText;
    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 20;
    private Location mLastLocation;
    private static final int MY_PERMISSIONS_REQUEST_LOC = 30;
    private AutoCompleteAdapter mAutoCompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = selectLocation_Activity.class.getSimpleName();
    private String COMING_FROM;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private ArrayList<PlaceAutoComplete> predictionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_location_layout, container,
                false);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());
        getActivity().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        getActivity().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ivClose = (ImageView) view.findViewById(R.id.close_btn);
        etSearch = (EditText) view.findViewById(R.id.search_bar);
        btnReset = (TextView) view.findViewById(R.id.btnReset);
        searchresult = (ListView) view.findViewById(R.id.searchResultLV);
        collectData();
        askPermission();
        doProcess();
        initView();

        return view;
    }

    private void collectData() {
        CURRENT_PAGE = "LOCATION";
        Log.i(TAG, "initView: CURRENT_PAGE " + CURRENT_PAGE);
        if (getArguments() != null) {
            COMING_FROM = getArguments().getString("COMING_FROM");
        }
    }

    private void initView() {
        showKeyboard();
        etSearch.setText(preFilledText);
        etSearch.setSelection(etSearch.getText().length());
    }

    private void doProcess() {
        searchresult.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                hideKeyBoard();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (etSearch.getText().toString().trim().length() > 0) {
                    btnReset.setVisibility(View.VISIBLE);
                } else {
                    btnReset.setVisibility(View.GONE);
                }
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        if (s != null) {
                            // cancel all the previous requests in the queue to optimise your network calls during autocomplete search
                            AppController.getInstance().cancelPendingRequests(GETPLACESHIT);
                            //build Get url of Place Autocomplete and hit the url to fetch result.
                            request = new VolleyJSONRequest(Request.Method.GET, getPlaceAutoCompleteUrl(String.valueOf(s)), null, null, SelectLocationDialogFragment.this, SelectLocationDialogFragment.this);
                            //Give a tag to your request so that you can use this tag to cancle request later.
                            request.setShouldCache(true);
                            AppController.getInstance().addToRequestQueue(request, GETPLACESHIT);
                        }
                    }
                };
                // only canceling the network calls will not help, you need to remove all callbacks as well
                // otherwise the pending callbacks and messages will again invoke the handler and will send the request
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                } else {
                    handler = new Handler();
                }
                handler.postDelayed(run, 0);

            }

            @Override
            public void afterTextChanged(final Editable s) {
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();
                etSearch.getText().clear();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (COMING_FROM != null) {
                    if (COMING_FROM.equals("LISTINGS")) {
                        hideKeyBoard();
                        //changeFragment(new ExploreListings(), false, bundle, false);
                    } else if (COMING_FROM.equals("EXPLORE")) {
                        hideKeyBoard();
                        //
                        // changeFragment(new ExploreFragment(), false, bundle, false);
                    }
                }
                dismiss();
            }
        });

        searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyBoard();
                String CHOSEN_LOCATION = predictions.getPlaces().get(position).getPlaceDesc();
                Log.i(TAG, "onItemClick: Location Address " + CHOSEN_LOCATION);
                sharedPrefrenceHelper.saveString("CHOSEN_LOCATION", CHOSEN_LOCATION);
                Bundle bundle = new Bundle();
                bundle.putString("CHOSEN_LOCATION", CHOSEN_LOCATION);
              /*  if (COMING_FROM.equals("EXPLORE")) {
                    changeFragment(new ExploreListings(), false, bundle, true);
                } else if (COMING_FROM.equals("LISTINGS")) {
                    hideKeyBoard();
                    changeFragment(new ExploreListings(), false, bundle, true);
                }*/
                changeFragment(new ExploreListings(), false, bundle, true);
                dismiss();
            }
        });
    }

    @Override
    public void onResponse(String response) {
        //Log.d("PLACES RESULT:::", response);
        Gson gson = new Gson();
        predictions = gson.fromJson(response, PlacePredictions.class);
        System.out.println("predictions.getPlaces()==>" + predictions.getPlaces());
        predictionList = predictions.getPlaces();
        if (mAutoCompleteAdapter == null) {
            if (!predictionList.isEmpty()) {
                mAutoCompleteAdapter = new AutoCompleteAdapter(getActivity(), predictionList, getActivity());
                searchresult.setAdapter(mAutoCompleteAdapter);
                mAutoCompleteAdapter.notifyDataSetChanged();
            }
        } else {
            mAutoCompleteAdapter.clear();
            mAutoCompleteAdapter.addAll(predictions.getPlaces());
            mAutoCompleteAdapter.notifyDataSetChanged();
            searchresult.invalidate();
        }
    }

    public String getPlaceAutoCompleteUrl(String input) {
        System.out.println("Working1233444");
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/autocomplete/json");
        urlString.append("?input=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&location=");
        urlString.append(latitude + "," + longitude); // append lat long of current location to show nearby results.
        urlString.append("&radius=500&language=en");
        urlString.append("&key=" + "AIzaSyCvKRr1Q0j7Kyfz-2MNtoyQ8np2RUZAYxA");
        System.out.println("string url "+urlString.toString());
        //Log.d("FINAL URL:::   ", urlString.toString());
        return urlString.toString();
    }

    public void askPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fetchLocation();
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOC);
            } else {
                fetchLocation();
            }
        }
    }

    private void showKeyboard() {
        etSearch.requestFocus();
        ((InputMethodManager) (getContext()).getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hideKeyBoard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void fetchLocation() {
        //Build google API client to use fused location
        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
