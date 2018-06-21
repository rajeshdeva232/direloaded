package com.cog.Dropinn.Traveller.Activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cog.Dropinn.Models.DetailsModel;
import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.ContactHost;
import com.cog.Dropinn.Utils.MySpannable;

import java.io.EOFException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.shmakinv.android.material.widget.GpCollapsingToolbar;

public class Listing_details extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener  {

    private int mMaxScrollSize;
    TextView tvTittle,tvHostName,tvRoomType,tvNoofguest,tvNoofbedroom,tvNoofbeds,tvDetails,tvRoomprice;
    private boolean mIsAvatarShown = true;
    ImageButton ibBAck;
    ImageView ivTittleimage,ivHostpic;
    RelativeLayout layoutContact;
    GpCollapsingToolbar toolbar_layout;
    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 24;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    private static final String TAG = Listing_details.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_details);
        ibBAck=(ImageButton)findViewById(R.id.ibBack);
        tvRoomprice=(TextView)findViewById(R.id.tvRoomprice);
        tvNoofguest=(TextView)findViewById(R.id.tvNoofguest);
        tvNoofbedroom=(TextView)findViewById(R.id.tvNoofbedroom);
        tvNoofbeds=(TextView)findViewById(R.id.tvNoofbeds);
        layoutContact=(RelativeLayout)findViewById(R.id.layoutContact);
        tvDetails=(TextView)findViewById(R.id.tvDetails);
        ivHostpic=(ImageView)findViewById(R.id.ivHostpic);
        tvRoomType=(TextView)findViewById(R.id.tvRoomType);
        tvHostName=(TextView)findViewById(R.id.tvHostName);
        ivTittleimage=(ImageView)findViewById(R.id.ivTittleimage);
        toolbar_layout = (GpCollapsingToolbar)findViewById(R.id.toolbar_layout);
        tvTittle=(TextView)findViewById(R.id.tvTittle);
        toolbar_layout.setGooglePlayBehaviour(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(Listing_details.this);
        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_white));
        makeTextViewResizable(tvDetails, 4, "...read more", true);
        tvDetails.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = tvDetails.getLineCount();
                Log.d(TAG, "run no of lines==>: "+lineCount);
                // Use lineCount here
            }
        });
        //makeTextViewResizable(tvDetails, 4, "See More", true);
        Intent intent=getIntent();
        String Roomid=intent.getStringExtra("ID");
        String CurrencyCode=intent.getStringExtra("CURRENCYCODE");
        Log.i(TAG, "onCreate: ID,CURRENCYCODE"+Roomid+CurrencyCode);
        details(Roomid,CurrencyCode);
        layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Listing_details.this,ContactHost.class);
                startActivity(i);
            }
        });
    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        System.out.println("working1");
                       /* tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();*/
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        System.out.println("working");
                     /*   tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();*/
                        makeTextViewResizable(tv, 3, "...read more", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if(mMaxScrollSize==0) {
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }
            int percentage = (Math.abs(verticalOffset)) * 35 / mMaxScrollSize;
            System.out.println("percentage==>"+percentage);
            if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
                mIsAvatarShown = false;
                ibBAck.setImageResource(R.drawable.ic_goback);
            }
            if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
                mIsAvatarShown = true;
                ibBAck.setImageResource(android.R.color.transparent);
                ibBAck.setImageResource(R.drawable.ic_arrow_white);
            }

    }

    public void details(String Roomid,String CurrencyCode){

        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.ROOM_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            Toast.makeText(this, "LIVE URL NOT VALID", Toast.LENGTH_SHORT).show();
        }
        RetrofitArrayAPI service = retrofit.create(RetrofitArrayAPI.class);
        Call<List<DetailsModel>> call = service.details(Roomid,CurrencyCode);
        Log.d(TAG, "details: detailsURL==>"+ call.request().url().toString());
        call.enqueue(new Callback<List<DetailsModel>>() {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                List<DetailsModel> Data=response.body();
                if(Data!=null){
                    for(int i=0;i<Data.size();i++){
                        String Tittle=Data.get(i).getTitle();
                        String RoomType=Data.get(i).getRoomType();
                        String HostName=Data.get(i).getFname();
                        String noofBeds=Data.get(i).getBeds();
                        String noofBeedroom=Data.get(i).getBathrooms();
                        String noofGuest=Data.get(i).getCapacity();
                        String describtion=Data.get(i).getDesc();
                        String HostPic=Data.get(i).getSrc();
                        String Tittleimage=Data.get(i).getImage();
                        String Price=Data.get(i).getPrice();
                        String Currencysymbol=Data.get(i).getCurrencySymbol();
                        Glide.with(Listing_details.this).load(Tittleimage).into(ivTittleimage);
                        Glide.with(Listing_details.this).load(HostPic).placeholder(getDrawable(R.drawable.placeholder)).into(ivHostpic);
                        tvTittle.setText(Tittle);
                        tvHostName.setText(HostName);
                        tvRoomType.setText(RoomType);
                        if(noofGuest.equals("1")) {
                            noofGuest = noofGuest + " guest";
                            tvNoofguest.setText(noofGuest);
                        }else {
                            noofGuest = noofGuest + " guests";
                            tvNoofguest.setText(noofGuest);
                        }
                        if(noofBeedroom.equals("1")) {
                            noofBeedroom = noofBeedroom + " \nbedroom";
                            tvNoofbedroom.setText(noofBeedroom);
                        }else {
                            noofBeedroom = noofBeedroom + " \nbedrooms";
                            tvNoofbedroom.setText(noofBeedroom);
                        }
                        if(noofBeds.equals("1")) {
                            noofBeds = noofBeds + " bed";
                            tvNoofbeds.setText(noofBeds);
                        }else {
                            noofBeds = noofBeds + " beds";
                            tvNoofbeds.setText(noofBeds);
                        }
                        tvDetails.setText(describtion);
                        Price=Currencysymbol+Price+" per night";
                        tvRoomprice.setText(Html.fromHtml(Price));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DetailsModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(Listing_details.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(Listing_details.this, "URL IN ERROR STATE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Listing_details.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
