package com.cog.Dropinn.Traveller.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cog.Dropinn.Host.Inbox_pojo;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

import java.util.Collections;
import java.util.List;

/**
 * Created by test on 2/2/18.
 */

public class TravellerListings_Adapter extends RecyclerView.Adapter<TravellerListings_Adapter.MyViewHolder> {

    List<Inbox_pojo> ArrayList = Collections.emptyList();
    Context context;
    private FontUtility fontUtility;

    public TravellerListings_Adapter(List<Inbox_pojo> arrayList, FragmentActivity activity) {
        this.ArrayList = arrayList;
        this.context = activity;
    }

    @Override
    public TravellerListings_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_listing_adapter_card, parent, false);
        fontUtility = new FontUtility(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TravellerListings_Adapter.MyViewHolder holder, int position) {
        holder.tvTitle.setTypeface(fontUtility.getLatoBold());
        holder.tvPerNight.setTypeface(fontUtility.getLatoRegular());
        holder.tvNoofBeds.setTypeface(fontUtility.getLatoRegular());

        holder.tvTitle.setText(ArrayList.get(position).getTitle());
        holder.tvPerNight.setText(ArrayList.get(position).getPrice());
        holder.tvNoofBeds.setText(ArrayList.get(position).getNoofBeds());
    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RatingBar ratingBar;
        private final ImageView ivListingImage;
        private final TextView tvPerNight;
        private final TextView tvTitle;
        private final TextView tvNoofBeds;

        public MyViewHolder(View view) {
            super(view);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            ivListingImage = (ImageView) view.findViewById(R.id.ivListingImage);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvPerNight = (TextView) view.findViewById(R.id.tvPerNight);
            tvNoofBeds = (TextView) view.findViewById(R.id.tvNoofbeds);
        }
    }

}
