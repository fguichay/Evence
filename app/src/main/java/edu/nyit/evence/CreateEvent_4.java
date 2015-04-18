package edu.nyit.evence;

import android.app.Activity;
import android.app.ProgressDialog;
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


    private Button btnPrevious;
    private Button btnFinish;
    private String op1;
    private RadioGroup radioSelect;
    private EditText txtArriving;
    private EditText txtDeparting;
    private ProgressDialog pDialog;


    private SessionManager session;

    // get user data from session

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_4);

        // session manager
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        radioSelect = (RadioGroup) findViewById(R.id.radioSelect);
        radioSelect.setOnCheckedChangeListener(this);

        txtArriving = (EditText) findViewById(R.id.txtArriving);
        txtDeparting = (EditText) findViewById(R.id.txtDeparting);
        actv(false);


        btnFinish = (Button) findViewById(R.id.btnFinish);


        btnFinish.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {

          HashMap<String, String> user = session.getUserDetails();
          // name
          final String userID = user.get(SessionManager.KEY_USER_ID);

          // eventID
          String eventID = user.get(SessionManager.EVENT_ID);

          Toast.makeText(getApplicationContext(), "LALALAevent id: " + eventID ,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainEventsActivity.class);
                startActivity(intent);

            }

        });

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