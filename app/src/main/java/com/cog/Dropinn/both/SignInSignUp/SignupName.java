package com.cog.Dropinn.both.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

import java.util.ArrayList;

public class SignupName extends AppCompatActivity {
    FontUtility fontUtility;
    private TextView tvName;
    private TextView tvFirstName;
    private EditText edFirstName;
    private TextView tvLastName;
    private EditText edLastName;
    private ImageButton ivGoNext;
    private ImageView ivBack;
    private String TAG = Login.class.getSimpleName();
    private SharedPrefrenceHelper sharedPrefrenceHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_name);
        fontUtility = new FontUtility(SignupName.this);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(SignupName.this);
        tvName = (TextView) findViewById(R.id.tvName); //BIG
        tvFirstName = (TextView) findViewById(R.id.tvFirstName); //BIG
        tvLastName = (TextView) findViewById(R.id.tvLastName); //BIG
        ivGoNext = (ImageButton) findViewById(R.id.ivGoNext);
        edFirstName = (EditText) findViewById(R.id.edFirstName);
        edLastName = (EditText) findViewById(R.id.edLastName);
        ivBack = (ImageView) findViewById(R.id.ivBack);

        doProcess();
    }

    public void doProcess() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                sharedPrefrenceHelper.saveString("email",null);
                sharedPrefrenceHelper.saveString("password",null);
                sharedPrefrenceHelper.saveString("DOB",null);

            }
        });

        edFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edFirstName.getText().toString().length() == 0 && edLastName.getText().toString().length() == 0) {
                    onTextChangedlistner();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edFirstName.getText().toString().length() != 0 && edLastName.getText().toString().length() != 0) {
                    aftertextchangedlistner();
                } else {
                    onTextChangedlistner();
                }
            }
        });

        edLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edFirstName.getText().toString().length() == 0 && edLastName.getText().toString().length() == 0) {
                    onTextChangedlistner();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edFirstName.getText().toString().length() != 0 && edLastName.getText().toString().length() != 0) {
                    aftertextchangedlistner();
                } else {
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
        ivGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> stringArray = new ArrayList<String>();
                stringArray.add(edFirstName.getText().toString());
                stringArray.add(edLastName.getText().toString());
                Intent startActivity = new Intent(SignupName.this, SignupEmail.class);
                startActivity.putExtra("SIGNUP_ARRAY", stringArray);
                startActivity(startActivity);
                overridePendingTransition(R.anim.anim_right_to_left, R.anim.stay);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvName.setTypeface(fontUtility.getLatoBold());
        tvFirstName.setTypeface(fontUtility.getLatoBold());
        tvLastName.setTypeface(fontUtility.getLatoBold());
        edFirstName.setTypeface(fontUtility.getLatoRegular());
        edLastName.setTypeface(fontUtility.getLatoRegular());
    }
}




