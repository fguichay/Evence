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

   public static List<Event> parseHosted(String content) {

       System.out.println("this is the content: " + content);

        try {
            JSONArray ar = new JSONArray(content);
            List<Event> eventList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Event event = new Event();


                event.setEventId(obj.getString("event_id"));
                event.setName(obj.getString("name"));
                event.setDesc(obj.getString("descr"));
                event.setStartDate(obj.getString("start_time"));
                event.setEndDate(obj.getString("end_time"));
                event.setLocation(obj.getString("formatted_address"));
                event.setLat(obj.getString("latitude"));
                event.setLng(obj.getString("longitude"));
                event.setRadius(obj.getString("radius"));
                event.setGeoStart(obj.getString("geofence_start_time"));
                event.setGeoEnd(obj.getString("geofence_end_time"));
                event.setArriveMsg(obj.getString("notify_arriving_guest"));
                event.setDepartMsg(obj.getString("notify_departing_guest"));

                eventList.add(event);

            }

            return eventList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static List<Event> parseInvited(String content) {

        System.out.println("this is the content: " + content);

        try {
            JSONArray ar = new JSONArray(content);
            List<Event> eventList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {

                JSONObject obj = ar.getJSONObject(i);
                Event event = new Event();

                event.setName(obj.getString("name"));
                event.setDesc(obj.getString("descr"));

                eventList.add(event);

            }

            return eventList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


}
