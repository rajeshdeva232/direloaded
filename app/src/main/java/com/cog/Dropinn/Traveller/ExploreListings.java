package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cog.Dropinn.Models.ExploreResultModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Adapter.Explore_Adaptor;
import com.cog.Dropinn.Utils.AppController;
import com.cog.Dropinn.Utils.FontUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.cog.Dropinn.Traveller.ExploreFragment.CURRENT_PAGE;

public class ExploreListings extends Fragment {

    private static final String TAG = ExploreListings.class.getSimpleName();
    private FontUtility fontUtility;
    RelativeLayout toppanelExplore;
    TextView search_tv, ivLocationHint;
    RecyclerView recyclerListing;
    //NestedScrollView contentmain;
    private int CUSTOM_AUTOCOMPLETE_REQUEST_CODE = 13;
    Explore_Adaptor explore_adaptor;
    static public List<ExploreResultModel> list = new ArrayList<>();
    static String CHOSEN_LOCATION;
    private View view;
    private AppCompatImageView ivGoback;
    private Bundle bundle;
    static private List<ExploreResultModel> temp= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.explore_listings, container, false);

        fontUtility = new FontUtility(getActivity());
        toppanelExplore = (RelativeLayout) view.findViewById(R.id.layoutTopPanel);
       // contentmain = (NestedScrollView) view.findViewById(R.id.main_content);
        ivLocationHint = (TextView) view.findViewById(R.id.ivLocationHint);
        recyclerListing = (RecyclerView) view.findViewById(R.id.recyclerListing);
        ivGoback = (AppCompatImageView) view.findViewById(R.id.ivGoback);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerListing.setLayoutManager(horizontalLayoutManager);

        collectData();
        initView();
        processNext();
        return view;
    }

    private void initView() {
        CURRENT_PAGE = "LISTINGS";
        Log.i(TAG, "initView: CURRENT_PAGE " + CURRENT_PAGE);
    }

    private void collectData() {
        if (getArguments() != null) {
            if (getArguments().getString("CHOSEN_LOCATION") != null) {
                CHOSEN_LOCATION = getArguments().getString("CHOSEN_LOCATION");
                Log.i(TAG, "CHOSEN_LOCATION: " + CHOSEN_LOCATION);
                String TEMP = CHOSEN_LOCATION.indexOf(",") > 0 ? CHOSEN_LOCATION.substring(0, CHOSEN_LOCATION.indexOf(",")) : CHOSEN_LOCATION;
                ivLocationHint.setText(TEMP);
                getSearchresult(1, CHOSEN_LOCATION.replace(" ", "%20"));
            } else if (getArguments().getString("COMING_FROM").equals("RESTORE")) {
                Log.i(TAG, "CHOSEN_LOCATION: " + CHOSEN_LOCATION);
                String TEMP = CHOSEN_LOCATION.indexOf(",") > 0 ? CHOSEN_LOCATION.substring(0, CHOSEN_LOCATION.indexOf(",")) : CHOSEN_LOCATION;
                ivLocationHint.setText(TEMP);
                getSearchresult(1,CHOSEN_LOCATION.replace(" ", "%20"));
            }
        }
        Log.i(TAG, "onStart: CHOSEN_LOCATION " + CHOSEN_LOCATION);
    }

    public void processNext() {
        ivGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("CHOSEN_LOCATION", "LISTINGS");
                changeFragment(new ExploreFragment(), false, bundle, true);
            }
        });
        toppanelExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("COMING_FROM", "LISTINGS");
                bundle.putString("CURRENT_CHOICE", CHOSEN_LOCATION);
                changeFragment(new SelectLocationDialogFragment(), false, bundle, false);
            }
        });
    }

    public void getSearchresult(int noofguest, String location_searched) {
        String url = Constants.SEARCH_PAGE_URL + "search_results?guests=&room_types=&price_min=&price_max=&min_beds=&min_bedrooms=&min_bathrooms=&location=" + location_searched + "&start=1&checkin=null&checkout=null&common_currency=USD&keywords=";
        System.out.println("search result url==>" + url);
        final JsonArrayRequest searchReq = new JsonArrayRequest(url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (jsonObject.optString("status").equals("List Found")) {
                            ExploreResultModel exploreResultModel = new ExploreResultModel();
                            exploreResultModel.setBeds(jsonObject.optString("beds"));
                            //exploreResultModel.setThumbnail(jsonObject.optString("resize"));
                            exploreResultModel.setTitle(jsonObject.optString("title"));
                            exploreResultModel.setRoomType(jsonObject.optString("room_type"));
                            exploreResultModel.setCurrencyCode(jsonObject.optString("currency_code"));
                            exploreResultModel.setId(jsonObject.optString("id"));
                            exploreResultModel.setCountrySymbol(jsonObject.optString("country_symbol"));
                            exploreResultModel.setPrice(jsonObject.optString("price"));
                            exploreResultModel.setOverallreview(jsonObject.optString("overallreview"));
                            exploreResultModel.setImage(jsonObject.optString("image"));
                            list.add(exploreResultModel);
                        } else {
                            Toast.makeText(getContext(), "No list found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | NullPointerException e) {

                        e.printStackTrace();
                    }
                }
                explore_adaptor = new Explore_Adaptor(list, getActivity());
                recyclerListing.setAdapter(explore_adaptor);
                explore_adaptor.notifyDataSetChanged();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "onErrorResponse: error" + volleyError.getMessage());
                    Toast.makeText(getContext(), "Failed to load Listings", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AppController.getInstance().addToRequestQueue(searchReq);
        searchReq.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void setList(List<ExploreResultModel> list) {
        ExploreListings.list = list;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored: ");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
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
