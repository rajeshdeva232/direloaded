package com.cog.Dropinn.both.Fragment;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cog.Dropinn.Host.Profile_Host;
import com.cog.Dropinn.Models.Book;
import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.Models.ViewProfileModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.ProfileTraveller;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.RealmController;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by test on 1/2/18.
 */

public class ViewProfile extends Fragment {
    View view;
    private String TAG = ViewProfile.class.getSimpleName();

    private AppCompatImageView ivBack;
    private AppCompatImageView ivEdit;
    private AppCompatImageView ivUserImage;
    private FontUtility fontUtility;
    private AppCompatTextView tvMemberSince;
    private AppCompatTextView tvName;
    private AppCompatEditText edName;
    private AppCompatTextView tvGender;
    private AppCompatEditText tvGenderSelect;
    private AppCompatTextView tvDob;
    private AppCompatEditText tvDobSelect;
    private AppCompatTextView tvEmail;
    private AppCompatEditText edEmail;
    private AppCompatTextView tvPlace;
    private AppCompatEditText edPlace;
    private AppCompatTextView tvDesc;
    private AppCompatEditText edDesc;
    private AppCompatTextView tvSpokenLanguages;
    private AppCompatEditText edSpokenLanguages;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private String CURRENT_USER;

    public String USER_EMAIL;
    public String USER_ID;
    public String USER_FIRSTNAME;
    public String USER_LASTNAME;
    public String USER_SCHOOL;
    public String USER_PROFILEPIC;
    public String USER_SRC;
    public String USER_WORK;
    public String USER_PHONENUMBER;
    public String USER_COUNTRYCODE;
    public String USER_ABOUTME;
    public String USER_GENDER;
    public String USER_DOB;
    public String USER_JOINDATE;
    public String USER_LIVE;
    private ProgressDialog progressDialog;
    private static ViewProfile viewProfile;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_viewprofile, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());

        fontUtility = new FontUtility(getActivity());
        tvName = (AppCompatTextView) view.findViewById(R.id.tvName);
        edName = (AppCompatEditText) view.findViewById(R.id.edName);

        tvGender = (AppCompatTextView) view.findViewById(R.id.tvGender);
        tvGenderSelect = (AppCompatEditText) view.findViewById(R.id.tvGenderSelect);

        tvDob = (AppCompatTextView) view.findViewById(R.id.tvDob);
        tvDobSelect = (AppCompatEditText) view.findViewById(R.id.tvDobSelect);

        tvEmail = (AppCompatTextView) view.findViewById(R.id.tvEmail);
        edEmail = (AppCompatEditText) view.findViewById(R.id.edEmail);

        tvEmail = (AppCompatTextView) view.findViewById(R.id.tvEmail);
        edEmail = (AppCompatEditText) view.findViewById(R.id.edEmail);

        tvPlace = (AppCompatTextView) view.findViewById(R.id.tvPlace);
        edPlace = (AppCompatEditText) view.findViewById(R.id.edPlace);

        tvDesc = (AppCompatTextView) view.findViewById(R.id.tvDesc);
        edDesc = (AppCompatEditText) view.findViewById(R.id.edDesc);

        tvSpokenLanguages = (AppCompatTextView) view.findViewById(R.id.tvSpokenLanguages);
        edSpokenLanguages = (AppCompatEditText) view.findViewById(R.id.edSpokenLanguages);

        ivBack = (AppCompatImageView) view.findViewById(R.id.ivBack);
        ivEdit = (AppCompatImageView) view.findViewById(R.id.ivEdit);
        ivUserImage = (AppCompatImageView) view.findViewById(R.id.ivUserImage);
        this.realm = RealmController.with(this).getRealm();

        doRealm();

        doProcess();
        return view;
    }

    private void doProcess() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation_bar.selectTabAtPosition(Navigation_bar.getTabCount() - 1);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditProfile();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                realm.beginTransaction();
                realm.clear(Book.class);
                realm.commitTransaction();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CURRENT_USER = sharedPrefrenceHelper.getString("CURRENT_USER");

                if (CURRENT_USER.equals("HOST")) changeFragment(new Profile_Host(), false);
                else if (CURRENT_USER.equals("TRAVELLER"))
                    changeFragment(new ProfileTraveller(), false);
                else
                    Toast.makeText(getActivity(), "CAN'T FIND HOST_TYPE", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void changeFragment(Fragment fragment, boolean addBacktoStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        //ft.setTransition(transition);
        if (addBacktoStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    private void fetchDetails() {

        showDialog();
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.LIVE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            dismissDialog();
            Toast.makeText(getActivity(), "LIVE URL NOT VALID", Toast.LENGTH_SHORT).show();
        }
        RetrofitArrayAPI service = retrofit.create(RetrofitArrayAPI.class);
        Call<List<ViewProfileModel>> call = service.viewProfile(USER_ID, USER_EMAIL);
        Log.d(TAG, "ViewProfileModel: URL" + call.request().url().toString());

        call.enqueue(new Callback<List<ViewProfileModel>>() {
            @Override
            public void onResponse(Call<List<ViewProfileModel>> call, retrofit2.Response<List<ViewProfileModel>> response) {
                try {
                    List<ViewProfileModel> RequestData = response.body();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + RequestData.toString());

                        if (RequestData != null) {
                            List<ViewProfileModel> userList = response.body();
                            Log.d(TAG, "onResponse: userList.size " + userList.size());
                            for (int i = 0; i < userList.size(); i++) {
                                String strStatus = userList.get(i).getStatus();
                                if (strStatus != null) {
                                    if (strStatus.equals("success")) { //NoEmailExist
                                        assignValues(userList, i);
                                        initView();
                                        dismissDialog();
                                    } else if (strStatus.equals("This User is not available")) { //WhenEmailExist
                                        dismissDialog();
                                        Toast.makeText(getActivity(), "This user is not Available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            dismissDialog();
                            Toast.makeText(getApplicationContext(), "EMPTY RESPONSE", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        dismissDialog();
                        Log.e(TAG, "onResponse: Fail" + response.message());
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<ViewProfileModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(getActivity(), "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(getActivity(), "URL IN ERROR STATE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
                dismissDialog();

            }
        });
    }

    private void initView() {
        Log.i(TAG, "assignValues: USER_ID " + USER_ID);
        Log.i(TAG, "assignValues: USER_FIRSTNAME " + USER_FIRSTNAME);
        Log.i(TAG, "assignValues: USER_LASTNAME " + USER_LASTNAME);
        Log.i(TAG, "assignValues: USER_SCHOOL " + USER_SCHOOL);
        Log.i(TAG, "assignValues: USER_EMAIL " + USER_EMAIL);
        Log.i(TAG, "assignValues: USER_PROFILEPIC " + USER_PROFILEPIC);
        Log.i(TAG, "assignValues: USER_SRC " + USER_SRC);
        Log.i(TAG, "assignValues: USER_WORK " + USER_WORK);
        Log.i(TAG, "assignValues: USER_PHONENUMBER " + USER_PHONENUMBER);
        Log.i(TAG, "assignValues: USER_COUNTRYCODE " + USER_COUNTRYCODE);
        Log.i(TAG, "assignValues: USER_ABOUTME " + USER_ABOUTME);
        Log.i(TAG, "assignValues: USER_GENDER " + USER_GENDER);
        Log.i(TAG, "assignValues: USER_DOB " + USER_DOB);
        Log.i(TAG, "assignValues: USER_JOINDATE " + USER_JOINDATE);
        Log.i(TAG, "assignValues: USER_LIVE " + USER_LIVE);

        edName.setText(USER_FIRSTNAME + " " + USER_LASTNAME);
        tvGenderSelect.setText(USER_GENDER);
        tvDobSelect.setText(USER_DOB);
        edEmail.setText(USER_EMAIL);
        edPlace.setText("Manhatten,Newyork,USA");
        edDesc.setText(USER_ABOUTME);
        edSpokenLanguages.setText("English,Tamil,Kannada,Hindi,Arabic");
        /*Glide.with(ViewProfile.this)
                .load(USER_PROFILEPIC)
                .placeholder(R.drawable.no_image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivUserImage);*/
    }

    private void doRealm() {
        showDialog();
        int value;
        realm.beginTransaction();
        RealmResults<Book> movies = realm.where(Book.class).findAll();
        value = (int) realm.where(Book.class).maximumInt("id");
        Log.i(TAG, "doRealm: value " + value);
        /*for (Book movie : movies) {
            Log.d(TAG, "doRealm: movie.getTitle() "+movie.getTitle());
            Log.d(TAG, "doRealm: movie.getTitle() "+movie.getAuthor());
            Log.d(TAG, "doRealm: movie.getTitle() "+movie.getDescription());
            Log.d(TAG, "doRealm: movie.getTitle() "+movie.getImageUrl());
        }*/
        realm.commitTransaction();
        setRealmData(value);
    }

    private void setRealmData(int value) {
        ArrayList<Book> books = new ArrayList<>();
        Book book = new Book();

        book.setId(value + 1);
        book.setAuthor("Reto Meier");
        book.setTitle("Android 4 Application Development");
        book.setImageUrl("https://api.androidhive.info/images/realm/1.png");
        book.setImageByte(getBitmapByte(getBitmapFromURL("https://api.androidhive.info/images/realm/1.png")));
        Log.i(TAG, "setRealmData: imageByte " + Arrays.toString(book.getImageByte()));
        ivUserImage.setImageBitmap(ByteArrayToBitmap(book.getImageByte()));
        books.add(book);

        book = new Book();
        book.setId(value + 2);
        book.setAuthor("Itzik Ben-Gan");
        book.setTitle("Microsoft SQL Server 2012 T-SQL Fundamentals");
        book.setImageUrl("https://api.androidhive.info/images/realm/2.png");
        books.add(book);

        book = new Book();
        book.setId(value + 3);
        book.setAuthor("Magnus Lie Hetland");
        book.setTitle("Beginning Python: From Novice To Professional Paperback");
        book.setImageUrl("https://api.androidhive.info/images/realm/3.png");
        books.add(book);

        book = new Book();
        book.setId(value + 4);
        book.setAuthor("Chad Fowler");
        book.setTitle("The Passionate Programmer: Creating a Remarkable Career in Software Development");
        book.setImageUrl("https://api.androidhive.info/images/realm/4.png");
        books.add(book);

        book = new Book();
        book.setId(value + 5);
        book.setAuthor("Yashavant Kanetkar");
        book.setTitle("Written Test Questions In C Programming");
        book.setImageUrl("https://api.androidhive.info/images/realm/5.png");
        books.add(book);

        for (Book b : books) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(b);
            realm.commitTransaction();
        }
        dismissDialog();
    }

    private byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void assignValues(List<ViewProfileModel> userList, int i) {
        USER_ID = userList.get(i).getUserId();
        USER_FIRSTNAME = userList.get(i).getFirstname();
        USER_LASTNAME = userList.get(i).getLastname();
        USER_SCHOOL = String.valueOf(userList.get(i).getSchool());
        USER_PROFILEPIC = userList.get(i).getProfilePic();
        USER_SRC = userList.get(i).getSrc();
        USER_WORK = userList.get(i).getWork();
        USER_PHONENUMBER = userList.get(i).getPhnum();
        USER_COUNTRYCODE = userList.get(i).getCountryCode();
        USER_ABOUTME = userList.get(i).getAboutMe();
        USER_GENDER = String.valueOf(userList.get(i).getGender());
        USER_DOB = userList.get(i).getDob();
        USER_JOINDATE = userList.get(i).getJoinDate();
        USER_LIVE = userList.get(i).getLive();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //ivUserImage.setImageBitmap(myBitmap);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap ByteArrayToBitmap(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing");
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            if (!getActivity().isFinishing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Realm.getDefaultInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        USER_ID = sharedPrefrenceHelper.getString("USER_ID");
        USER_EMAIL = sharedPrefrenceHelper.getString("USER_EMAIL");
        fetchDetails();
        Log.i(TAG, "onStartTAG:" + TAG);

        Log.i(TAG, "onStart: USER_ID " + USER_ID);
        Log.i(TAG, "onStart: USER_EMAIL " + USER_EMAIL);

        tvName.setTypeface(fontUtility.getLatoRegular());
        edName.setTypeface(fontUtility.getLatoRegular());
        tvGender.setTypeface(fontUtility.getLatoRegular());
        tvGenderSelect.setTypeface(fontUtility.getLatoRegular());
        tvDob.setTypeface(fontUtility.getLatoRegular());
        tvDobSelect.setTypeface(fontUtility.getLatoRegular());
        tvEmail.setTypeface(fontUtility.getLatoRegular());
        edEmail.setTypeface(fontUtility.getLatoRegular());
        tvEmail.setTypeface(fontUtility.getLatoRegular());
        edEmail.setTypeface(fontUtility.getLatoRegular());
        tvPlace.setTypeface(fontUtility.getLatoRegular());
        edPlace.setTypeface(fontUtility.getLatoRegular());
        tvDesc.setTypeface(fontUtility.getLatoRegular());
        edDesc.setTypeface(fontUtility.getLatoRegular());
        tvSpokenLanguages.setTypeface(fontUtility.getLatoRegular());
        edSpokenLanguages.setTypeface(fontUtility.getLatoRegular());
        tvPlace.setTypeface(fontUtility.getLatoRegular());
        tvDesc.setTypeface(fontUtility.getLatoRegular());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
