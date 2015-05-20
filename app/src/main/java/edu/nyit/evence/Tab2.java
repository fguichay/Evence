package edu.nyit.evence;

/**
 * Created by Frank on 3/11/2015.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nyit.evence.adapter.EventAdapter;
import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.db.SessionManager;
import edu.nyit.evence.model.Event;
import edu.nyit.evence.parser.EventJSONParser;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2 extends ListFragment {

    private ProgressDialog pDialog;
    private static final String TAG = Tab2.class.getSimpleName();

    //List<Event> events = new EventJSONParser().getHosted();
    //List <Event> events = new EventJSONParser().getHosted();

    List<Event> eventList = new ArrayList<Event>();
    SessionManager session;

    //private Callbacks activity;

    public Tab2(){
        }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // session manager
        session = new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        final String userID = user.get(SessionManager.KEY_USER_ID);

        requestData(userID, "getInvited");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2,container,false);

        return v;
    }

    private void requestData(String id, String tag){

        final String uid = id;
        final String requestTag = tag;
        String tag_json_obj = "req_event";
        pDialog.setMessage("Loading...");
        showpDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());


                        eventList = EventJSONParser.parseInvited(response);

                            updateDisplay();

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", requestTag);
                params.put("uid", uid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    protected void updateDisplay() throws NullPointerException {
        EventAdapter adapter = new EventAdapter(getActivity(), R.layout.item_event, eventList);
        setListAdapter(adapter);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

/*    public interface Callbacks {
        public void onItemSelected(Event event);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Event event = eventList.get(position);
        activity.onItemSelected(event);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }*/

}