package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.db.SessionManager;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationManager;
import android.content.Context;


public class CreateEvent_2 extends Activity implements NumberPicker.OnValueChangeListener {

    private static final String TAG = Register.class.getSimpleName();
    private Button btnPrevious;
    private Button btnNext;
    private Button btnLocate;
    private EditText txtAddress;
    private EditText txtFenceStart;
    private EditText txtFenceEnd;
    private SeekBar barRadius;
    private TextView viewRadius;
    static Dialog d;
    private ProgressDialog pDialog;
    private SessionManager session;
    private String eventAddress;
    private double latitude;
    private double longitude;
    private double radius;
    private int geoFenceID;
    private String startTimeFormatted;
    private String endTimeFormatted;

    private GoogleMap googleMap;
    private LocationManager locMan;
    private Marker userMarker;
    private int userIcon;
    static final LatLng TutorialsPoint = new LatLng(21 , 57);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            Marker TP = googleMap.addMarker(new MarkerOptions().
                    position(TutorialsPoint).title("TutorialsPoint"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //session manager - check for user login
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        //get userID and eventID from shared preferences
        HashMap<String, String> user = session.getUserDetails();
        final String userID = user.get(SessionManager.KEY_USER_ID);
        final String eventID = user.get(SessionManager.EVENT_ID);

        //initialize user input fields
        txtFenceStart = (EditText) findViewById(R.id.txtFenceStart);
        txtFenceEnd = (EditText) findViewById(R.id.txtFenceEnd);
        barRadius = (SeekBar) findViewById(R.id.barRadius);
        viewRadius = (TextView) findViewById(R.id.viewRadius);
        txtAddress = (EditText) findViewById(R.id.txtAddress);

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

                eventAddress = txtAddress.getText().toString();

                //insert code for "locate" button to search & display location on google maps

            }

        });

        latitude = 40.769620;
        longitude = -73.982647;
        radius = 8.9;
        geoFenceID = 5555555;

        final String eventLatd = String.valueOf(latitude);
        final String eventLong = String.valueOf(longitude);
        final String eventRadius = String.valueOf(radius);
        final String gFenceID = Integer.toString(geoFenceID);

        //initialize Next button & set onClick listener - sends user to the invite-guests form
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //code to pass parameters to db once next button is clicked
                postParams(eventID, eventAddress, eventLatd, eventLong, eventRadius, gFenceID, startTimeFormatted, endTimeFormatted);

                System.out.println("this is eventid: " + eventID);
                System.out.println("this is eventAddress: " + eventAddress);
                System.out.println("this is eventLatd: " + eventLatd);
                System.out.println("this is eventLong: " + eventLong);
                System.out.println("this is eventRadius: " + eventRadius);
                System.out.println("this is gfenceID: " + gFenceID);
                System.out.println("this is geostarttime: " + startTimeFormatted);
                System.out.println("this is geoendtime: " + endTimeFormatted);

                Intent intent = new Intent(getApplicationContext(), CreateEvent_3.class);
                startActivity(intent);
            }

        });

        //initialize Previous button & set onClick listener - returns the user to the event-details form
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            //code to go back to the previous activity (double check code below..)

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateEvent_1.class);
                startActivity(intent);
            }

        });
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    //method to get a fence start time
    private void startFence() {

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
                int hourValue = hour.getValue();
                int minuteValue = minute.getValue();
                if (hourValue == 0) {
                    txtFenceStart.setText(new StringBuilder().append("Start ").append(pad(minuteValue)).append(" Minute(s) before the event"));
                } else {
                    txtFenceStart.setText(new StringBuilder().append("Start ").append(pad(hourValue)).append(":").append(pad(minuteValue)).append(" Hours(s) before the event"));
                }
                startTimeFormatted = pad(hourValue) + ":" + pad(minuteValue) + ":" + "00";
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
    private void endFence() {

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
                int hourValue = hour.getValue();
                int minuteValue = minute.getValue();
                if (hourValue == 0) {
                    txtFenceEnd.setText(new StringBuilder().append("End ").append(pad(minuteValue)).append(" Minute(s) after the event"));
                } else {
                    txtFenceEnd.setText(new StringBuilder().append("End ").append(pad(hourValue)).append(":").append(pad(minuteValue)).append(" hour(s) after the event"));
                }
                endTimeFormatted = pad(hourValue) + ":" + pad(minuteValue) + ":" + "00";
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

    //method to post parameters to db
    private void postParams(String id, String address, String lat, String lon, String rad, String gID, String gStart, String gEnd) {

        final String eID = id;
        final String eAddress = address;
        final String eLat = lat;
        final String eLon = lon;
        final String eRadius = rad;
        final String geoID = gID;
        final String geoStart = gStart;
        final String geoEnd = gEnd;

        // Tag used to cancel the request
        String tag_json_obj = "req_location";

        pDialog.setMessage("Loading...");
        showpDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addLocation");
                params.put("event_id", eID);
                params.put("formatted_address", eAddress);
                params.put("latitude", eLat);
                params.put("longitude", eLon);
                params.put("radius", eRadius);
                params.put("geofenceID", geoID);
                params.put("geofence_start_time", geoStart);
                params.put("geofence_end_time", geoEnd);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


//GOOGLE MAPS METHODS - GOOGLE MAPS METHODS - GOOGLE MAPS METHODS - GOOGLE MAPS METHODS - GOOGLE MAPS METHODS - GOOGLE MAPS METHODS - GOOGLE MAPS METHODS



}