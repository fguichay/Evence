package edu.nyit.evence;

/**
 * Created by Frank on 4/26/2015.
 */

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import edu.nyit.evence.model.Event;

public class EventAdapter extends ArrayAdapter<Event>{

    private Context context;
    private List<Event> objects;

    public EventAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = objects.get(position);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_event, parent, false);

        //Display event name in the TextView widget
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(event.getName());

        return view;
    }

}
