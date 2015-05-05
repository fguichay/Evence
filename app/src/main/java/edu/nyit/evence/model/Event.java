package edu.nyit.evence.model;

import android.os.Bundle;

/**
 * Created by Frank on 4/16/2015.
 */
public class Event {

    private String name;
    private int eventId;
    private String startDate;
    private String endDate;
    private String desc;
    private String location;
    private String lat;
    private String lng;
    private String geoStart;
    private String geoEnd;
    private String arriveMsg;
    private String departMsg;

    //	constants for field references
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_DESC = "eventDesc";

    //	Used when creating the data object
    public Event(){
    }

    public Event(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventIdD() {
        return eventId;
    }

    public void setEventId(int eventId) {
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


    //	Create from a bundle
    public Event(Bundle b) {
        if (b != null) {
            this.name = b.getString(EVENT_NAME);
            this.desc = b.getString(EVENT_DESC);
        }
    }

    //	Package data for transfer between activities
    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putString(EVENT_NAME, this.name);
        b.putString(EVENT_DESC, this.desc);
        return b;
    }

    @Override
    public String toString(){
        return name;
    }

}
