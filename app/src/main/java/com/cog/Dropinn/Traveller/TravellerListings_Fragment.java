package com.cog.Dropinn.Traveller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cog.Dropinn.Host.Inbox_pojo;
import com.cog.Dropinn.Models.ExploreResultModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Adapter.Explore_Adaptor;
import com.cog.Dropinn.Traveller.Adapter.TravellerListings_Adapter;
import com.cog.Dropinn.Utils.AppController;
import com.cog.Dropinn.Utils.FontUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TravellerListings_Fragment extends Fragment {

    private View view;
    private TextView tvListings,hint;
    private AppCompatImageView ivAdd;
    private FontUtility fontUtility;
    public List<ExploreResultModel> list=new ArrayList<>();
    String LocationAddress="";
    RecyclerView recyclerListing;
    private TravellerListings_Adapter listingsAdapter;
    private TextView tvNoList;
    private Button alldate,guest,hometype,price;
    Explore_Adaptor explore_adaptor;

    public TravellerListings_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_listing, container, false);
        fontUtility = new FontUtility(getActivity());

//        tvListings = (TextView) view.findViewById(R.id.tvListings);
        tvNoList = (TextView) view.findViewById(R.id.tvNoList);
        ivAdd = (AppCompatImageView) view.findViewById(R.id.ivAdd);
        recyclerListing = (RecyclerView) view.findViewById(R.id.recyclerListing);
        hint=(TextView)view.findViewById(R.id.search_hint);
        alldate=(Button)view.findViewById(R.id.btnalldates);
        guest=(Button)view.findViewById(R.id.btnguest);
        hometype=(Button)view.findViewById(R.id.btnhometype);
        price=(Button)view.findViewById(R.id.btnprice);
        hint.setTypeface(fontUtility.getLatoRegular());
        alldate.setTypeface(fontUtility.getLatoRegular());
        guest.setTypeface(fontUtility.getLatoRegular());
        hometype.setTypeface(fontUtility.getLatoRegular());
        price.setTypeface(fontUtility.getLatoRegular());

        try{
            if(!getActivity().getIntent().getStringExtra("Location Address").equals(null)){
               LocationAddress=getActivity().getIntent().getStringExtra("Location Address");
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerListing.setLayoutManager(horizontalLayoutManager);
               fill_with_data(LocationAddress.replaceAll(" ","%20"));
                hint.setText(LocationAddress.replaceAll(" ","%20"));

            }
        }catch (NullPointerException e){
            System.out.println("Error==>"+e);
        }
        return view;
    }

    public void fill_with_data(String LocationAddress) {

        String url = Constants.SEARCH_PAGE_URL+"search_results?guests=&room_types=&price_min=&price_max=&min_beds=&min_bedrooms=&min_bathrooms=&location="+LocationAddress+"&start=1&checkin=null&checkout=null&common_currency=USD&keywords=";
        System.out.println("search result url==>"+url);
        final JsonArrayRequest searchReq = new JsonArrayRequest(url, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(jsonObject.optString("status").equals("List Found")) {
                            ExploreResultModel exploreResultModel=new ExploreResultModel();
                            exploreResultModel.setBeds(jsonObject.optString("beds"));
                            //exploreResultModel.setThumbnail(jsonObject.optString("resize"));
                            exploreResultModel.setTitle(jsonObject.optString("title"));
                            exploreResultModel.setRoomType(jsonObject.optString("room_type"));
                            //exploreResultModel.sethostProfile(jsonObject.optString("src"));
                            // exploreResultModel.setRoomID(jsonObject.optString("id"));
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
                }
            }
        });

        AppController.getInstance().addToRequestQueue(searchReq);
        searchReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
