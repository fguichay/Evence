package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

public class CreateEvent_2 extends Activity implements NumberPicker.OnValueChangeListener {

    private Button button;
    private EditText fenceStart;
    private EditText fenceEnd;
    private SeekBar seekBar;
    private TextView textView;
    static Dialog d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2);

        fenceStart = (EditText) findViewById(R.id.fenceStartTrack);
        fenceEnd = (EditText) findViewById(R.id.fenceEndTrack);
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        textView = (TextView) findViewById(R.id.textView1);

        //initalize radius seekbar
        textView.setText("Radius: " + seekBar.getProgress() + " Miles");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                textView.setText("Radius: " + progress + " Miles");
            }
        });

        //initialize dialog for fence start time
        fenceStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startShow();
            }
        });

        //initialize dialog for fence end time
        fenceEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endShow();
            }
        });

        //temporary? - buttons to navigate screens
        addListenerOnButton();
    }

    public void startShow() {

        final Dialog d = new Dialog(CreateEvent_2.this);
        d.setTitle("Select a Time [hh:mm]");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker hour = (NumberPicker) d.findViewById(R.id.numberPicker1);
        final NumberPicker minute = (NumberPicker) d.findViewById(R.id.numberPicker2);

        hour.setMaxValue(12); // max value 100
        hour.setMinValue(0);   // min value 0
        hour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        hour.setWrapSelectorWheel(true);
        hour.setOnValueChangedListener(this);

        minute.setMaxValue(60); // max value 60
        minute.setMinValue(0);   // min value 1
        minute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        minute.setWrapSelectorWheel(true);
        minute.setOnValueChangedListener(this);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hour.getValue() == 0) {
                    fenceStart.setText("Start " + String.valueOf(minute.getValue()) + " Minutes before event");
                } else {
                    fenceStart.setText("Start " + String.valueOf(hour.getValue()) + ":" + String.valueOf(minute.getValue()) + " Hour(s) before event"); //set the value to textview
                }
                d.dismiss();
            }
        });

        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    public void endShow() {

        final Dialog d = new Dialog(CreateEvent_2.this);
        d.setTitle("Select a Time [hh:mm]");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);

        final NumberPicker hour = (NumberPicker) d.findViewById(R.id.numberPicker1);
        final NumberPicker minute = (NumberPicker) d.findViewById(R.id.numberPicker2);

        hour.setMaxValue(12); // max value 100
        hour.setMinValue(0);   // min value 0
        hour.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        hour.setWrapSelectorWheel(true);
        hour.setOnValueChangedListener(this);

        minute.setMaxValue(60); // max value 60
        minute.setMinValue(0);   // min value 1
        minute.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        minute.setWrapSelectorWheel(true);
        minute.setOnValueChangedListener(this);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hour.getValue() == 0) {
                    fenceEnd.setText("End " + String.valueOf(minute.getValue()) + " Minutes after event");
                } else {
                    fenceEnd.setText("End " + String.valueOf(hour.getValue()) + ":" + String.valueOf(minute.getValue()) + " Hour(s) after event"); //set the value to textview
                }
                d.dismiss();
            }
        });

        b2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is", "" + newVal);
    }

    public void addListenerOnButton() {
        final Context context = this;
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, CreateEvent_3.class);
                startActivity(intent);
            }

        });
    }
}