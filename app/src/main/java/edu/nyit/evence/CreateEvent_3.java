package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.custom.CustomAdapter;
import edu.nyit.evence.custom.Model;
import edu.nyit.evence.db.SessionManager;

public class CreateEvent_3 extends Activity {

    private static final String TAG = Register.class.getSimpleName();
    private Button btnPrevious;
    private Button btnNext;
    private ListView lvGuestlist;
    private EditText txtEmail;
    private ArrayList<Model> myGuestlist;

    private ArrayList<String> tempList;

    private CustomAdapter adapter;
    private ProgressDialog pDialog;
    private SessionManager session;
    String name;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3);

        // session manager - check for user login
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //initialize guestlist & get an email from textbox
        lvGuestlist = (ListView) findViewById(R.id.lvGuestlist);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        myGuestlist = new ArrayList<Model>();
        adapter = new CustomAdapter(getApplicationContext(), myGuestlist);
        lvGuestlist.setAdapter(adapter);

        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateEvent_2.class);
                startActivity(intent);
            }
        });

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), CreateEvent_4.class);
                startActivity(intent);

            }
        });
    }

    public void addbtn(View v) {
        String email = txtEmail.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a value.", Toast.LENGTH_SHORT).show();
        } else {
            postParams(email);
        }
    }

    private void postParams(String email) {
        final String eMail = email;
        // Tag used to cancel the request
        String tag_string_req = "req_guests";

        pDialog.setMessage("Finding Guest...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    int uid = jObj.getInt("uid");
                    final String userID = Integer.toString(uid);

                    Model md = new Model(eMail);
                    myGuestlist.add(md);
                    adapter.notifyDataSetChanged();
                    txtEmail.setText("");

                    HashMap<String, String> user = session.getUserDetails();
                    final String eventID = user.get(SessionManager.EVENT_ID);
                    postParamsTwo(eventID, userID);

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "User Doesn't Exist", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "checkGuest");
                params.put("email", eMail);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void postParamsTwo(String eID, String uID) {
        final String event_id = eID;
        final String uid = uID;
        // Tag used to cancel the request
        String tag_string_req = "req_guests_add";

        pDialog.setMessage("Adding Guest...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addGuest");
                params.put("event_id", event_id);
                params.put("uid", uid);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
