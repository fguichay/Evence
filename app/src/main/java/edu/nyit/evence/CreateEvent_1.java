package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import edu.nyit.evence.db.SessionManager;

public class CreateEvent_1 extends Activity {

    private Button btnNext;
    private Button btnCancel;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private EditText txtStartDate;
    private EditText txtEndDate;
    private EditText txtStartTime;
    private EditText txtEndTime;

    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1);

        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }

        txtStartDate = (EditText) findViewById(R.id.txtStartDate);
        txtEndDate = (EditText) findViewById(R.id.txtEndDate);
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        txtStartDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDialog();
            }
        });

        txtEndDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateDialog();
            }
        });

        txtStartTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeDialog();
            }
        });

        txtEndTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeDialog();
            }
        });

        addListenerOnButton();
    }

    public void startDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                txtStartDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }

    public void endDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                txtEndDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public void startTimeDialog() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                String zone = "";

                if (hour > 12) {
                    hour -= 12;
                    zone = "PM";
                } else {
                    zone = "AM";
                }

                txtStartTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)).append(" ").append(zone));
            }
        };

        TimePickerDialog tpDialog = new TimePickerDialog(this, listener, hour, minute, false);
        tpDialog.show();
    }

    public void endTimeDialog() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                String zone = "";

                if (hour > 12) {
                    hour -= 12;
                    zone = "PM";
                } else {
                    zone = "AM";
                }

                txtEndTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)).append(" ").append(zone));
            }
        };

        TimePickerDialog tpDialog = new TimePickerDialog(this, listener, hour, minute, false);
        tpDialog.show();
    }

    public void addListenerOnButton() {

        final Context context = this;
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, CreateEvent_2.class);
                startActivity(intent);
            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainEventsActivity.class);
                startActivity(i);
            }
        });
    }
}