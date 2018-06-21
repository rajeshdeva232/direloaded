package com.cog.Dropinn.both.SignInSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.cog.Dropinn.Models.ForgotPasswordModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Models.RetrofitArrayAPI;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Forgot_password extends AppCompatActivity {

    ImageView backbtn;
    EditText email;
    ImageButton submit;
    final String emailPattern ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String TAG = Login.class.getSimpleName();
    ArrayList<String> SIGNUP_ARRAY = new ArrayList<String>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        backbtn = (ImageView) findViewById(R.id.back_btn);
        email = (EditText) findViewById(R.id.edEmail);
        submit = (ImageButton) findViewById(R.id.login_btn);
        doProcess();
    }

    public void doProcess() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Forgot_password.this, Login.class);
                startActivity(i);
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!email.getText().toString().matches(emailPattern)) {
                    submit.setBackground(getDrawable(R.drawable.circular_button));
                    submit.setClickable(false);
                    submit.setElevation(0);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (email.getText().toString().matches(emailPattern)) {
                    submit.setBackground(getDrawable(R.drawable.circular_btnlogin));
                    submit.setClickable(true);
                    submit.setElevation(8);
                    forgotpassword();
                }

            }
        });


    }

    void sentForgotPassword(String userEmail) {
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
        Call<List<ForgotPasswordModel>> call = service.sentForgotPassword(userEmail);
        Log.d(TAG, "isEmailExists: URL" + call.request().url().toString());

        call.enqueue(new Callback<List<ForgotPasswordModel>>() {
            @Override
            public void onResponse(Call<List<ForgotPasswordModel>> call, retrofit2.Response<List<ForgotPasswordModel>> response) {
                try {
                    List<ForgotPasswordModel> RequestData = response.body();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + RequestData.toString());

                        if (RequestData != null) {
                            List<ForgotPasswordModel> userList = response.body();
                            for (int i = 0; i < userList.size(); i++) {
                                String strStatus = userList.get(i).getStatus();
                                if (strStatus != null) {
                                    if (strStatus.equals("Mail successfully sent.")) { //NoEmailExist
                                        dismissDialog();
                                        Toast.makeText(Forgot_password.this, "Successfully sent reset password link to your mail", Toast.LENGTH_SHORT).show();
                                    } else if (strStatus.equals("no data")) { //WhenEmailExist
                                        dismissDialog();
                                        Toast.makeText(Forgot_password.this, "EMAIL NOT REGISTERED", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Forgot_password.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<ForgotPasswordModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(Forgot_password.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(Forgot_password.this, "URL IN ERROR STATE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Forgot_password.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
                dismissDialog();

            }
        });
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Sending ..");
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
    }

    public void forgotpassword(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentForgotPassword(email.getText().toString());
            }
        });
    }
}
