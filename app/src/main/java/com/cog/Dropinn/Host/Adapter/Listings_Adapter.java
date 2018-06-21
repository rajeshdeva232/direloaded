package com.cog.Dropinn.Host.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cog.Dropinn.Host.Inbox_pojo;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;

import java.util.Collections;
import java.util.List;

/**
 * Created by test on 2/2/18.
 */

public class Listings_Adapter extends RecyclerView.Adapter<Listings_Adapter.MyViewHolder> {

    List<Inbox_pojo> ArrayList = Collections.emptyList();
    Context context;
    private FontUtility fontUtility;

    public Listings_Adapter(List<Inbox_pojo> arrayList, FragmentActivity activity) {
        this.ArrayList = arrayList;
        this.context = activity;
    }

    @Override
    public Listings_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings_adapter_view, parent, false);
        fontUtility = new FontUtility(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Listings_Adapter.MyViewHolder holder, int position) {
        holder.tvInprogress.setTypeface(fontUtility.getLatoRegular());
        /*holder.tvAddress.setTypeface(fontUtility.getLatoRegular());
        holder.tvStateCity.setTypeface(fontUtility.getLatoRegular());*/

        /*holder.tvAddress.setText(ArrayList.get(position).getAddress());
        holder.tvStateCity.setText(ArrayList.get(position).getState());*/
        holder.ivProfile.setImageResource(R.drawable.image_room);
    }

    @Override
    public int getItemCount() {
        return ArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar progressBar;
        private final AppCompatImageView ivProfile;
        private final TextView tvInprogress;
        private final TextView tvAddress;
        private final TextView tvStateCity;

        public MyViewHolder(View view) {
            super(view);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvStateCity = (TextView) view.findViewById(R.id.tvStateCity);
            ivProfile = (AppCompatImageView) view.findViewById(R.id.ivProfile);

            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            tvInprogress = (TextView) view.findViewById(R.id.tvInprogress);
        }
    }

}
