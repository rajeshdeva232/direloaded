package com.cog.Dropinn.Host.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cog.Dropinn.Host.Inbox_pojo;
import com.cog.Dropinn.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by test on 2/2/18.
 */

public class Inbox_Adaptor extends RecyclerView.Adapter<Inbox_Adaptor.MyViewHolder> {

    List<Inbox_pojo> horizontalList = Collections.emptyList();
    Context context;

    public Inbox_Adaptor(List<Inbox_pojo> data, FragmentActivity activity) {
        this.horizontalList = data;
        this.context = activity;
    }

    @Override
    public Inbox_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Inbox_Adaptor.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(horizontalList.get(position).imageId);
        String name="Guest Name:"+horizontalList.get(position).txt;
        holder.txtview.setText(name);
        holder.dateago.setText(horizontalList.get(position).time);
        String tittle=horizontalList.get(position).status+" - "+horizontalList.get(position).title;
        holder.tittle.setText(tittle);
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview,dateago,tittle,date;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.profile_img);
            txtview = (TextView) view.findViewById(R.id.guestname_tv);
            dateago=(TextView)view.findViewById(R.id.ago);
            tittle=(TextView)view.findViewById(R.id.tittle);

        }
    }

}
