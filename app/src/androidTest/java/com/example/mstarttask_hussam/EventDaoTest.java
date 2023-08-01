package com.example.mstarttask_hussam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.mstarttask_hussam.Controller.EventsRoom.AppDatabase;
import com.example.mstarttask_hussam.Controller.EventsRoom.EventDao;
import com.example.mstarttask_hussam.Model.Beans.Events.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class EventDaoTest {

    private AppDatabase appDatabase;
    private EventDao eventDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        eventDao = appDatabase.eventDao();
    }

    @After
    public void tearDown() {
        appDatabase.close();
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = eventDao.getAllEvents();
        assertEquals(0, events.size());

        eventDao.insert(new Event("Event 1", "Description 1", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted()));
        eventDao.insert(new Event("Event 2", "Description 2", "02-08-2023", "15-01-1445", getCurrentDateTimeFormatted()));
        eventDao.insert(new Event("Event 3", "Description 3", "03-08-2023", "16-01-1445", getCurrentDateTimeFormatted()));
        events = eventDao.getAllEvents();
        assertEquals(3, events.size());
    }

    @Test
    public void testGetEventByPosition() {
        Event event = eventDao.getEventByPosition(0);
        assertNull(event);

        Event event1 = new Event("Event 1", "Description 1", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted());
        Event event2 = new Event("Event 2", "Description 2", "02-08-2023", "15-01-1445", getCurrentDateTimeFormatted());
        Event event3 = new Event("Event 3", "Description 3", "03-08-2023", "16-01-1445", getCurrentDateTimeFormatted());
        eventDao.insert(event1);
        eventDao.insert(event2);
        eventDao.insert(event3);

        event = eventDao.getEventByPosition(0);
        assertEquals(event1.getEvent_name(), event.getEvent_name());
        assertEquals(event1.getEvent_description(), event.getEvent_description());
        assertEquals(event1.getGregorian_date(), event.getGregorian_date());
        assertEquals(event1.getHijri_date(), event.getHijri_date());
        assertEquals(event1.getServer_datetime(), event.getServer_datetime());

        event = eventDao.getEventByPosition(1);
        assertEquals(event2.getEvent_name(), event.getEvent_name());
        assertEquals(event2.getEvent_description(), event.getEvent_description());
        assertEquals(event2.getGregorian_date(), event.getGregorian_date());
        assertEquals(event2.getHijri_date(), event.getHijri_date());
        assertEquals(event2.getServer_datetime(), event.getServer_datetime());

        event = eventDao.getEventByPosition(2);
        assertEquals(event3.getEvent_name(), event.getEvent_name());
        assertEquals(event3.getEvent_description(), event.getEvent_description());
        assertEquals(event3.getGregorian_date(), event.getGregorian_date());
        assertEquals(event3.getHijri_date(), event.getHijri_date());
        assertEquals(event3.getServer_datetime(), event.getServer_datetime());

        event = eventDao.getEventByPosition(3);
        assertNull(event);
    }

    @Test
    public void testAddEvent_Success() {
        Event event = new Event("Event 1", "Description 1", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted());
        eventDao.insert(event);
        List<Event> events = eventDao.getAllEvents();
        assertEquals(1, events.size());
    }

    @Test
    public void testUpdateEvent_Success() {
        Event event = new Event("Event Title", "Event Description", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted());
        eventDao.insert(event);
        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.size() > 0);

        Event updatedEvent = events.get(0);
        updatedEvent.setEvent_name("Updated Event Title");
        updatedEvent.setEvent_description("Updated Event Description");
        eventDao.update(updatedEvent);

        List<Event> updatedEvents = eventDao.getAllEvents();
        assertEquals(updatedEvent.getEvent_name(), updatedEvents.get(0).getEvent_name());
        assertEquals(updatedEvent.getEvent_description(), updatedEvents.get(0).getEvent_description());
    }

    @Test
    public void testUpdateEvent_Failure() {
        Event event = new Event("Event Title", "Event Description", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted());
        event.setId(1234);
        eventDao.update(event);
        List<Event> events = eventDao.getAllEvents();
        assertEquals(0, events.size());
    }

    @Test
    public void testDeleteEvent_Success() {
        Event event = new Event("Event Title", "Event Description", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted());
        eventDao.insert(event);
        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.size() > 0);

        eventDao.delete(events.get(0));
        List<Event> updatedEvents = eventDao.getAllEvents();
        assertEquals(0, updatedEvents.size());
    }

    @Test
    public void testDeleteEvent_Failure() {
        Event event = new Event("Event Title", "Event Description", "01-08-2023", "14-01-1445", getCurrentDateTimeFormatted());
        eventDao.delete(event);
        List<Event> events = eventDao.getAllEvents();
        assertEquals(0, events.size());
    }

    public static String getCurrentDateTimeFormatted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(calendar.getTime());
    }

}
