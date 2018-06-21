package com.cog.Dropinn.Traveller.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Models.NotificationAdapter_Model;

import java.util.Collections;
import java.util.List;

/**
 * Created by test on 2/3/18.
 */

public class Notification_adaptor extends RecyclerView.Adapter<Notification_adaptor.MyViewHolder>{

    List<NotificationAdapter_Model> horizontalList = Collections.emptyList();
    Context context;

    public Notification_adaptor(List<NotificationAdapter_Model> data, FragmentActivity activity){
        this.horizontalList = data;
        this.context = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationcard_traveller, parent, false);
        System.out.println("working");
        return new Notification_adaptor.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.imageView.setImageResource(horizontalList.get(position).imageId);
        holder.txtview.setText(horizontalList.get(position).txt);

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtview;

        public MyViewHolder(View view) {
            super(view);

            imageView=(ImageView) view.findViewById(R.id.profile_img);
            txtview=(TextView) view.findViewById(R.id.notification_tv);

        }
    }

}
