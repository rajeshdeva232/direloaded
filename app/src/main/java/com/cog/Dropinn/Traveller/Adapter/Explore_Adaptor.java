package com.cog.Dropinn.Traveller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.cog.Dropinn.Models.ExploreResultModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.Activity.Listing_details;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import javax.sql.DataSource;

/**
 * Created by test on 2/12/18.
 */

public class Explore_Adaptor extends RecyclerView.Adapter<Explore_Adaptor.MyviewHolder> {

    List<ExploreResultModel> list;
    Context context;

    public Explore_Adaptor(List<ExploreResultModel> list, FragmentActivity activity) {
        this.list = list;
        this.context = activity.getApplicationContext();
        System.out.println("Explore_Adaptor working(1)");
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder working");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_listing_adapter_card, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, final int position) {
        System.out.println("onbind view holder working");
        holder.tvBedroomtype.setText(list.get(position).getRoomType());
        String noofBeds = list.get(position).getBeds() + " Beds";
        holder.tvNoofbeds.setText(noofBeds);
        holder.tvTitle.setText(list.get(position).getTitle());
        String pricepernight = list.get(position).getCountrySymbol() + " " + list.get(position).getPrice() + " " + "per night";
        System.out.println("currency code==>" + list.get(position).getCountrySymbol());
        holder.tvPerNight.setText(Html.fromHtml(pricepernight), TextView.BufferType.SPANNABLE);
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getOverallreview()));
        System.out.println("rateing===>" + Float.parseFloat(list.get(position).getOverallreview()));
        holder.cardListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Listing_details.class);
                intent.putExtra("ID",list.get(position).getId());
                intent.putExtra("CURRENCYCODE",list.get(position).getCurrencyCode());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });
//        Glide.with(context)
//                .load(list.get(position).getImage())
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//                        // Do something with bitmap here.
//                        holder.thumbnail.setImageBitmap(bitmap);
//                        Log.e("GalleryAdapter","Glide getMedium ");
//
//                        Glide.with(context)
//                                .load(list.get(position).getImage())
//                                .asBitmap()
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//                                        // Do something with bitmap here.
//                                        holder.thumbnail.setImageBitmap(bitmap);
//                                        Log.e("GalleryAdapter","Glide getLarge ");
//                                    }
//                                });
//                    }
//                });
        Glide.with(context)
                .load(list.get(position).getImage())
                .placeholder(R.drawable.no_image)
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.ivListingImage.setImageResource(R.drawable.no_image);
                        holder.progressAnim.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressAnim.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivListingImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        ImageView ivListingImage;
        TextView tvBedroomtype, tvNoofbeds, tvTitle, tvPerNight;
        RatingBar ratingBar;
        CardView cardListing;
        AVLoadingIndicatorView progressAnim;
        public ImageView thumbnail;

        public MyviewHolder(View itemView) {
            super(itemView);
            ivListingImage = (ImageView) itemView.findViewById(R.id.ivListingImage);
            tvBedroomtype = (TextView) itemView.findViewById(R.id.tvBedroomtype);
            tvNoofbeds = (TextView) itemView.findViewById(R.id.tvNoofbeds);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPerNight = (TextView) itemView.findViewById(R.id.tvPerNight);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            progressAnim = (AVLoadingIndicatorView) itemView.findViewById(R.id.progressAnim);
            cardListing=(CardView)itemView.findViewById(R.id.cardListing);
        }
    }
}
