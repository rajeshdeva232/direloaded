package com.cog.Dropinn.both.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cog.Dropinn.Host.Activity.Host_Activity;
import com.cog.Dropinn.Models.EmailExistModel;
import com.cog.Dropinn.Models.RealmLogin;
import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Activity.Traveller_Activity;
import com.cog.Dropinn.Utils.NetworkUtils;
import com.cog.Dropinn.Utils.RealmController;
import com.cog.Dropinn.Utils.Retrofit.APIHelper;
import com.cog.Dropinn.Utils.Retrofit.RetryableCallback;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.cog.Dropinn.both.SignInSignUp.Login;
import com.cog.Dropinn.both.SignInSignUp.SignupEmail;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.EOFException;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    private Realm realm;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private String USER_ID;
    private String USER_EMAIL;
    private String USER_USERNAME;
    private String USER_PROFILE;
    private String TAG = SplashActivity.class.getSimpleName();
    AVLoadingIndicatorView progressAnim;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.realm = RealmController.with(this).getRealm();
        sharedPrefrenceHelper = new SharedPrefrenceHelper(SplashActivity.this);
        progressAnim = (AVLoadingIndicatorView) findViewById(R.id.progressAnim);
        initView();
    }

    private void initView() {
        doRealm();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (android.os.Build.VERSION.SDK_INT > 22) {
                    if (!NetworkUtils.isNetworkAvailable(SplashActivity.this)) {
                        showDialog(true, "Close");
                    } else {
                        resumeLogin();
                    }
                } else {
                    resumeLogin();
                }
            }
        }, 3000);
    }

    private void doRealm() {
        realm.beginTransaction();
        RealmLogin realmResults
                = realm.where(RealmLogin.class).findFirst();
        if (realmResults != null) {
            progressAnim.setVisibility(View.VISIBLE);
        } else {
            progressAnim.setVisibility(View.INVISIBLE);
        }
        realm.commitTransaction();
    }

    void showDialog(final boolean close, String text) {
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle("internet Required")
                .setMessage("cannot Access Internet Connection..")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(text, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (close) {
                            finish();
                        }
                    }
                }).show();
    }

    private void resumeLogin() {
        realm.beginTransaction();
        RealmLogin realmResults
                = realm.where(RealmLogin.class).findFirst();
        Log.i(TAG, "resumeLogin: realmResults " + realmResults);

        if (realmResults != null) {
            USER_ID = realmResults.getUserID();
            USER_EMAIL = realmResults.getUserEmail();
            USER_USERNAME = realmResults.getUserName();
            USER_PROFILE = realmResults.getUserProfile();

            Log.i(TAG, "resumeLogin: USER_ID " + USER_ID);
            Log.i(TAG, "resumeLogin: USER_EMAIL " + USER_EMAIL);
            Log.i(TAG, "resumeLogin: USER_USERNAME " + USER_USERNAME);
            Log.i(TAG, "resumeLogin: USER_PROFILE " + USER_PROFILE);

            sharedPrefrenceHelper.saveString("USER_ID", USER_ID);
            sharedPrefrenceHelper.saveString("USER_EMAIL", USER_EMAIL);
            sharedPrefrenceHelper.saveString("USER_USERNAME", USER_USERNAME);
            sharedPrefrenceHelper.saveString("USER_PROFILE", USER_PROFILE);

            if (sharedPrefrenceHelper.getString("CURRENT_USER").equals("HOST")) {
                intent = new Intent(SplashActivity.this, Host_Activity.class);
            } else {
                intent = new Intent(SplashActivity.this, Traveller_Activity.class);
            }
            isEmailExists(USER_EMAIL);
        } else {
            realm.commitTransaction();
            intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            SplashActivity.this.finish();
            overridePendingTransition(R.anim.anim_right_to_left, R.anim.stay);
        }
    }

    void isEmailExists(String userEmail) {
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.EMAIL_EXIST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            Toast.makeText(this, "LIVE URL NOT VALID", Toast.LENGTH_SHORT).show();
        }
        RetrofitArrayAPI service = retrofit.create(RetrofitArrayAPI.class);
        Call<List<EmailExistModel>> call = service.isEmailExist(userEmail);
        Log.d(TAG, "isEmailExists: URL" + call.request().url().toString());

        call.enqueue(new RetryableCallback<List<EmailExistModel>>(call, 3) {
            @Override
            public void onResponse(Call<List<EmailExistModel>> call, retrofit2.Response<List<EmailExistModel>> response) {
                try {
                    List<EmailExistModel> RequestData = response.body();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + RequestData.toString());

                        if (RequestData != null) {
                            List<EmailExistModel> userList = response.body();
                            for (int i = 0; i < userList.size(); i++) {
                                String strStatus = userList.get(i).getStatus();
                                if (strStatus != null) {
                                    if (strStatus.equals("Success")) { //NoEmailExist
                                        Toast.makeText(SplashActivity.this, "PREV USER ID NOT AVAIL - AUTOLOGIN", Toast.LENGTH_SHORT).show();
                                    } else if (strStatus.equals("Fail")) { //WhenEmailExist
                                        processNext();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "EMPTY RESPONSE", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e(TAG, "onResponse: Fail" + response.message());
                        Toast.makeText(SplashActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<EmailExistModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(SplashActivity.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_LONG).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(SplashActivity.this, "URL IN ERROR STATE", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SplashActivity.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void processNext() {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        realm.commitTransaction();
        SplashActivity.this.finish();
        overridePendingTransition(R.anim.anim_right_to_left, R.anim.stay);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Realm.getDefaultInstance();
    }

    @Override
    protected void onStop() {
        super.onStop();
        realm.close();
    }
}
