package edu.nyit.evence;

/**
 * Created by Frank on 3/1/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.db.SQLiteHandler;
import edu.nyit.evence.db.SessionManager;
import edu.nyit.evence.model.Event;
import edu.nyit.evence.tabs.SlidingTabLayout;
import edu.nyit.evence.tabs.ViewPagerAdapter;


public class MainEventsActivity extends ActionBarActivity implements Tab1.Callbacks {

    // Declaring Your View and Variables
    private static final String TAG = Register.class.getSimpleName();
    Button btnCreateEvent;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Hosting","Invites"};
    int Numboftabs =2;
    private ProgressDialog pDialog;

    public static final String EVENT_BUNDLE = "EVENT_BUNDLE";
    private static final int REQUEST_CODE = 1001;


    //XXXXX
    private SQLiteHandler db;
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //XXXXX
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        // name
        final String userID = user.get(SessionManager.KEY_USER_ID);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();





        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Fetching user details from sqlite
        //HashMap<String, String> user = db.getUserDetails();

        //String name = user.get("name");
        //String email = user.get("email");

        // Displaying the user details on the screen
        //Toast.makeText(getApplicationContext(), "em_id: " + email + "+" + name ,Toast.LENGTH_LONG).show();

        //txtName.setText(name);
        //txtEmail.setText(email);



        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        btnCreateEvent = (Button) findViewById(R.id.btnCreateEvent);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                getEventID(userID);

                Intent intent = new Intent(getApplicationContext(), CreateEvent_1.class);
                startActivity(intent);

            }

        });

    }


    private void getEventID(String userID) {

        final String uid = userID;
        // Tag used to cancel the request
        String  tag_json_obj = "req_event";
        System.out.println(uid);
        pDialog.setMessage("Loading...");
        showpDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    int id = jObj.getInt("event_id");
                    String eventID = Integer.toString(id);
                    Toast.makeText(getApplicationContext(), "event id: " + eventID ,Toast.LENGTH_LONG).show();
                    Log.d(TAG, "this is the event id beinbg passes" + eventID);
                    session.createEvent(eventID);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

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
                params.put("tag", "startCreate");
                params.put("uid", uid);

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



    private void logoutUser() {
        session.logoutUser();
        db.deleteUsers();

        // Launching the login activity
        //Intent intent = new Intent(MainEventsActivity.this, Login.class);
        //startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } if(id == R.id.logout){

            logoutUser();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Event event) {
        Bundle b = event.toBundle();
        Intent intent = new Intent(this, EventDetailActivity.class);
        intent.putExtra(EVENT_BUNDLE, b);
        startActivityForResult(intent, REQUEST_CODE);
    }

}
