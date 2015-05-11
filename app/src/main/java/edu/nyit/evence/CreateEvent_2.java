package edu.nyit.evence;

/**
 * Created by Frank on 3/17/2015.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import edu.nyit.evence.app.AppConfig;
import edu.nyit.evence.app.AppController;
import edu.nyit.evence.db.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Location;
import android.widget.Toast;


public class CreateEvent_2 extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        NumberPicker.OnValueChangeListener {

    private static final String TAG = Register.class.getSimpleName();
    private Button btnPrevious;
    private Button btnNext;
    private EditText txtAddress;
    private EditText txtFenceStart;
    private EditText txtFenceEnd;
    private SeekBar barRadius;
    private TextView viewRadius;
    static Dialog d;
    private ProgressDialog pDialog;
    private SessionManager session;
    private String eventAddress;
    private double radius;
    private int geoFenceID;
    private String startTimeFormatted;
    private String endTimeFormatted;
    private GoogleMap mMap;
    String latitude = null;
    String longitude = null;
    private static final double NEWYORK_LAT = 40.714353, NEWYORK_LNG = -74.005973;
    private static final float DEFAULTZOOM = 10;

    GoogleApiClient mLocationClient;
    Marker marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2);


        if (initMap()) {
            Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
            //mMap.setMyLocationEnabled(true);

            gotoLocation(NEWYORK_LAT, NEWYORK_LNG, DEFAULTZOOM);

            mLocationClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            mLocationClient.connect();

        } else {
            Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
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


        //initialize the radius seekbar
        viewRadius.setText("Radius: " + barRadius.getProgress() + " Miles");
        barRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

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

        radius = 8.9;
        geoFenceID = 5555555;

        final String eventRadius = String.valueOf(radius);
        final String gFenceID = Integer.toString(geoFenceID);

        //initialize Next button & set onClick listener - sends user to the invite-guests form
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //code to pass parameters to db once next button is clicked
                postParams(eventID, eventAddress, latitude, longitude, eventRadius, gFenceID, startTimeFormatted, endTimeFormatted);

                System.out.println("this is eventid: " + eventID);
                System.out.println("this is eventAddress: " + eventAddress);
                System.out.println("this is eventLatd: " + latitude);
                System.out.println("this is eventLong: " + longitude);
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

    public void geoLocate(View v) throws IOException {
        //hideSoftKeyboard(v);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        String location = txtAddress.getText().toString();


        if (location.length() == 0) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder gc = new Geocoder(this, Locale.getDefault());
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();

        String address = add.getAddressLine(0);
        String area = add.getAdminArea();
        String zip = add.getPostalCode();

        Toast.makeText(this, address + ", " + locality + " " + area + " " + zip, Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        gotoLocation(lat, lng, DEFAULTZOOM);

        setMarker(address, locality, lat, lng);

        eventAddress = address + ", " + locality + " " + area + " " + zip;
        latitude = String.valueOf(lat);
        longitude = String.valueOf(lng);

        System.out.println("geolocate loc: " + latitude + " , " + longitude);

    }

    public void geoCurrentLocation(View v) throws IOException {
        gotoCurrentLocation();
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFrag =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFrag.getMap();

            if (mMap != null) {
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.info_window, null);
                        TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);
                        TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                        TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                        TextView tvArea = (TextView) v.findViewById(R.id.tv_area);

                        LatLng ll = marker.getPosition();

                        tvAddress.setText(marker.getTitle());
                        tvLat.setText("Latitude: " + ll.latitude);
                        tvLng.setText("Longitude: " + ll.longitude);
                        tvArea.setText(marker.getSnippet());

                        return v;
                    }
                });

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng ll) {
                        Geocoder gc = new Geocoder(CreateEvent_2.this);
                        List<Address> list = null;
                        try {
                            list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        Address add = list.get(0);
                        String locality = add.getLocality();
                        String address = add.getAddressLine(0);
                        String area = add.getAdminArea();
                        String zip = add.getPostalCode();

                        Toast.makeText(getApplicationContext(), address + ", " + locality + " " + area + " " + zip, Toast.LENGTH_LONG).show();

                        setMarker(address, locality, ll.latitude, ll.longitude);

                        eventAddress = address + ", " + locality + " " + area + " " + zip;
                        latitude = String.valueOf(ll.latitude);
                        longitude = String.valueOf(ll.longitude);

                        System.out.println("mapclicklistener loc: " + latitude + " , " + longitude);

                    }
                });


            }

        }
        return (mMap != null);
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    private void gotoCurrentLocation() throws IOException {
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        if (currentLocation == null) {
            Toast.makeText(this, "Current Location is not Available", Toast.LENGTH_SHORT).show();
        } else {
            LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOM);
            mMap.animateCamera(update);
        }

        Geocoder currentLoc = new Geocoder(this, Locale.getDefault());
        List<Address> list = currentLoc.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        String address = add.getAddressLine(0);
        String area = add.getAdminArea();
        String zip = add.getPostalCode();

        Toast.makeText(this, address + ", " + locality + " " + area + " " + zip, Toast.LENGTH_LONG).show();

        setMarker(address, locality, currentLocation.getLatitude(), currentLocation.getLongitude());

        eventAddress = address + ", " + locality + " " + area + " " + zip;
        latitude = String.valueOf(currentLocation.getLatitude());
        longitude = String.valueOf(currentLocation.getLongitude());

        System.out.println("currentLocation loc: " + latitude + " , " + longitude);
    }

    public void setMarker(String address, String area, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }

        MarkerOptions options = new MarkerOptions()
                .title(address)
                .snippet(area)
                .position(new LatLng(lat, lng));
        marker = mMap.addMarker(options);
    }


    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected to Location Service", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}