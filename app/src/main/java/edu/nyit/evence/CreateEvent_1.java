package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.db.SessionManager;

public class CreateEvent_1 extends Activity {

    private static final String TAG = Register.class.getSimpleName();
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
    private EditText txtEventName;
    private EditText txtDesc;
    private ProgressDialog pDialog;
    private SessionManager session;
    private String timeStart;
    private String timeEnd;
    private String dateStart;
    private String dateEnd;
    private String startFormatted;
    private String endFormatted;
    private String eventName;
    private String desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1);

        // session manager - check for user login
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //initialize user input fields
        txtStartDate = (EditText) findViewById(R.id.txtStartDate);
        txtEndDate = (EditText) findViewById(R.id.txtEndDate);

        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);

        txtEventName = (EditText) findViewById(R.id.txtEventName);
        eventName = txtEventName.getText().toString();

        txtDesc = (EditText) findViewById(R.id.txtDesc);
        desc = txtDesc.getText().toString();

        //initialize calendar items
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);

        //initialize onClick listeners for time & date text fields
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

        //get userID and eventID from shared preferences
        HashMap<String, String> user = session.getUserDetails();
        final String userID = user.get(SessionManager.KEY_USER_ID);
        final String eventID = user.get(SessionManager.EVENT_ID);

        //initialize Next button & set onClick listener - sends user to the location form
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Toast.makeText(getApplicationContext(), "LALALAevent id: " + eventID, Toast.LENGTH_LONG).show();

                formatTime(timeStart, timeEnd, dateStart, dateEnd);

                postParams(eventID, eventName, startFormatted, endFormatted, desc);

                Intent intent = new Intent(getApplicationContext(), CreateEvent_2.class);
                startActivity(intent);
            }

        });

        //initialize Cancel button & set onClick listener - returns the user to the main events page
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(), MainEventsActivity.class);
                startActivity(intent);
            }
        });
    }

    //method to get the start date
    public void startDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;
                if (month < 10) {

                    formattedMonth = "0" + month;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                dateStart = formattedMonth + "-" + formattedDayOfMonth + "-" + year;
                txtStartDate.setText(dateStart);
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }

    //method to get an end date
    public void endDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + dayOfMonth;
                if (month < 10) {

                    formattedMonth = "0" + month;
                }
                if (dayOfMonth < 10) {

                    formattedDayOfMonth = "0" + dayOfMonth;
                }
                dateEnd = formattedMonth + "-" + formattedDayOfMonth + "-" + year;
                txtEndDate.setText(dateEnd);
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, year, month, day);
        dpDialog.show();
    }

    //helper method to format time picker values
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    //method to get a start time
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

                int time = (minute * 60 + hour * 60 * 60) * 1000;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                timeStart = format.format(time);

            }
        };

        TimePickerDialog tpDialog = new TimePickerDialog(this, listener, hour, minute, false);
        tpDialog.show();
    }

    //method to get an end time
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

                int time = (minute * 60 + hour * 60 * 60) * 1000;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                timeEnd = format.format(time);

            }
        };

        TimePickerDialog tpDialog = new TimePickerDialog(this, listener, hour, minute, false);
        tpDialog.show();
    }

    //format start time and end time to: mm:dd:yyyy hh:mm:ss
    public void formatTime(String timeStart, String timeEnd, String dateStart, String dateEnd) {
        startFormatted = dateStart + " " + timeStart;
        endFormatted = dateEnd + " " + timeEnd;
    }

    //method to post parameters to db
    private void postParams(String id, String name, String start, String end, String desc) {

        final String eID = id;
        final String eName = name;
        final String eStart = start;
        final String eEnd = end;
        final String eDesc = desc;

        // Tag used to cancel the request
        String tag_json_obj = "req_event";

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
                params.put("tag", "addDetail");
                params.put("event_id", eID);
                params.put("name", eName);
                params.put("desc", eDesc);
                params.put("start_time", eStart);
                params.put("end_time", eEnd);

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

}