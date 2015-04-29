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
        events.add(new Event("Azalea", "Large double. Good grower, heavy bloomer. Early to mid-season, acid loving plants. Plant in moist well drained soil with pH of 4.0-5.5."));
        events.add(new Event("Tibouchina Semidecandra", "Beautiful large royal purple flowers adorn attractive satiny green leaves that turn orange/red in cold weather. Grows to up to 18 feet, or prune annually to shorten."));
        events.add(new Event("Hibiscus", "Blooms in summer, 20-35 inches high. Fertilize regularly for best results. Full sun, drought tolerant."));
        events.add(new Event("Azalea", "Large double. Good grower, heavy bloomer. Early to mid-season, acid loving plants. Plant in moist well drained soil with pH of 4.0-5.5."));
        events.add(new Event("Tibouchina Semidecandra", "Beautiful large royal purple flowers adorn attractive satiny green leaves that turn orange/red in cold weather. Grows to up to 18 feet, or prune annually to shorten."));
        events.add(new Event("Hibiscus", "Blooms in summer, 20-35 inches high. Fertilize regularly for best results. Full sun, drought tolerant."));
        events.add(new Event("Azalea", "Large double. Good grower, heavy bloomer. Early to mid-season, acid loving plants. Plant in moist well drained soil with pH of 4.0-5.5."));
        events.add(new Event("Tibouchina Semidecandra", "Beautiful large royal purple flowers adorn attractive satiny green leaves that turn orange/red in cold weather. Grows to up to 18 feet, or prune annually to shorten."));
        events.add(new Event("Hibiscus", "Blooms in summer, 20-35 inches high. Fertilize regularly for best results. Full sun, drought tolerant."));

    }


}