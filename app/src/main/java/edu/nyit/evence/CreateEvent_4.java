package edu.nyit.evence;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.db.SessionManager;

import static com.android.volley.Request.Method.*;

/**
 * Created by Frank on 4/12/2015.
 */
public class CreateEvent_4 extends Activity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = Register.class.getSimpleName();
    private Button btnPrevious;
    private Button btnFinish;
    private String op1;
    private RadioGroup radioSelect;
    private EditText txtArriving;
    private EditText txtDeparting;
    private String arrivingMessage;
    private String departingMessage;
    private ProgressDialog pDialog;
    private SessionManager session;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_4);

        // session manager - check for user login
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //initialize user input fields
        radioSelect = (RadioGroup) findViewById(R.id.radioSelect);
        radioSelect.setOnCheckedChangeListener(this);
        txtArriving = (EditText) findViewById(R.id.txtArriving);
        txtDeparting = (EditText) findViewById(R.id.txtDeparting);
        actv(false);

        arrivingMessage = txtArriving.getText().toString();
        departingMessage = txtDeparting.getText().toString();

        //get userID and eventID from shared preferences
        HashMap<String, String> user = session.getUserDetails();
        final String userID = user.get(SessionManager.KEY_USER_ID);
        final String eventID = user.get(SessionManager.EVENT_ID);

        //initialize Finish button & set onClick listener - create the event & return the user to the main events page
        btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "LALALAevent id: " + eventID, Toast.LENGTH_LONG).show();

                //insert code to pass the custom notification messages onyl if the custom button is selected

                postParams(eventID);

                Intent intent = new Intent(getApplicationContext(), MainEventsActivity.class);
                startActivity(intent);

            }

        });

        //initialize Previous button & set onClick listener - send the user back to the invite-users form
        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            //double check code below..

            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), CreateEvent_3.class);
                //startActivity(intent);

            }
        });

    }


    private void postParams(String id) {
        System.out.println("create event4"+id);
        final String eventID = id;
        // Tag used to cancel the request
        String tag_json_obj = "req_event";

        pDialog.setMessage("Loading...");
        showpDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                /*try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    int id = jObj.getInt("event_id");
                    String eventID = Integer.toString(id);
                    Toast.makeText(getApplicationContext(), "event id: " + eventID ,Toast.LENGTH_LONG).show();
                    session.createEvent(eventID);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }*/

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
                params.put("tag", "endCreate");
                params.put("event_id", eventID);

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


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioDefault:
                op1 = "default";
                actv(false);
                break;

            case R.id.radioCustom:
                op1 = "custom";
                actv(true);
                break;
        }
    }

    private void actv(final boolean active) {
        txtArriving.setEnabled(active);
        txtDeparting.setEnabled(active);
        if (active) {
            txtDeparting.requestFocus();
            txtArriving.requestFocus();

        }
    }
}