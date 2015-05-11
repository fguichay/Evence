package edu.nyit.evence.model;

import android.os.Bundle;

/**
 * Created by Frank on 4/16/2015.
 */
public class Event {

    private String eventId;
    private String name;
    private String startDate;
    private String endDate;
    private String desc;
    private String location;
    private String lat;
    private String lng;
    private String radius;
    private String geoStart;
    private String geoEnd;
    private String arriveMsg;
    private String departMsg;

    //	constants for field references
    public static final String EVENT_ID = "eventID";
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_STARTDATE = "eventStartDate";
    public static final String EVENT_ENDDATE = "eventEndDate";
    public static final String EVENT_DESC = "eventDesc";
    public static final String EVENT_LOCATION = "eventLocation";
    public static final String EVENT_LAT = "eventLat";
    public static final String EVENT_LNG = "eventLng";
    public static final String EVENT_RADIUS = "eventRadius";
    public static final String EVENT_GEOSTART = "eventGeoStart";
    public static final String EVENT_GEOEND = "eventGeoEnd";
    public static final String EVENT_ARRIVINGMSG = "eventArriveMsg";
    public static final String EVENT_DEPARTMSG = "eventDepartMsg";


    //	Used when creating the data object
    public Event(){
    }

    public Event(String id, String name, String start, String end, String desc, String location, String lat, String lng,
                 String rad, String gStart, String gEnd, String arrive, String depart){
        this.eventId = id;
        this.name = name;
        this.startDate = start;
        this.endDate = end;
        this.desc = desc;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.radius = rad;
        this.geoStart = gStart;
        this.geoEnd = gEnd;
        this.arriveMsg = arrive;
        this.departMsg = depart;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventIdD() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getGeoStart() {
        return geoStart;
    }

    public void setGeoStart(String geoStart) {
        this.geoStart = geoStart;
    }

    public String getGeoEnd() {
        return geoEnd;
    }

    public void setGeoEnd(String geoEnd) {
        this.geoEnd = geoEnd;
    }

    public String getArriveMsg() {
        return arriveMsg;
    }

    public void setArriveMsg(String arriveMsg) {
        this.arriveMsg = arriveMsg;
    }

    public String getDepartMsg() {
        return departMsg;
    }

    public void setDepartMsg(String departMsg) {
        this.departMsg = departMsg;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }


    //	Create from a bundle
    public Event(Bundle b) {
        if (b != null) {
            this.eventId = b.getString(EVENT_ID);
            this.name = b.getString(EVENT_NAME);
            this.startDate = b.getString(EVENT_STARTDATE);
            this.endDate = b.getString(EVENT_ENDDATE);
            this.desc = b.getString(EVENT_DESC);
            this.location = b.getString(EVENT_LOCATION);
            this.lat = b.getString(EVENT_LAT);
            this.lng = b.getString(EVENT_LNG);
            this.radius = b.getString(EVENT_RADIUS);
            this.geoStart = b.getString(EVENT_GEOSTART);
            this.geoEnd = b.getString(EVENT_GEOEND);
            this.arriveMsg = b.getString(EVENT_ARRIVINGMSG);
            this.departMsg = b.getString(EVENT_DEPARTMSG);

        }
    }

    //	Package data for transfer between activities
    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putString(EVENT_ID, this.eventId);
        b.putString(EVENT_NAME, this.name);
        b.putString(EVENT_STARTDATE, this.startDate);
        b.putString(EVENT_ENDDATE, this.endDate);
        b.putString(EVENT_DESC, this.desc);
        b.putString(EVENT_LOCATION, this.location);
        b.putString(EVENT_LAT, this.lat);
        b.putString(EVENT_LNG, this.lng);
        b.putString(EVENT_RADIUS, this.radius);
        b.putString(EVENT_GEOSTART, this.geoStart);
        b.putString(EVENT_GEOEND, this.geoEnd);
        b.putString(EVENT_ARRIVINGMSG, this.arriveMsg);
        b.putString(EVENT_DEPARTMSG, this.departMsg);

        return b;
    }

    @Override
    public String toString(){
        return name;
    }

}
