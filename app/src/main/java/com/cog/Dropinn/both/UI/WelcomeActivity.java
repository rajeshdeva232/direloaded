package com.cog.Dropinn.both.UI;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.Models.FacebookSignupModel;
import com.cog.Dropinn.Models.RealmLogin;
import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Activity.Traveller_Activity;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.RealmController;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.cog.Dropinn.both.SignInSignUp.Login;
import com.cog.Dropinn.both.SignInSignUp.SignupName;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.EOFException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WelcomeActivity extends AppCompatActivity {
    FontUtility fontUtility;
    TextView login, tearms, tittle;
    TextView btncreate_account;
    Button facebook;
    private ArrayList<String> rDates1 = new ArrayList<String>();
    ImageButton closebtn;
    String TAG = "FacebookLogin";

    //Facebook Declaration
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    Bundle parameters;
    private String fbFullName;
    private String fbEmail;
    private String fbID;
    private String fbFirstName;
    private String fbLastName;
    private String fbUserProfile;
    private ProgressDialog progressDialog;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private Realm realm;
    private String USER_PROFILE;
    private String USER_ID;
    private String USER_FIRSTNAME;
    private String USER_LASTNAME;
    private String USER_EMAIL;
    private String USER_USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.realm = RealmController.with(this).getRealm();
        fontUtility = new FontUtility(WelcomeActivity.this);
        login = (TextView) findViewById(R.id.login_text);
        btncreate_account = (TextView) findViewById(R.id.btncreate_account);
        closebtn = (ImageButton) findViewById(R.id.imgBtClose);
        facebook = (Button) findViewById(R.id.btnFacebook);
        tearms = (TextView) findViewById(R.id.tvTerms);
        tittle = (TextView) findViewById(R.id.title_text);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(WelcomeActivity.this);
        doProcess();
        setupFB();
        setupFont();
        getHash();
    }

    public void doProcess() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(WelcomeActivity.this, CalendarActivity.class);
                Intent intent = new Intent(WelcomeActivity.this, Login.class);
                //intent.putStringArrayListExtra("listdates",rDates1);
                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
                startActivity(intent, bndlanimation);
            }
        });

        btncreate_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, SignupName.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();//Logout Facebook
                LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this, Arrays.asList("public_profile", "email"));
            }
        });
    }

    private void setupFB() {
        //    compile 'com.facebook.android:facebook-android-sdk:4.2.0'
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        if (Profile.getCurrentProfile() == null) {
                            profileTracker = new ProfileTracker() {
                                @Override
                                protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                    //Log.v("facebook - profile2", profile2.getFirstName());
                                    displayMessage(profile2);
                                    profileTracker.stopTracking();
                                }
                            };
                            profileTracker.startTracking();
                        } else {
                            Profile profile = Profile.getCurrentProfile();
                            displayMessage(profile);
                            //Log.v("facebook - profile", profile.getFirstName());
                        }
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        System.out.println("jsonobject" + object);
                                        fbEmail = object.optString("email");
                                        fbFullName = object.optString("name");
                                        fbFullName = fbFullName.replaceAll(" ", "%20");
                                        fbID = object.optString("id");
                                        callFaceBookLogIn();
                                    }
                                });
                        parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,gender,birthday,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Bundle bundle = new Bundle();
                        bundle.putString("fields", "token_for_business");
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("Facebook Login failed!!");
                        //Toast.makeText(EnrollActivity.this, "Login Cancelled by user!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Log.d("Facebooksdk", "Login with Facebook failure", e);
                        Toast.makeText(WelcomeActivity.this, "An unknown network error has occured", Toast.LENGTH_LONG).show();
                        System.out.println("Facebook Login failed!!");
                    }
                });
    }

    private void callFaceBookLogIn() {
        LoginManager.getInstance().logOut();//Logout Facebook
        doFacebookSignup();
    }

    void doFacebookSignup() {
        String JoinDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/"
                + Calendar.getInstance().get(Calendar.MONTH) + 1 + "/"
                + Calendar.getInstance().get(Calendar.YEAR);

        showDialog();
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.LIVE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            dismissDialog();
            Toast.makeText(this, "LIVE URL NOT VALID", Toast.LENGTH_SHORT).show();
        }
        RetrofitArrayAPI service = retrofit.create(RetrofitArrayAPI.class);
        Call<List<FacebookSignupModel>> call = service.doFacebookSignup(fbFirstName, fbLastName, fbEmail, fbID, "null", "null", "null", "null", "", JoinDate);
        Log.d(TAG, "doFacebookSignup: URL " + call.request().url().toString());

        call.enqueue(new Callback<List<FacebookSignupModel>>() {
            @Override
            public void onResponse(Call<List<FacebookSignupModel>> call, retrofit2.Response<List<FacebookSignupModel>> response) {
                try {
                    List<FacebookSignupModel> RequestData = response.body();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + RequestData.toString());
                        if (RequestData != null) {
                            List<FacebookSignupModel> userList = response.body();
                            for (int i = 0; i < userList.size(); i++) {
                                String strSuccess = userList.get(i).getStatus();
                                Log.i(TAG, "onResponse: strStatus " + strSuccess);
                                if (strSuccess != null) {
                                    if (strSuccess.equals("Successfully registered")) { //NoEmailExist
                                        dismissDialog();
                                        processNext(userList, i);
                                    } else if (strSuccess.equals("Sorry! This email has already been registered")) { //WhenEmailExist
                                        processNext(userList, i);
                                        dismissDialog();
                                    } else {
                                        Toast.makeText(WelcomeActivity.this, "SOCIAL LOGIN FAILED - UNEXPECTED ERROR " + strSuccess, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                dismissDialog();
                            }
                        } else {
                            dismissDialog();
                            Toast.makeText(getApplicationContext(), "EMPTY RESPONSE", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        dismissDialog();
                        Log.e(TAG, "onResponse: Fail" + response.message());
                        Toast.makeText(WelcomeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<FacebookSignupModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(WelcomeActivity.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(WelcomeActivity.this, "URL IN ERROR STATE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WelcomeActivity.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
                dismissDialog();
            }
        });
    }
    //get Facebook Details

    private void processNext(List<FacebookSignupModel> userList, int i) {

        USER_ID = userList.get(i).getUserId();
        USER_FIRSTNAME = userList.get(i).getFirstName();
        USER_LASTNAME = userList.get(i).getLastName();
        USER_USERNAME = USER_FIRSTNAME+" "+USER_LASTNAME;
        USER_EMAIL = userList.get(i).getEmail();
        USER_PROFILE = userList.get(i).getProfilePic();

        if (userList.size() == i + 1) {
            USER_ID = userList.get(i).getUserId();
            USER_EMAIL = userList.get(i).getEmail();
            USER_PROFILE = userList.get(i).getProfilePic();
            if (storeRealm()) {
                sharedPrefrenceHelper.saveString("USER_ID", userList.get(i).getUserId());
                sharedPrefrenceHelper.saveString("USER_FIRSTNAME", userList.get(i).getFirstName());
                sharedPrefrenceHelper.saveString("USER_LASTNAME", userList.get(i).getLastName());
                sharedPrefrenceHelper.saveString("USER_USERNAME", USER_USERNAME);
                sharedPrefrenceHelper.saveString("USER_EMAIL", userList.get(i).getEmail());
                sharedPrefrenceHelper.saveString("USER_PROFILE", userList.get(i).getProfilePic());

                Intent intent = new Intent(WelcomeActivity.this, Traveller_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.anim_left_to_right);
                finish();
                Toast.makeText(WelcomeActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean storeRealm() {
        RealmLogin realmResults;
        realm.beginTransaction();
        realmResults = realm.where(RealmLogin.class).findFirst();
        if (realmResults != null) {
            Log.i(TAG, "storeRealm: realmResults " + realmResults.getUserID());
            Log.i(TAG, "storeRealm: USER_ID " + USER_EMAIL);
            if (!realmResults.getUserID().equals(USER_EMAIL)) {
                realm.commitTransaction();
                return true;
            }
        } else {
            RealmLogin realmLoginClass = new RealmLogin();
            realmLoginClass.setUserID(USER_ID);
            realmLoginClass.setUserName(USER_USERNAME);
            realmLoginClass.setUserEmail(USER_EMAIL);
            realm.copyToRealmOrUpdate(realmLoginClass);
            realmResults = realm.where(RealmLogin.class).findFirst();
            Log.d(TAG, "realmLogin: movie.getUserID() " + realmResults.getUserID());
            Log.d(TAG, "realmLogin: movie.getUserEmail() " + realmResults.getUserEmail());
            realm.commitTransaction();
            return true;
        }
        realm.commitTransaction();
        return false;
    }

    private void displayMessage(Profile profile) {
        if (profile != null) {
            fbFullName = profile.getName();
            fbFirstName = profile.getFirstName();
            fbLastName = profile.getLastName();
            fbID = profile.getId();
            fbUserProfile = profile.getProfilePictureUri(100, 100).toString();

            Log.i(TAG, "displayMessage: fbFullName " + fbFullName);
            Log.i(TAG, "displayMessage: fbFirstName " + fbFirstName);
            Log.i(TAG, "displayMessage: fbLastName " + fbLastName);
            Log.i(TAG, "displayMessage: fbUserProfile " + fbUserProfile);
            Log.i(TAG, "displayMessage: fbEmail " + fbEmail);
        }
    }

    public void setupFont() {
        tittle.setTypeface(fontUtility.getLatoRegular());
        login.setTypeface(fontUtility.getLatoRegular());
        tearms.setTypeface(fontUtility.getLatoRegular());
        btncreate_account.setTypeface(fontUtility.getLatoRegular());
        facebook.setTypeface(fontUtility.getLatoRegular());
    }

    private void getHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    WelcomeActivity.this.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HashKey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Connecting..");
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            if (!isFinishing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
