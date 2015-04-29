package edu.nyit.evence;

/**
 * Created by Frank on 4/28/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import edu.nyit.evence.model.Event;

public class EventDetailFragment extends Fragment {

    Event event;

    //    Required no-args constructor
    public EventDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if(b != null && b.containsKey(event.EVENT_NAME)){
            event = new Event(b);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Load the layout
        View view = inflater.inflate(R.layout.event_detail_fragment, container, false);

        if (event != null) {

            //Display values and image
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(event.getName());

            TextView tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvDesc.setText(event.getDesc());

        }

        return view;
    }

}