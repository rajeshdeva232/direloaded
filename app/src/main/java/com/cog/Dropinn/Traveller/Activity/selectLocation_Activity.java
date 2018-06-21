package com.cog.Dropinn.Traveller.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.cog.Dropinn.Models.PlacePredictions;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.Adapter.AutoCompleteAdapter;
import com.cog.Dropinn.Utils.AppController;
import com.cog.Dropinn.Utils.VolleyJSONRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class selectLocation_Activity extends Activity implements Response.Listener<String>, Response.ErrorListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location_layout);
        askPermission();
        ivClose = (ImageView) findViewById(R.id.close_btn);
        etSearch = (EditText) findViewById(R.id.search_bar);
        btnReset = (TextView) findViewById(R.id.btnReset);
        searchresult = (ListView) findViewById(R.id.searchResultLV);

        if (getIntent().hasExtra("Search Text")) {
            preFilledText = getIntent().getStringExtra("Search Text");
        }
        //        changeFragment(new ExploreFragment(), true);
        doProcess();
        initView();
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
                System.out.println("on text change method called");
                if (etSearch.getText().toString().trim().length() > 0) {
                    btnReset.setVisibility(View.VISIBLE);
                } else {
                    btnReset.setVisibility(View.GONE);
                }
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        // cancel all the previous requests in the queue to optimise your network calls during autocomplete search
                        AppController.getInstance().cancelPendingRequests(GETPLACESHIT);
                        //build Get url of Place Autocomplete and hit the url to fetch result.
                        request = new VolleyJSONRequest(Request.Method.GET,     getPlaceAutoCompleteUrl(String.valueOf(s)), null, null, selectLocation_Activity.this, selectLocation_Activity.this);
                        //Give a tag to your request so that you can use this tag to cancle request later.
                        request.setShouldCache(true);
                        AppController.getInstance().addToRequestQueue(request, GETPLACESHIT);
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
                finish();
            }
        });

        searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyBoard();
                Log.i(TAG, "onItemClick: Location Address " + predictions.getPlaces().get(position).getPlaceDesc());
                Intent data = new Intent();
                data.setData(Uri.parse(predictions.getPlaces().get(position).getPlaceDesc()));
                setResult(RESULT_OK, data);
//---close the activity---
                finish();
            }
        });
    }

    private void initView() {
        etSearch.setText(preFilledText);
        etSearch.setSelection(etSearch.getText().length());
    }

    @Override
    public void onResponse(String response) {
        //Log.d("PLACES RESULT:::", response);
        Gson gson = new Gson();
        predictions = gson.fromJson(response, PlacePredictions.class);
        System.out.println("predictions.getPlaces()==>" + predictions.getPlaces());
        if (mAutoCompleteAdapter == null) {
            mAutoCompleteAdapter = new AutoCompleteAdapter(getApplicationContext(), predictions.getPlaces(), selectLocation_Activity.this);
            searchresult.setAdapter(mAutoCompleteAdapter);
            mAutoCompleteAdapter.notifyDataSetChanged();
        } else {
            mAutoCompleteAdapter.clear();
            mAutoCompleteAdapter.addAll(predictions.getPlaces());
            mAutoCompleteAdapter.notifyDataSetChanged();
            searchresult.invalidate();
        }
    }

    public String getPlaceAutoCompleteUrl(String input) {
        System.out.println("Working in get auto complete view ");
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
        System.out.println("aurl from appen method "+urlString.toString());
        //Log.d("FINAL URL:::   ", urlString.toString());
        return urlString.toString();
    }

    public void askPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            fetchLocation();
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOC);
            } else {
                fetchLocation();
            }
        }
    }

    public void hideKeyBoard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
