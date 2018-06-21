package com.cog.Dropinn.Traveller.Adapter;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.Models.PlaceAutoComplete;
import com.cog.Dropinn.R;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by test on 2/8/18.
 */

public class AutoCompleteAdapter extends ArrayAdapter<PlaceAutoComplete> {
    ViewHolder holder;
    Context context;
    List<PlaceAutoComplete> Places;
    private Activity mActivity;

    public AutoCompleteAdapter(Context context, List<PlaceAutoComplete> modelsArrayList, Activity activity) {
        super(context, R.layout.autocomplete_card, modelsArrayList);
        this.context = context;
        this.Places = modelsArrayList;
        this.mActivity = activity;
        //System.out.println("places==>"+Places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        System.out.println("view is working");
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.autocomplete_card, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.place_name);
            holder.coma=(TextView) rowView.findViewById(R.id.coma);
            holder.location = (TextView) rowView.findViewById(R.id.place_detail);
            rowView.setTag(holder);

        }
        else
            holder = (ViewHolder) rowView.getTag();
        /***** Get each Model object from ArrayList ********/
        holder.Place = Places.get(position);
        StringTokenizer st=new StringTokenizer(holder.Place.getPlaceDesc(), ",");
        /************  Set Model values in Holder elements ***********/

        holder.name.setText(st.nextToken());
        String desc_detail="";
        for(int i=1; i<st.countTokens(); i++) {
            if(i==st.countTokens()-1){
                desc_detail = desc_detail + st.nextToken();
            }else {
                desc_detail = desc_detail + st.nextToken();// + ",";
            }
        }
        holder.location.setText(desc_detail);
        if (holder.location.getText().toString().length()!=0){
            holder.coma.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

    class ViewHolder {
        PlaceAutoComplete Place;
        TextView name, location,coma;
    }

    @Override
    public int getCount(){
        return Places.size();
    }
}