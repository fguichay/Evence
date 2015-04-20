package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.nyit.evence.db.SessionManager;

public class CreateEvent_2 extends Activity implements NumberPicker.OnValueChangeListener {

    private Button btnPrevious;
    private Button btnNext;
    private Button btnLocate;
    private EditText txtAddress;
    private EditText txtFenceStart;
    private EditText txtFenceEnd;
    private SeekBar barRadius;
    private TextView viewRadius;
    static Dialog d;
    private String eventAddress;

    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2);

        //session manager - check for user login
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        //initialize user input fields
        txtFenceStart = (EditText) findViewById(R.id.txtFenceStart);
        txtFenceEnd = (EditText) findViewById(R.id.txtFenceEnd);

        barRadius = (SeekBar) findViewById(R.id.barRadius);
        viewRadius = (TextView) findViewById(R.id.viewRadius);

        txtAddress = (EditText) findViewById(R.id.txtAddress);
        eventAddress = txtAddress.getText().toString();

        //initialize the radius seekbar
        viewRadius.setText("Radius: " + barRadius.getProgress() + " Miles");
        barRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewRadius.setText("Radius: " + progress + " Miles");
            }
        });

        //initialize dialog for fence start time
        txtFenceStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startFence();
            }
        });

        //initialize dialog for fence end time
        txtFenceEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endFence();
            }
        });

        //initialize Locate button & set onClick listener
        btnLocate = (Button) findViewById(R.id.btnLocate);
        btnLocate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //insert code for "locate" button to search & display location on google maps

            }

        });

        //initialize Next button & set onClick listener - sends user to the invite-guests form
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {

            //code to pass parameters to db once next button is clicked
            //postParams();

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateEvent_3.class);
                startActivity(intent);
            }

        });

        //initialize Previous button & set onClick listener - returns the user to the event-details form
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            //code to go back to the previous activity (double check code below..)

            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), CreateEvent_1.class);
                //startActivity(intent);
            }

        });

    }

    //method to get a fence start time
    public void startFence() {

        final Dialog d = new Dialog(CreateEvent_2.this);
        d.setTitle("Select a Time [hh:mm]");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker hour = (NumberPicker) d.findViewById(R.id.numberPicker1);
        final NumberPicker minute = (NumberPicker) d.findViewById(R.id.numberPicker2);

        hour.setMaxValue(12); //hour max value 12
        hour.setMinValue(0);   //hour min value 0
        hour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        hour.setWrapSelectorWheel(true);
        hour.setOnValueChangedListener(this);

        minute.setMaxValue(60); //minute max value 60
        minute.setMinValue(0);   //minute min value 0
        minute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        minute.setWrapSelectorWheel(true);
        minute.setOnValueChangedListener(this);

        //initializes Set button & display the time selected
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hour.getValue() == 0) {
                    txtFenceStart.setText("Start " + String.valueOf(minute.getValue()) + " Minutes before event");
                } else {
                    txtFenceStart.setText("Start " + String.valueOf(hour.getValue()) + ":" + String.valueOf(minute.getValue()) + " Hour(s) before event"); //set the value to textview
                }
                d.dismiss();
            }
        });

        //initializes Cancel button & dismisses the time picker dialog
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    //method to get a fence end time
    public void endFence() {

        final Dialog d = new Dialog(CreateEvent_2.this);
        d.setTitle("Select a Time [hh:mm]");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker hour = (NumberPicker) d.findViewById(R.id.numberPicker1);
        final NumberPicker minute = (NumberPicker) d.findViewById(R.id.numberPicker2);

        hour.setMaxValue(12); //hour max value 12
        hour.setMinValue(0);   //hour min value 0
        hour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        hour.setWrapSelectorWheel(true);
        hour.setOnValueChangedListener(this);

        minute.setMaxValue(60); //minute max value 60
        minute.setMinValue(0);   //minute min value 0
        minute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        minute.setWrapSelectorWheel(true);
        minute.setOnValueChangedListener(this);

        //initializes Set button & display the time selected
        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hour.getValue() == 0) {
                    txtFenceEnd.setText("End " + String.valueOf(minute.getValue()) + " Minutes after event");
                } else {
                    txtFenceEnd.setText("End " + String.valueOf(hour.getValue()) + ":" + String.valueOf(minute.getValue()) + " Hour(s) after event"); //set the value to textview
                }
                d.dismiss();
            }
        });

        //initializes Cancel button & dismisses the time picker dialog
        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    //log changes to numberpicker
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is", "" + newVal);
    }
}