package edu.nyit.evence;

/**
 * Created by Frank on 3/11/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import edu.nyit.evence.model.Event;

public class Tab1 extends ListFragment {

    List<Event> events = new EventData().getEvents();

    private Callbacks activity;

    public Tab1(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



        EventAdapter adapter = new EventAdapter(getActivity(), R.layout.item_event, events);

        setListAdapter(adapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1,container,false);
        return v;

    }

    public interface Callbacks {
        public void onItemSelected(Event event);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Event event = events.get(position);
        activity.onItemSelected(event);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }

}