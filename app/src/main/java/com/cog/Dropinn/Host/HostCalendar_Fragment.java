package com.cog.Dropinn.Host;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.FontUtility;
import com.cog.Dropinn.Utils.timessquare.CalendarCellDecorator;
import com.cog.Dropinn.Utils.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HostCalendar_Fragment extends Fragment {

    private View view;
    private FontUtility fontUtility;
    private TextView tvWhen_you_publish;
    private TextView tvCalendar;
    private ProgressDialog progressDialog;

    private CalendarPickerView calendar;
    boolean isSelecteable;
    Calendar cal = Calendar.getInstance();
    public ArrayList<String> rDates = new ArrayList<String>();
    ArrayList<String> selectedDates = new ArrayList<String>();
    ArrayList<String> rangedates = new ArrayList<>();
    ArrayList<Date> highlighteddates = new ArrayList<>();
    List<CalendarCellDecorator> decoratorList = new ArrayList<>();
    List<String> monthsList = new ArrayList<String>();
    List<String> daysList = new ArrayList<String>();
    List<String> checkInDates = new ArrayList<String>();
    List<String> checkOutDates = new ArrayList<String>();
    private int CALENDAR_REQ_CODE_IN_DETAIL = 1;
    private int CALENDAR_REQ_CODE_IN_BOOKIT = 2;
    private Button saveButton;
    private TextView checkInText;
    private TextView checkOutText;
    private TextView clearDatesText;
    private ImageButton buttonClose;
    private String isFrom="NO";
    private String txtCheckin1;
    private String txtCheckout1;
    private int count = 0;

    public HostCalendar_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_host_calendar, container, false);
        fontUtility = new FontUtility(getActivity());
        tvCalendar = (TextView) view.findViewById(R.id.tvCalendar);
        tvWhen_you_publish = (TextView) view.findViewById(R.id.tvWhen_you_publish);
        doProcess();

        return view;
    }

    private void doProcess() {
    }

    @Override
    public void onStart() {
        super.onStart();
        /*tvCalendar.setTypeface(fontUtility.getLatoBold());
        tvWhen_you_publish.setTypeface(fontUtility.getLatoRegular());*/
    }
}
