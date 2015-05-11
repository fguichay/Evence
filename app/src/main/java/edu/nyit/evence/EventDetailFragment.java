package edu.nyit.evence;

/**
 * Created by Frank on 4/28/2015.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.nyit.evence.model.Event;

public class EventDetailFragment extends Fragment implements View.OnClickListener {

    Button viewGuestList;
    private GoogleMap mMap;
    MapView mMapView;
    Marker marker;
    Event event;

    //    Required no-args constructor
    public EventDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (b != null && b.containsKey(event.EVENT_NAME)) {
            event = new Event(b);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        double lat = Double.parseDouble(event.getLat());
        double lng = Double.parseDouble(event.getLng());

        String d = event.getStartDate();
        String d1 = d.substring(0, 10);
        String t1 = d.substring(11);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("MMM dd, yyyy");
        String formattedDate = sdf.format(date);


        SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
        Date dt = null;
        try {
            dt = sdf2.parse(t1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
        String formatterTime = sdfs.format(dt);

//        Load the layout
        View view = inflater.inflate(R.layout.event_detail_fragment, container, false);

        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMap = mMapView.getMap();
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        gotoLocation(lat, lng, 15);
        //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10);
        //mMap.animateCamera(cameraUpdate);

        setMarker(event.getLocation(), lat, lng);


        viewGuestList = (Button) view.findViewById(R.id.btnViewGuest);
        viewGuestList.setOnClickListener(this);


        if (event != null) {

            //Display values and image
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(event.getName());

            TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvAddress.setText(event.getLocation());

            TextView tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
            tvStartDate.setText(formattedDate + " at " + formatterTime);

            //TextView tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);
            //tvEndDate.setText(event.getEndDate());

            TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvDesc.setText(event.getDesc());

        }

        return view;
    }

    public void setMarker(String address, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }

        MarkerOptions options = new MarkerOptions()
                .title(address)
                .position(new LatLng(lat, lng));
        marker = mMap.addMarker(options);
    }

    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Invited Guests");
        alertDialog.setMessage("guest1\nguest2\nguest3\nguest4\nguest5");

        alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}