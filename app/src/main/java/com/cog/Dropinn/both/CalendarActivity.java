package com.cog.Dropinn.both;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cog.Dropinn.R;
import com.cog.Dropinn.Utils.timessquare.CalendarCellDecorator;
import com.cog.Dropinn.Utils.timessquare.CalendarCellView;
import com.cog.Dropinn.Utils.timessquare.CalendarPickerView;
import com.cog.Dropinn.Utils.timessquare.DefaultDayViewAdapter;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class CalendarActivity extends AppCompatActivity {


    private static final String TAG = "CalendarActivity";
    private CalendarPickerView calendar;
    private AlertDialog theDialog;
    public Button saveButton;
    public String strRoomId, strMinStay, strMaxStay, isFrom;
    ImageButton buttonClose;
    public long longMinDay, longMaxDay;
    TextView checkInText, checkOutText, clearDatesText;
    Calendar cal = Calendar.getInstance();
    public ArrayList<String> rDates = new ArrayList<String>();

    ArrayList<String> selectedDates = new ArrayList<String>();

    ArrayList<String> rangedates = new ArrayList<>();
    ArrayList<Date> highlighteddates = new ArrayList<>();
    List<CalendarCellDecorator> decoratorList = new ArrayList<>();
    boolean isSelecteable;
    Typeface typeface;
    List<String> monthsList = new ArrayList<String>();
    List<String> daysList = new ArrayList<String>();
    List<String> checkInDates = new ArrayList<String>();
    List<String> checkOutDates = new ArrayList<String>();
    private int CALENDAR_REQ_CODE_IN_DETAIL = 1;
    private int CALENDAR_REQ_CODE_IN_BOOKIT = 2;
    ProgressDialog progressDialog;
    int count = 0;
    String txtCheckIn, txtCheckOut, txtCheckin1, txtCheckout1;
    String strCheckInDate, strCheckOutDate, strGuests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosable_calendar);

        Intent intent = getIntent();
        rDates = intent.getStringArrayListExtra("listdates");
        // txtCheckIn=intent.getStringExtra("checkinstr");
        // txtCheckOut=intent.getStringExtra("checkoutstr");
        System.out.println("Registereddate" + rDates);
        strMinStay = intent.getStringExtra("min_stay");
        strMaxStay = intent.getStringExtra("max_stay");
        strRoomId = intent.getStringExtra("room_id");
        isFrom = intent.getStringExtra("isfrom");
        strGuests = intent.getStringExtra("capacity");

        if (isFrom.equals("book_it")) {
            strCheckInDate = intent.getStringExtra("checkin");
            strCheckOutDate = intent.getStringExtra("checkout");
            txtCheckin1 = intent.getStringExtra("txtCheckin");
            txtCheckout1 = intent.getStringExtra("txtCheckout");
            // checkInDates=intent.getcheckInText.getText().toString(
            System.out.println("checkinstr=" + strCheckInDate);
            System.out.println("checkoutstr" + strCheckOutDate);
            highlighteddates = getDate(strCheckInDate, strCheckOutDate);
            System.out.println("Highlighteddates" + highlighteddates);
        }
        //Get List of Months
        String[] months = new DateFormatSymbols().getShortMonths();
        for (int i = 0; i < months.length; i++) {
            String month = months[i];
            System.out.println("month = " + month);
            monthsList.add(months[i]);
        }

        //Get List of Days
        String[] days = new DateFormatSymbols().getWeekdays();
        for (int i = 0; i < days.length; i++) {
            String day = days[i];
            System.out.println("day= " + day);
            daysList.add(days[i]);
        }

        //Removing all empty elements from Array
        daysList.removeAll(Arrays.asList("", null));

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        saveButton = (Button) findViewById(R.id.done_button);
        checkInText = (TextView) findViewById(R.id.check_in_date);
        checkOutText = (TextView) findViewById(R.id.check_out_date);
        clearDatesText = (TextView) findViewById(R.id.clear_dates);
        buttonClose = (ImageButton) findViewById(R.id.button_close);
        /*longMinDay = Long.parseLong(strMinStay);
        longMaxDay = Long.parseLong(strMaxStay);*/

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);


        calendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {

                isSelecteable = true;
                cal.setTime(date);

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String result = format.format(date);

                if (rDates != null) {
                    if (rDates.contains(result)) {
                        isSelecteable = false;
                        System.out.println("Inside IF " + isSelecteable);
                        System.out.println("Registered Date" + rDates);
                    } else {
                        isSelecteable = true;
                    }
                }
                return isSelecteable;
            }
        });

        calendar.setCustomDayView(new DefaultDayViewAdapter());
        Calendar today = Calendar.getInstance();
        ArrayList<Date> dates = new ArrayList<Date>();
        today.add(Calendar.DATE, 3);
        dates.add(today.getTime());
        today.add(Calendar.DATE, 5);
        dates.add(today.getTime());
        if (isFrom.equals("book_it")) {

            calendar.init(new Date(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withHighlightedDates(highlighteddates);
            try {
                checkInText.setText(txtCheckin1);
                checkOutText.setText(txtCheckout1);

                for (int x = 0; x < highlighteddates.size(); x++) {
                    decoratorList.add(new EventDecorator(highlighteddates, R.color.colorWhite));
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            // new EventDecorator(getApplicationContext(),highlighteddates.get(0),highlighteddates.get(highlighteddates.size()-1));
            calendar.setDecorators(decoratorList);
            // calendar.clearHighlightedDates();
            //highlighteddates.clear();
        } else {
            calendar.init(new Date(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE);
            //decoratorList.clear();
        }


        calendar.setTypeface(typeface);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {

            public void onDateSelected(Date date) {

                if (calendar.getSelectedDates().size() != 0) {
                    checkInText.setText("Check-in");
                    checkOutText.setText("Checkout");
                    String checkIn = calendar.getSelectedDates().get(0).getDate() + "-" + (calendar.getSelectedDates().get(0).getMonth() + 1) + "-" + (1900 + (calendar.getSelectedDates().get(0).getYear()));
                    if (isFrom.equals("book_it")) {
                        if (count == 0) {
                            calendar.init(new Date(), nextYear.getTime()) //
                                    .inMode(CalendarPickerView.SelectionMode.RANGE);
                        }

                        count = 1;
                    }

                    /*if (calendar.getSelectedDates().size() > 1) {
                        String checkOut = calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getDate() + "-" + (calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getMonth() + 1) + "-" + (1900 + (calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getYear()));
                        rangedates = getDates(checkIn, checkOut);


                        if (!Collections.disjoint(rDates, rangedates)) {
                            Toast.makeText(CalendarActivity.this, "Those Dates are already booked", Toast.LENGTH_SHORT).show();
                            calendar.init(new Date(), nextYear.getTime()) //
                                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                            ;
                            checkInText.setText("Check-in");
                            checkOutText.setText("Checkout");
                        }
                        rangedates.clear();
                    }*/
                }

                if (calendar.getSelectedDates().size() != 0) {
                    checkInText.setText(String.valueOf(daysList.get(calendar.getSelectedDates().get(0).getDay()) + "\n" + monthsList.get(calendar.getSelectedDates().get(0).getMonth()) + " " + calendar.getSelectedDates().get(0).getDate()));
                    if (calendar.getSelectedDates().size() > 1) {
                        checkOutText.setText(String.valueOf(daysList.get(calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getDay()) + "\n" + monthsList.get(calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getMonth()) + " " + calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getDate()));
                    }
                }

            }

            @Override
            public void onDateUnselected(Date date) {
                //Clearing the Checkout Date
                checkOutText.setText("Checkout");
            }
        });


        clearDatesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.init(new Date(), nextYear.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.RANGE);
                checkInText.setText("Check-in");
                checkOutText.setText("Checkout");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInText.getText().toString().matches("Check-in")) {
                    Toast.makeText(CalendarActivity.this, "Enter your Check-in date", Toast.LENGTH_SHORT).show();
                } else if (checkOutText.getText().toString().matches("Checkout")) {

                    Toast.makeText(CalendarActivity.this, "Enter your Checkout date", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Check-in date:" + checkInText.getText().toString());
                    System.out.println("Checkout date:" + checkOutText.getText().toString());

                    String strCheckin = checkInText.getText().toString();
                    String strCheckout = checkOutText.getText().toString();

                    strCheckin = strCheckin.replaceAll(" ", "%20");
                    strCheckout = strCheckout.replaceAll(" ", "%20");

                    if (calendar.getSelectedDates().size() > 1) {
                        long difference = Math.abs(calendar.getSelectedDates().get(0).getTime() - calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getTime());
                        long differenceDates = difference / (24 * 60 * 60 * 1000);
                        System.out.println("Difference in Dates" + differenceDates);

                        String checkIn = calendar.getSelectedDates().get(0).getDate() + "-" + (calendar.getSelectedDates().get(0).getMonth() + 1) + "-" + (1900 + (calendar.getSelectedDates().get(0).getYear()));
                        String checkOut = calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getDate() + "-" + (calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getMonth() + 1) + "-" + (1900 + (calendar.getSelectedDates().get(calendar.getSelectedDates().size() - 1).getYear()));

                        System.out.println("Check-in date2:" + checkIn);
                        System.out.println("Checkout date2:" + checkOut);

                        selectedDates = getDates(checkIn, checkOut);
                        System.out.println("Selected dates are:" + selectedDates);

                        if (Collections.disjoint(rDates, selectedDates)) {
                            if (isFrom != null && !isFrom.equals("null")) {
                                if (isFrom.equals("book_it")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("checkin", checkIn);
                                    intent.putExtra("checkout", checkOut);
                                    intent.putExtra("checkinstr", strCheckin);
                                    intent.putExtra("checkoutstr", strCheckout);
                                    intent.putExtra("stay_nights", String.valueOf(differenceDates));
                                    intent.putExtra("capacity", strGuests);
                                    setResult(CALENDAR_REQ_CODE_IN_BOOKIT, intent);
                                    finish();
                                } else if (isFrom.equals("detail")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("checkin", checkIn);
                                    intent.putExtra("checkout", checkOut);
                                    intent.putExtra("checkinstr", strCheckin);
                                    intent.putExtra("checkoutstr", strCheckout);
                                    intent.putExtra("stay_nights", String.valueOf(differenceDates));
                                    intent.putExtra("capacity", strGuests);
                                    setResult(CALENDAR_REQ_CODE_IN_DETAIL, intent);
                                    finish();
                                } else if (isFrom.equals("contact")) {
                                    Intent intent = new Intent();
                                    intent.putExtra("checkin", checkIn);
                                    intent.putExtra("checkout", checkOut);
                                    intent.putExtra("checkinstr", strCheckin);
                                    intent.putExtra("checkoutstr", strCheckout);
                                    intent.putExtra("stay_nights", String.valueOf(differenceDates));
                                    intent.putExtra("capacity", strGuests);
                                    setResult(CALENDAR_REQ_CODE_IN_DETAIL, intent);
                                    finish();
                                }
                            }

                        } else {
                            Toast.makeText(CalendarActivity.this, "Those Dates are not available", Toast.LENGTH_SHORT).show();
                        }


                        System.out.println("Checkin" + checkInDates);
                        System.out.println("checkout" + checkOutDates);
                    }
                }
            }
        });
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }


    private ArrayList<String> getDates(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        ArrayList<String> tempDates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }

        for (int j = 0; j < dates.size(); j++) {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date date = null;
            try {
                date = (Date) formatter.parse(String.valueOf(dates.get(j)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(date);

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            String result = format.format(date);
            System.out.println("Converted Dates" + result);
            tempDates.add(result);
            System.out.println("Dates array" + tempDates);
        }
        return tempDates;
    }

    private ArrayList<Date> getDate(String dateString1, String dateString2) {
        ArrayList<Date> dates = new ArrayList<Date>();
        ArrayList<String> tempDates = new ArrayList<String>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }


        return dates;
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(CalendarActivity.this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public class EventDecorator implements CalendarCellDecorator {
        private ArrayList<Date> evd;
        private int BackgroundColor;

        public EventDecorator(ArrayList<Date> evd, int BackgroundColor) {
            this.evd = evd;
            this.BackgroundColor = BackgroundColor;
        }

        @Override
        public void decorate(CalendarCellView calendarCellView, Date date) {
            if (date.equals(evd)) {
                calendarCellView.setBackgroundColor(BackgroundColor);
            } else {
                calendarCellView.setBackgroundResource(R.drawable.calendar_bg_selector);

            }

        }

    }


}
