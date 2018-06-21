package com.cog.Dropinn.both.SignInSignUp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.Models.RetrofitArrayAPI;
import com.cog.Dropinn.Models.SignupModel;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Static.Constants;
import com.cog.Dropinn.Traveller.Activity.Traveller_Activity;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

import java.io.EOFException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupDob extends AppCompatActivity {
    FontUtility fontUtility;
    private TextView when_your_birthday;
    private TextView birthdayDesc;
    private TextView tvBirthday;
    private EditText edBirthday;
    private ImageView ivBack;
    private ImageButton ivGoNext;
    int mYear,mMonth,mDay;
    private ScrollView contentlayout;
    private String TAG = Login.class.getSimpleName();
    ArrayList<String> SIGNUP_ARRAY = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private SharedPrefrenceHelper sharedPrefrenceHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_calendar);
        fontUtility = new FontUtility(SignupDob.this);
        sharedPrefrenceHelper = new SharedPrefrenceHelper(SignupDob.this);
        when_your_birthday = (TextView) findViewById(R.id.when_your_birthday); //BIG
        birthdayDesc = (TextView) findViewById(R.id.birthdayDesc); //Regular
        tvBirthday = (TextView) findViewById(R.id.tvBirthday); //BIG
        edBirthday = (EditText) findViewById(R.id.edBirthday); //Regular
        ivGoNext = (ImageButton) findViewById(R.id.ivGoNext);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        contentlayout=(ScrollView)findViewById(R.id.contentlayout);

        if(sharedPrefrenceHelper.getString("DOB")!=null){
            edBirthday.setText(sharedPrefrenceHelper.getString("DOB"));
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(edBirthday.getText().toString().length()!=0)
                sharedPrefrenceHelper.saveString("DOB",edBirthday.getText().toString());
            }
        });
        edBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender();
            }
        });
        edBirthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Age age = calculateAge(new Date(edBirthday.getText().toString()));
                System.out.println("age is greter than 17==>"+calculateAge(new Date(edBirthday.getText().toString())));
                if(edBirthday.length()==0 ){
                    onTextChangedlistner();
                }
                if( age.getYears() < 18) {
                    snackbar();
                    onTextChangedlistner();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Age age = calculateAge(new Date(edBirthday.getText().toString()));
                System.out.println("age is greter than 18==>"+calculateAge(new Date(edBirthday.getText().toString())));
                if(edBirthday.length()!=0 && age.getYears() >= 18){
                    aftertextchangedlistner();
                }
//                if(age.getYears() >= 18){
//
//
//                }
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
                sharedPrefrenceHelper.saveString("email",null);
                sharedPrefrenceHelper.saveString("password",null);
                sharedPrefrenceHelper.saveString("DOB",null);
                doSignup(SIGNUP_ARRAY);
            }
        });
    }

    void doSignup(ArrayList<String> SIGNUP_ARRAY) {
        String firstName = SIGNUP_ARRAY.get(0) == null ? "" : SIGNUP_ARRAY.get(0);
        String lastName = SIGNUP_ARRAY.get(1) == null ? "" : SIGNUP_ARRAY.get(1);
        String emailID = SIGNUP_ARRAY.get(2) == null ? "" : SIGNUP_ARRAY.get(2);
        String password = SIGNUP_ARRAY.get(3) == null ? "" : SIGNUP_ARRAY.get(3);
        String JoinDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/"
                + Calendar.getInstance().get(Calendar.MONTH) + 1 + "/"
                + Calendar.getInstance().get(Calendar.YEAR);

        Log.i(TAG, "doSignup: firstName " + firstName);
        Log.i(TAG, "doSignup: lastName " + lastName);
        Log.i(TAG, "doSignup: emailID " + emailID);
        Log.i(TAG, "doSignup: password " + password);
        Log.i(TAG, "doSignup: JoinDate " + JoinDate);

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
        Call<List<SignupModel>> call = service.doSignup(firstName, lastName, emailID, password, JoinDate);
        Log.d(TAG, "doSignup: URL " + call.request().url().toString());

        call.enqueue(new Callback<List<SignupModel>>() {
            @Override
            public void onResponse(Call<List<SignupModel>> call, retrofit2.Response<List<SignupModel>> response) {
                try {
                    List<SignupModel> RequestData = response.body();
                    if (response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + RequestData.toString());
                        if (RequestData != null) {
                            List<SignupModel> userList = response.body();
                            for (int i = 0; i < userList.size(); i++) {
                                String strSuccess = userList.get(i).getSuccess();
                                Log.i(TAG, "onResponse: strStatus " + strSuccess);
                                if (strSuccess != null) {
                                    if (strSuccess.equals("1")) { //NoEmailExist
                                        dismissDialog();
                                        processNext();
                                    } else if (strSuccess.equals("0")) { //WhenEmailExist
                                        dismissDialog();
                                        Toast.makeText(SignupDob.this, "EMAIL ALREADY EXIST", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignupDob.this, "SIGNUP FAILED UNEXPECTED ERROR", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SignupDob.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<List<SignupModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                if (t instanceof EOFException) {
                    Toast.makeText(SignupDob.this, "EXECUTION FAILED - NO DATA", Toast.LENGTH_SHORT).show();
                } else if (t instanceof com.google.gson.stream.MalformedJsonException) {
                    Toast.makeText(SignupDob.this, "URL IN ERROR STATE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupDob.this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
                dismissDialog();
            }
        });
    }

    private void processNext() {
        Intent startActivity = new Intent(SignupDob.this, Traveller_Activity.class);
        startActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startActivity);
        overridePendingTransition(R.anim.anim_right_to_left, R.anim.stay);
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

    public void calender(){

        contentlayout.setBackground( getResources().getDrawable(R.drawable.forgotpassword_bg) );
        final Calendar mcurrentDate = Calendar.getInstance();
       if(edBirthday.getText().toString().length()!=0 ) {
           String birthday = edBirthday.getText().toString();
           StringTokenizer tokens = new StringTokenizer(birthday, "/");
           String day = tokens.nextToken().trim();
           String month = tokens.nextToken().trim();
           String year = tokens.nextToken().trim();
           Log.i(TAG, "calender selected date==>" + day + " " + month + " " + year);
           mYear=Integer.parseInt(year);
           mMonth=Integer.parseInt(month)-1;
           mDay=Integer.parseInt(day);
       }
       else {
            mYear = mcurrentDate.get(Calendar.YEAR);
            mMonth = mcurrentDate.get(Calendar.MONTH);
            mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        }

        DatePickerDialog mDatePicker = new DatePickerDialog(
                SignupDob.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker,
                                  int selectedyear, int selectedmonth,
                                  int selectedday) {
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH,
                        selectedday);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                        Locale.US);
                System.out.println("age(1)==>"+mcurrentDate.getTime());
                edBirthday.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDatePicker.show();

    }

    public void snackbar(){
        contentlayout.setBackground(getDrawable(R.drawable.bg_error) );
        Snackbar snackbar = Snackbar
                .make(contentlayout,R.string.invalidbirthdate, Snackbar.LENGTH_LONG)
                .setAction("change", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contentlayout.setBackground(getDrawable(R.drawable.forgotpassword_bg) );
                        calender();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(getColor(R.color.appColor));
        snackbar.getView().setBackgroundColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);

        snackbar.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        SIGNUP_ARRAY = intent.getStringArrayListExtra("SIGNUP_ARRAY");
        Log.i(TAG, "onStart: " + SIGNUP_ARRAY.size());

        when_your_birthday.setTypeface(fontUtility.getLatoBold());
        birthdayDesc.setTypeface(fontUtility.getLatoRegular());
        tvBirthday.setTypeface(fontUtility.getLatoBold());
        edBirthday.setTypeface(fontUtility.getLatoRegular());
    }

        private static Age calculateAge( Date birthDate)
        {
            int years = 0;
            int months = 0;
            int days = 0;
            //create calendar object for birth day
            Calendar birthDay = Calendar.getInstance();
            birthDay.setTimeInMillis(birthDate.getTime());
            //create calendar object for current day
            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);
            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            int currMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthDay.get(Calendar.MONTH) + 1;
            //Get difference between months
            months = currMonth - birthMonth;
            //if month difference is in negative then reduce years by one and calculate the number of months.
            if (months < 0)
            {
                years--;
                months = 12 - birthMonth + currMonth;
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--;
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            {
                years--;
                months = 11;
            }
            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
            } else
            {
                days = 0;
                if (months == 12)
                {
                    years++;
                    months = 0;
                }
            }
            //Create new Age object
            return new Age(days, months, years);
        }
    }

     class Age
    {
        private int days;
        private int months;
        private int years;

        private Age()
        {
            //Prevent default constructor
        }

        public Age(int days, int months, int years)
        {
            this.days = days;
            this.months = months;
            this.years = years;
        }

        public int getDays()
        {
            return this.days;
        }

        public int getMonths()
        {
            return this.months;
        }

        public int getYears()
        {
            return this.years;
        }

        @Override
        public String toString()
        {
            return years + " Years, " + months + " Months, " + days + " Days";
        }

    }




