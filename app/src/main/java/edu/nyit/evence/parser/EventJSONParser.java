package edu.nyit.evence.parser;

/**
 * Created by Frank on 4/26/2015.
 */

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.nyit.evence.model.Event;

public class EventJSONParser {

    /*public static List<Event> parseFeed(String content) {

        try {
            JSONArray ar = new JSONArray(content);
            List<Event> eventList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Event event = new Event();

                event.setName(obj.getString("name"));
                event.setDesc(obj.getString("instructions"));


                eventList.add(event);
            }

            return eventList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }*/

}
