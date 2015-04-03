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
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class CreateEvent_1 extends Activity {

    Button btnNextPage;
    Button btnCancel;



    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    TextView lblDateAndTime;
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            updateLabel();
        }
    };

    private void updateLabel() {
        lblDateAndTime.setText(fmtDateAndTime.format(myCalendar.getTime()));
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1);
        addListenerOnButton();
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


        lblDateAndTime = (TextView) findViewById(R.id.lbl_date_time);
        Button btnDate = (Button) findViewById(R.id.btn_date);

        btnDate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(CreateEvent_1.this, d, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Button btnTime = (Button) findViewById(R.id.btn_time);
        btnTime.setOnClickListener(new OnClickListener() {
            public  void onClick(View v) {
                new TimePickerDialog(CreateEvent_1.this, t, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        updateLabel();


    }


}