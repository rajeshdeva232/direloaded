package com.cog.Dropinn.both.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

import java.util.ArrayList;

public class SignupPassword extends AppCompatActivity {
    FontUtility fontUtility;
    private TextView create_a_password;
    private TextView passwordDesc;
    private TextView tvPassword,tv_password;
    private EditText edPassword;
    private  String password;
    Boolean isShowing=false;
    Snackbar snackbar;
    View view;
    ScrollView scroll;
    private ImageView ivBack;
    private ImageButton ivGoNext;
    Boolean isVisible = true;
    private String TAG = Login.class.getSimpleName();
    final String password_patten="((([a-zA-Z]+)([0-9#<!:;\\\\{}.|?,=@$%^&*+(/)>_-]+))|(([0-9#<!:;\\\\{}.|?,=@$%^&*+(/)>_-]+)([a-zA-Z]+)))[a-zA-Z0-9#<!:;\\\\{}.|?,=@$%^&*+(/)>_-]*";//"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%_^()*\\-\\+\\=#?&]{8,}$";
    ArrayList<String> SIGNUP_ARRAY = new ArrayList<String>();
    private SharedPrefrenceHelper sharedPrefrenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_password);
        fontUtility = new FontUtility(SignupPassword.this);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(SignupPassword.this);
        create_a_password = (TextView) findViewById(R.id.create_a_password); //BIG
        passwordDesc = (TextView) findViewById(R.id.passwordDesc); //Regular
        tvPassword = (TextView) findViewById(R.id.tvPassword); //BIG
        edPassword = (EditText) findViewById(R.id.edPassword); //Regular
        ivGoNext = (ImageButton) findViewById(R.id.ivGoNext);
        if(sharedPrefrenceHelper.getString("password")!=null) {
            password=sharedPrefrenceHelper.getString("password");
            edPassword.setText(password);
            edPassword.setSelection(password.length());
            sharedPrefrenceHelper.saveString("password",null);

        }
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tv_password=(TextView)findViewById(R.id.tv_password);
        scroll=(ScrollView) findViewById(R.id.scroll);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(edPassword.getText().toString().length()!=0)
                    sharedPrefrenceHelper.saveString("password",edPassword.getText().toString());
            }
        });

        tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isVisible) {

                    passwordvisible();

                } else {
                    passwordhide();
                }
            }
        });


        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isShowing){
                    snackbar.dismiss();
                    isShowing=false;
                }
                edPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
                if(edPassword.getText().toString().length()<9 ) {// && !edPassword.getText().toString().matches(password_patten))
                    onTextChangedlistner();
                }
            }



            @Override
            public void afterTextChanged(Editable s) {
                if(edPassword.getText().toString().length()>=8){//&& edPassword.getText().toString().matches(password_patten)) {
                    aftertextchangedlistner();
                }else {
                    onTextChangedlistner();
                }
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
        processnext();

    }

    public void processnext(){

        final String firstName = SIGNUP_ARRAY.get(0) == null ? "" : SIGNUP_ARRAY.get(0).trim();
        final String lastName = SIGNUP_ARRAY.get(1) == null ? "" : SIGNUP_ARRAY.get(1).trim();
        String emailID = SIGNUP_ARRAY.get(2) == null ? "" : SIGNUP_ARRAY.get(2).trim();
        final String[] emailid=emailID.split("@");
        emailid[0]=emailid[0].trim();
        System.out.println("email==>"+firstName+" "+lastName+" "+emailid[0]);
        ivGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=edPassword.getText().toString();
                if(edPassword.getText().toString().matches(password_patten)) {

                    if(!password.contains(firstName)&&!password.contains(lastName)&&!password.contains(emailid[0])) {
                        Intent startActivity = new Intent(SignupPassword.this, SignupDob.class);
                        SIGNUP_ARRAY.add(edPassword.getText().toString());
                        startActivity.putExtra("SIGNUP_ARRAY", SIGNUP_ARRAY);
                        startActivity(startActivity);
                        overridePendingTransition(R.anim.anim_right_to_left, R.anim.stay);
                    }
                    else{
                       snackbar_name();
                        edPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.set_error, 0);
                    }

                }
                else{
                    snackbar();
                    edPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.set_error, 0);
                }
            }
        });
    }
    public void snackbar_name() {
        isShowing=true;
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        snackbar = Snackbar.make(scroll,"", Snackbar.LENGTH_INDEFINITE);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(0,0,0,0);
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // Inflate our custom view
        View snackView = getLayoutInflater().inflate(R.layout.snakbar_nameerror, null);
        // Configure the view
        TextView textViewOne = (TextView) snackView.findViewById(R.id.error);

        textViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("One", "First one is clicked");
            }
        });

        ImageView textViewTwo = (ImageView) snackView.findViewById(R.id.close);
        textViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Two", "Second one is clicked");
                snackbar.dismiss();
                isShowing=false;
            }
        });

        // Add the view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams);
        // Show the Snackbar
        snackbar.show();

    }

    public void passwordvisible() {
        tv_password.setText(R.string.hide);
        edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        int position = edPassword.length();
        Editable etext = edPassword.getText();
        Selection.setSelection(etext, position);
        isVisible = false;
    }

    public void passwordhide() {
        tv_password.setText(R.string.show);
        edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        int position = edPassword.length();
        Editable etext = edPassword.getText();
        Selection.setSelection(etext, position);
        isVisible = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        SIGNUP_ARRAY = intent.getStringArrayListExtra("SIGNUP_ARRAY");
        Log.i(TAG, "onStart: "+SIGNUP_ARRAY.size());

        create_a_password.setTypeface(fontUtility.getLatoBold());
        passwordDesc.setTypeface(fontUtility.getLatoRegular());
        tvPassword.setTypeface(fontUtility.getLatoBold());
        edPassword.setTypeface(fontUtility.getLatoRegular());
        tv_password.setTypeface(fontUtility.getLatoRegular());
    }
    public void snackbar()
    {
        // Create the Snackbar
        isShowing=true;
        LinearLayout.LayoutParams objLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        snackbar = Snackbar.make(scroll,"", Snackbar.LENGTH_INDEFINITE);
        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        layout.setPadding(0,0,0,0);
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        LayoutInflater mInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        // Inflate our custom view
        View snackView = getLayoutInflater().inflate(R.layout.snakbar_password, null);
        // Configure the view
        TextView textViewOne = (TextView) snackView.findViewById(R.id.error);

        textViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("One", "First one is clicked");
            }
        });

        ImageView textViewTwo = (ImageView) snackView.findViewById(R.id.close);
        textViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Two", "Second one is clicked");
                snackbar.dismiss();
                isShowing=false;
            }
        });

        // Add the view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams);
        // Show the Snackbar
        snackbar.show();
    }

}
