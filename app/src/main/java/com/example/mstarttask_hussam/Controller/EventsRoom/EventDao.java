package com.example.mstarttask_hussam.Controller.EventsRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mstarttask_hussam.Model.Beans.Events.Event;

import java.util.List;

@Dao
public interface EventDao {

    //Get All Event
    @Query("SELECT * FROM events")
    List<Event> getAllEvents();

    //Get Event By position
    @Query("SELECT * FROM events LIMIT 1 OFFSET :position")
    Event getEventByPosition(int position);

    //Add New Event
    @Insert
    void insert(Event event);

    //Edit Event Event
    @Update
    void update(Event event);

    //Delete Event
    @Delete
    void delete(Event event);

}
