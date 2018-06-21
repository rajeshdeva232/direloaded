package com.cog.Dropinn.Host;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.Models.RealmLogin;
import com.cog.Dropinn.R;
import com.cog.Dropinn.Traveller.ProfileTraveller;
import com.cog.Dropinn.Utils.RealmController;
import com.cog.Dropinn.both.UI.WelcomeActivity;
import com.cog.Dropinn.Utils.SharedPrefrenceHelper;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings_fragment extends Fragment {

    private int selectItem = -1;
    private TextView tvCurrencySymbol;
    private RelativeLayout layout_currency;
    private String[] currency_name_list;
    private AppCompatImageView ivBack;
    private SharedPrefrenceHelper sharedPrefrenceHelper;
    private TextView tvLogout;
    private Realm realm;

    public Settings_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_fragment, container, false);
        this.realm = RealmController.with(this).getRealm();
        sharedPrefrenceHelper = new SharedPrefrenceHelper(getActivity());

        ivBack = (AppCompatImageView) view.findViewById(R.id.ivBack);
        tvCurrencySymbol = (TextView) view.findViewById(R.id.tvCurrencySymbol);
        layout_currency = (RelativeLayout) view.findViewById(R.id.layout_currency);
        currency_name_list = getActivity().getResources().getStringArray(R.array.currency_name_list);
        tvLogout = (TextView) view.findViewById(R.id.tvLogout);

        doProcess();
        return view;
    }

    private void doProcess() {
        layout_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCurrency();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPrefrenceHelper.getString("CURRENT_USER").equals("TRAVELLER")) {
                    changeFragment(new ProfileTraveller(), false);
                } else if (sharedPrefrenceHelper.getString("CURRENT_USER").equals("HOST")) {
                    changeFragment(new Profile_Host(), false);
                } else {
                    Toast.makeText(getActivity(), "CAN'T FIND USERTYPE", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final AlertDialog.Builder builder;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog);
//                } else {
//                    builder = new AlertDialog.Builder(getActivity());
//                }
//                builder.setTitle("Are you sure you want to logout?")
//                        .setMessage("Are you sure you want to logout?")
//                        .setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // continue with delete
//                                dialog.cancel();
//                                Intent i = new Intent(getActivity(), WelcomeActivity.class);
//                                startActivity(i);
//                                getActivity().finish();
//
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                                dialog.cancel();
//                            }
//                        })
//                        .show();
                logoutDialog();

            }
        });
    }

    void selectCurrency() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Select Currency");
        builder.setSingleChoiceItems(currency_name_list, selectItem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String str = currency_name_list[item];
                String[] splitStr = str.split("\\s+");
                String selectedCurrency = splitStr[0];
                tvCurrencySymbol.setText(selectedCurrency);
                selectItem = item;
                dialog.cancel();
            }
        });
        builder.show();
    }

    void changeFragment(Fragment fragment, boolean addBacktoStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        //ft.setTransition(transition);
        if (addBacktoStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    public void logoutDialog() {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View deleteDialogView = factory.inflate(R.layout.logout_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(getContext()).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();
                RealmLogout();
            }
        });
        deleteDialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();

    }

    void RealmLogout() {
        realm.beginTransaction();
        realm.clear(RealmLogin.class);
        realm.commitTransaction();
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
