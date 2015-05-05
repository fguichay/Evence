package edu.nyit.evence;

/**
 * Created by Frank on 4/28/2015.
 */

import java.util.ArrayList;
import java.util.List;

import edu.nyit.evence.model.Event;


public class EventData {

    private List<Event> events = new ArrayList<Event>();

    public List<Event> getEvents() {
        return events;
    }

    public EventData() {
        events.add(new Event("Google HQ Meet", "Event with Google on April 20th, 2015 at 2:00PM."));
        events.add(new Event("Pulkit's Barmitzvah", "Event with 10 other users on May 16th, 2015 at 8:30PM."));
        events.add(new Event("Shrey's Densit Appointment", "Event with 2 other users on June 8th, 2015 at 2:00PM."));
    }

}