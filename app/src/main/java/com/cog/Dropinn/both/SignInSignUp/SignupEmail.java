package com.cog.Dropinn.both.SignInSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.Models.EmailExistModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Activity.Traveller_Activity;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupEmail extends AppCompatActivity {
    FontUtility fontUtility;
    private TextView tvName;
    private TextView tvEmail;
    private ImageButton ivGoNext;
    Boolean isShowing=false;
    Snackbar snackbar;
    private String email;
    private EditText edEmail;
    View view;
    private ImageView ivBack;
    private ScrollView contentlayout;
    final String emailPattern ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String TAG = Login.class.getSimpleName();
    ArrayList<String> SIGNUP_ARRAY = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private SharedPrefrenceHelper sharedPrefrenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(SignupEmail.this);
        fontUtility = new FontUtility(SignupEmail.this);
        tvName = (TextView) findViewById(R.id.tvName); //BIG
        tvEmail = (TextView) findViewById(R.id.tvEmail); //BIG
        ivGoNext = (ImageButton) findViewById(R.id.ivGoNext);
        edEmail = (EditText) findViewById(R.id.edEmail);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        contentlayout=(ScrollView)findViewById(R.id.contentlayout);

        doProgress();
    }

    public void doProgress() {


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(edEmail.getText().toString().length()!=0){
                    sharedPrefrenceHelper.saveString("email",edEmail.getText().toString());
                }

            }
        });

        if(sharedPrefrenceHelper.getString("email")!=null){
            email=sharedPrefrenceHelper.getString("email");
            edEmail.setText(email);
            edEmail.setSelection(email.length());
            //aftertextchangedlistner();
            sharedPrefrenceHelper.saveString("email",null);
        }
        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);

                if(isShowing){
                    snackbar.dismiss();
                }

                if (edEmail.getText().toString().length()==0) {
                    onTextChangedlistner();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edEmail.getText().toString().length()!=0)
                    aftertextchangedlistner();
            }
        });
    }

    public void onTextChangedlistner() {
        ivGoNext.setBackground(getDrawable(R.drawable.circular_button));
        ivGoNext.setClickable(false);
        ivGoNext.setElevation(0);

    }

    public void aftertextchangedlistner() {
        ivGoNext.setBackground(getDrawable(R.drawable.circular_btnlogin));
        ivGoNext.setClickable(true);
        ivGoNext.setElevation(8);
        ivGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edEmail.getText().toString().matches(emailPattern)) {
                    isEmailExists(edEmail.getText().toString());
                }else {
                    snackbar();
                    edEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.set_error, 0);
                }
            }
        });

    }

    void isEmailExists(String userEmail) {
        showDialog();
        Retrofit retrofit = null;
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.EMAIL_EXIST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            dismissDialog();
            Toast.makeText(this, "LIVE URL NOT VALID", Toast.LENGTH_SHORT).show();
        }
        RetrofitArrayAPI service = retrofit.create(RetrofitArrayAPI.class);
        Call<List<EmailExistModel>> call = service.isEmailExist(userEmail);
        Log.d(TAG, "isEmailExists: URL" + call.request().url().toString());

        call.enqueue(new Callback<List<EmailExistModel>>() {
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
                                        dismissDialog();
                                        processNext();
                                    } else if (strStatus.equals("Fail")) { //WhenEmailExist
                                        dismissDialog();
                                        //snackbar();
                                        Intent startActivity = new Intent(SignupEmail.this, Login.class);
                                        startActivity.putExtra("Emailalredyexist",edEmail.getText().toString());
                                        startActivity(startActivity);
                                        Toast.makeText(SignupEmail.this, "EMAIL ALREADY EXIST", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            dismissDialog();
                            Toast.makeText(getApplicationContext(), "EMPTY RESPONSE", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        dismissDialog();
                        Log.e(TAG, "onResponse: Fail" + response.message());
                        Toast.makeText(SignupEmail.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<EmailExistModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(SignupEmail.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(SignupEmail.this, "URL IN ERROR STATE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupEmail.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
                dismissDialog();
            }
        });
    }

    private void processNext() {
        Intent startActivity = new Intent(SignupEmail.this, SignupPassword.class);
        SIGNUP_ARRAY.add(edEmail.getText().toString());
        startActivity.putExtra("SIGNUP_ARRAY", SIGNUP_ARRAY);
        if (SIGNUP_ARRAY.isEmpty() || SIGNUP_ARRAY.size() ==0 ) {
            Log.i(TAG, "processNext: Array is empty");
        }
        else
        {
            startActivity(startActivity);
            overridePendingTransition(R.anim.anim_right_to_left, R.anim.stay);
        }

    }

    public void showDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Processing..");
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
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        SIGNUP_ARRAY = intent.getStringArrayListExtra("SIGNUP_ARRAY");
        Log.i(TAG, "onStart: " + SIGNUP_ARRAY.size());

        tvName.setTypeface(fontUtility.getLatoBold());
        tvEmail.setTypeface(fontUtility.getLatoBold());
        edEmail.setTypeface(fontUtility.getLatoRegular());
    }

    public void snackbar(){
        isShowing=true;
        snackbar = Snackbar
                .make(contentlayout,R.string.emailerror, Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();

    }
}
