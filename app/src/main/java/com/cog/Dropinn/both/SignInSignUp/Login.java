package com.cog.Dropinn.both.SignInSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.Models.LoginModel;
import com.cog.Dropinn.Models.RealmLogin;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Activity.Traveller_Activity;
import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.Utils.RealmController;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;
import com.cog.Dropinn.both.UI.WelcomeActivity;

import java.io.EOFException;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login extends AppCompatActivity {

    Snackbar snackbar;
    TextView forgotpassword;
    ImageButton loginbtn;
    ImageView backbtn;
    private EditText edEmail;
    private String TAG = Login.class.getSimpleName();
    TextView tvShowPassword;
    Boolean isVisible = true;
    final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private ProgressDialog progressDialog;
    private EditText edPassword;
    RelativeLayout layoutContent;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private Realm realm;
    private String USER_USERNAME;
    private String USER_EMAIL;
    private String USER_ID;
    private String USER_PROFILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgotpassword = (TextView) findViewById(R.id.forgot_txt);
        loginbtn = (ImageButton) findViewById(R.id.login_btn);
        backbtn = (ImageView) findViewById(R.id.back_btn);
        tvShowPassword = (TextView) findViewById(R.id.tv_password);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.password_et);
        layoutContent = (RelativeLayout) findViewById(R.id.content_layout);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(Login.this);
        this.realm = RealmController.with(this).getRealm();

        try {
            if (!getIntent().getStringExtra("Emailalredyexist").equals(null)) {
                edEmail.setText(getIntent().getStringExtra("Emailalredyexist"));
                edPassword.requestFocus();
                edEmail.setEnabled(false);

            }
        } catch (NullPointerException e) {
            System.out.println("error==>" + e);
        }
        doProcess();
    }

    public void doProcess() {

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Forgot_password.class);
                startActivity(i);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, WelcomeActivity.class);
                startActivity(i);
            }
        });

        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edEmail.getText().toString().matches(emailPattern) && edPassword.getText().toString().length() < 9) {
                    loginbtn.setBackground(getDrawable(R.drawable.circular_button));
                    loginbtn.setClickable(false);
                    loginbtn.setElevation(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edEmail.getText().toString().matches(emailPattern) && edPassword.getText().toString().length() >= 8) {
                    loginbtn.setBackground(getDrawable(R.drawable.circular_btnlogin));
                    loginbtn.setClickable(true);
                    loginbtn.setElevation(8);
                } else {
                    loginbtn.setBackground(getDrawable(R.drawable.circular_button));
                    loginbtn.setClickable(false);
                    loginbtn.setElevation(0);
                }
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edEmail.getText().toString().matches(emailPattern) && edPassword.getText().toString().length() < 9) {
                    loginbtn.setBackground(getDrawable(R.drawable.circular_button));
                    loginbtn.setClickable(false);
                    loginbtn.setElevation(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edEmail.getText().toString().matches(emailPattern) && edPassword.getText().toString().length() >= 8) {
                    loginbtn.setBackground(getDrawable(R.drawable.circular_btnlogin));
                    loginbtn.setClickable(true);
                    setLoginbtn();
                    loginbtn.setElevation(8);
                } else {
                    loginbtn.setBackground(getDrawable(R.drawable.circular_button));
                    loginbtn.setClickable(false);
                    loginbtn.setElevation(0);
                }
            }
        });

        tvShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Working");

                if (isVisible) {

                    passwordvisible();

                } else {

                    passwordhide();

                }
            }
        });
    }

    void doLogin(String email_id, String password) {
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
        Call<List<LoginModel>> call = service.doLogin(email_id, password);
        Log.d(TAG, "doLogin() called with: email_id = [" + email_id + "], password = [" + password + "] " + call.request().url().toString());

        call.enqueue(new Callback<List<LoginModel>>() {
            @Override
            public void onResponse(Call<List<LoginModel>> call, retrofit2.Response<List<LoginModel>> response) {
                Log.i(TAG, "onResponse: isSuccessful" + response.message());
                try {
                    if (response.isSuccessful()) {
                        List<LoginModel> RequestData = response.body();
                        if (RequestData != null) {
                            List<LoginModel> userList = response.body();
                            Log.i(TAG, "onResponse: userList.size " + userList.size());
                            for (int i = 0; i < userList.size(); i++) {
                                String strStatus = userList.get(i).getStatus();
                                if (strStatus != null) {
                                    if (strStatus.equals("Successfully logged in.")) {
                                        dismissDialog();
                                        processNext(userList, i);
                                    } else {
                                        dismissDialog();
                                        //Toast.makeText(Login.this, "CREDENTIAL NOT VALID", Toast.LENGTH_SHORT).show();
                                        snackbar();
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
                        Toast.makeText(Login.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    dismissDialog();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<LoginModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(Login.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
                dismissDialog();
            }
        });
    }

    private void processNext(List<LoginModel> userList, int i) {
        if (userList.size() == i + 1) {
            USER_ID = userList.get(i).getUserId();
            USER_EMAIL = userList.get(i).getEmail();
            USER_USERNAME = userList.get(i).getUsername();
            USER_PROFILE = userList.get(i).getProfilePic();
            if (storeRealm()) {
                sharedPrefrenceHelper.saveString("USER_ID", userList.get(i).getUserId());
                sharedPrefrenceHelper.saveString("USER_EMAIL", userList.get(i).getEmail());
                sharedPrefrenceHelper.saveString("USER_USERNAME", userList.get(i).getUsername());
                sharedPrefrenceHelper.saveString("USER_PROFILE", userList.get(i).getProfilePic());

                Log.i(TAG, "Login_Shared: USER_ID " + sharedPrefrenceHelper.getString("USER_ID"));
                Log.i(TAG, "Login_Shared: USER_EMAIL " + sharedPrefrenceHelper.getString("USER_EMAIL"));
                Log.i(TAG, "Login_Shared: USER_USERNAME " + sharedPrefrenceHelper.getString("USER_USERNAME"));
                Log.i(TAG, "Login_Shared: USER_PROFILE " + sharedPrefrenceHelper.getString("USER_PROFILE"));

                Intent intent = new Intent(Login.this, Traveller_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.anim_left_to_right);
                finish();
                Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
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

    public void showDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating");
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

    public void setLoginbtn() {

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(Login.this, Traveller_Activity.class);
                startActivity(i);*/
                doLogin(edEmail.getText().toString(), edPassword.getText().toString());
            }
        });

    }

    public void snackbar() {
        snackbar = Snackbar
                .make(layoutContent, R.string.notvalid, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.show_pass, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        passwordvisible();
                        snackbar.dismiss();
                    }
                });

        // Changing message text color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snackbar.setActionTextColor(getColor(R.color.appColor));
        }
        snackbar.getView().setBackgroundColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public void passwordvisible() {
        tvShowPassword.setText(R.string.hide);
        edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        int position = edPassword.length();
        Editable etext = edPassword.getText();
        Selection.setSelection(etext, position);
        isVisible = false;
    }

    public void passwordhide() {
        tvShowPassword.setText(R.string.show);
        edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        int position = edPassword.length();
        Editable etext = edPassword.getText();
        Selection.setSelection(etext, position);
        isVisible = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Realm.getDefaultInstance();
    }

    @Override
    protected void onStop() {
        super.onStop();
        realm.close();
    }
}
