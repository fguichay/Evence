package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreateEvent_1 extends Activity {

    private Button btnNextPage;
    private Button btnCancel;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private EditText startDate;
    private EditText endDate;
    private EditText startTime;
    private EditText endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1);

        startDate = (EditText) findViewById(R.id.setStartDate);
        endDate = (EditText) findViewById(R.id.setEndDate);
        startTime = (EditText) findViewById(R.id.setStartTime);
        endTime = (EditText) findViewById(R.id.setEndTime);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        startDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDialog();
            }
        });

        endDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateDialog();
            }
        });

        startTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeDialog();
            }
        });

        endTime.setOnClickListener(new OnClickListener() {
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
                startDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
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
                endDate.setText(monthOfYear + "/" + dayOfMonth + "/" + year);
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

                startTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)).append(" ").append(zone));
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

                endTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)).append(" ").append(zone));
            }
        };

        TimePickerDialog tpDialog = new TimePickerDialog(this, listener, hour, minute, false);
        tpDialog.show();
    }

    public void addListenerOnButton() {

        final Context context = this;
        btnNextPage = (Button) findViewById(R.id.btn_next);
        btnNextPage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, CreateEvent_2.class);
                startActivity(intent);
            }
        });

        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainEventsActivity.class);
                startActivity(i);
            }
        });
    }
}