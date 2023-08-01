package com.example.mstarttask_hussam.Model.Beans.Events;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String event_name;
    private String event_description;
    private String gregorian_date;
    private String hijri_date;
    private String server_datetime;

    public Event(String event_name, String event_description, String gregorian_date, String hijri_date, String server_datetime) {
        this.event_name = event_name;
        this.event_description = event_description;
        this.gregorian_date = gregorian_date;
        this.hijri_date = hijri_date;
        this.server_datetime = server_datetime;
    }

    public Event() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getGregorian_date() {
        return gregorian_date;
    }

    public void setGregorian_date(String gregorian_date) {
        this.gregorian_date = gregorian_date;
    }

    public String getHijri_date() {
        return hijri_date;
    }

    public void setHijri_date(String hijri_date) {
        this.hijri_date = hijri_date;
    }

    public String getServer_datetime() {
        return server_datetime;
    }

    public void setServer_datetime(String server_datetime) {
        this.server_datetime = server_datetime;
    }
}
