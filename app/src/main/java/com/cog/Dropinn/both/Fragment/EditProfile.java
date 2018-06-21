package com.cog.Dropinn.both.Fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.CalligraphyTypefaceSpan;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.TypefaceUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by test on 1/2/18.
 */

public class EditProfile extends Fragment {
    View view;
    private FontUtility fontUtility;
    private AppCompatTextView tvPlace;
    private AppCompatEditText edPlace;
    private AppCompatTextView tvDesc;
    private AppCompatEditText edDesc;
    private AppCompatTextView tvSpokenLanguages;
    private AppCompatEditText edSpokenLanguages;
    private AppCompatImageView ivBack;
    private AppCompatImageView ivCamera;
    private AppCompatImageView ivUserImage;
    private AppCompatTextView tvName;
    private AppCompatEditText edName;
    private AppCompatEditText edEmail;
    private AppCompatTextView tvEmail;
    private AppCompatTextView tvGender;
    private AppCompatEditText tvGenderSelect;
    private AppCompatTextView tvDob;
    private AppCompatEditText tvDobSelect;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_editprofile, container, false);
        fontUtility = new FontUtility(getActivity());
        tvName = (AppCompatTextView) view.findViewById(R.id.tvName);
        edName = (AppCompatEditText) view.findViewById(R.id.edName);

        tvPlace = (AppCompatTextView) view.findViewById(R.id.tvPlace);
        edPlace = (AppCompatEditText) view.findViewById(R.id.edPlace);

        tvGender = (AppCompatTextView) view.findViewById(R.id.tvGender);
        tvGenderSelect = (AppCompatEditText) view.findViewById(R.id.tvGenderSelect);

        tvDob = (AppCompatTextView) view.findViewById(R.id.tvDob);
        tvDobSelect = (AppCompatEditText) view.findViewById(R.id.tvDobSelect);

        tvDesc = (AppCompatTextView) view.findViewById(R.id.tvDesc);
        edDesc = (AppCompatEditText) view.findViewById(R.id.edDesc);

        tvEmail = (AppCompatTextView) view.findViewById(R.id.tvEmail);
        edEmail = (AppCompatEditText) view.findViewById(R.id.edEmail);

        tvSpokenLanguages = (AppCompatTextView) view.findViewById(R.id.tvSpokenLanguages);
        edSpokenLanguages = (AppCompatEditText) view.findViewById(R.id.edSpokenLanguages);

        ivBack = (AppCompatImageView) view.findViewById(R.id.ivBack);
        ivCamera = (AppCompatImageView) view.findViewById(R.id.ivCamera);
        ivUserImage = (AppCompatImageView) view.findViewById(R.id.ivUserImage);

        doProcess();
        return view;
    }

    private void doProcess() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewProfile();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tvGenderSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] photo = {spanText("MALE", fontUtility.getLatoRegular()), spanText("FEMALE", fontUtility.getLatoRegular())};
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(spanText("Select Gender", fontUtility.getLatoRegular()));
                alert.setSingleChoiceItems(photo, -1, new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    tvGenderSelect.setText("MALE");
                                    dialog.dismiss();
                                } else if (which == 1) {
                                    tvGenderSelect.setText("FEMALE");
                                    dialog.dismiss();
                                }
                            }

                        });
                alert.show();
            }
        });

        tvDobSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker = new DatePickerDialog(
                        getActivity(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker,
                                          int selectedyear, int selectedmonth,
                                          int selectedday) {
                        mcurrentDate.set(Calendar.YEAR, selectedyear);
                        mcurrentDate.set(Calendar.MONTH, selectedmonth);
                        mcurrentDate.set(Calendar.DAY_OF_MONTH,
                                selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                                Locale.US);
                        tvDobSelect.setText(sdf.format(mcurrentDate.getTime()));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.show();
            }
        });
    }

    SpannableString spanText(String string, Typeface typeface) {
        SpannableString spannableString = new SpannableString(string);
        CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(typeface);
        spannableString.setSpan(typefaceSpan, 0, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public void onStart() {
        super.onStart();
        tvName.setTypeface(fontUtility.getLatoRegular());
        edName.setTypeface(fontUtility.getLatoRegular());
        tvPlace.setTypeface(fontUtility.getLatoRegular());
        edPlace.setTypeface(fontUtility.getLatoRegular());
        tvDesc.setTypeface(fontUtility.getLatoRegular());
        edDesc.setTypeface(fontUtility.getLatoRegular());
        tvSpokenLanguages.setTypeface(fontUtility.getLatoRegular());
        edSpokenLanguages.setTypeface(fontUtility.getLatoRegular());
        tvDob.setTypeface(fontUtility.getLatoRegular());
        tvDobSelect.setTypeface(fontUtility.getLatoRegular());
        tvEmail.setTypeface(fontUtility.getLatoRegular());
        edEmail.setTypeface(fontUtility.getLatoRegular());
        tvGender.setTypeface(fontUtility.getLatoRegular());
        tvGenderSelect.setTypeface(fontUtility.getLatoRegular());
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
