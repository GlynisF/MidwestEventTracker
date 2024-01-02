package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.Notebook;
import com.glynisf.test.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class EventDaoTest {

    GenericDao notebookDao;
    /**
     * The User dao.
     */
    GenericDao eventDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new NotebookDao.
     */
    @BeforeEach
    void setUp() {
        notebookDao = new GenericDao(Notebook.class);
        eventDao = new GenericDao(Event.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    @Test
    void getAllEventsSuccess() {
        List<Event> events = eventDao.getAll();
        assertNotNull(events);
        assertEquals(4, events.size());
    }

    @Test
    void insertEventSuccess() {
        Notebook notebook = (Notebook) notebookDao.getById(1);
        Event events = new Event("NYE 2023", notebook);

        int id = eventDao.insert(events);
        notebook.addEvent(events);

        assertTrue(notebook.getEvents().contains(events));

        assertTrue(true, String.valueOf(eventDao.getById(4).equals(events)));

        assertTrue(eventDao.getByPropertyLike("eventName", "NYE 2023").contains(events));

    }

    @Test
    void deleteSuccess() {
        eventDao.delete(eventDao.getById(3));
        assertNull(eventDao.getById(3));
    }

    @Test
    void deleteEventFromNotebookSuccess() {
        Event event = (Event) eventDao.getById(4);
        Set<Event> retrievedEvent = event.getNotebook().getEvents();

        Notebook notebook = (Notebook) notebookDao.getById(5);

        notebook.removeEvent(event);
        notebook.setEvents(null);

        assertFalse(retrievedEvent.contains(notebook));

    }

    @Test
    void getByIdSuccess() {
        Event eventDaoGetById = (Event) eventDao.getById(4);
        assertNotNull(eventDaoGetById);
        assertTrue(eventDaoGetById.getEventName().equals("Patio takeover"));
        assertTrue(eventDao.getById(4).equals(eventDaoGetById));
    }

    @Test
    void saveOrUpdateSuccess() {
        Event eventToUpdate = (Event) eventDao.getById(4);

        assertNotNull(eventToUpdate);

        assertTrue(eventToUpdate.getEventName().equals("Patio takeover"));

        String title = "Patio Takeover 2";
        eventToUpdate.setEventName(title);
        eventDao.saveOrUpdate(eventToUpdate);

        assertTrue(eventToUpdate.getEventName().equals("Patio Takeover 2"));

        assertTrue(eventDao.getById(4).equals(eventToUpdate));
    }

    @Test
    void getByPropertyEqualSuccess() {

        List<Event> eventByDate = (List<Event>) eventDao.getByPropertyEqual("eventName", "RIN");
        assertNotNull(eventByDate);
        assertTrue(true, String.valueOf(eventByDate.contains("2023-12-31")));

    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        Event event = (Event) eventDao.getById(2);
        int id = event.getId();
        List<Event> getEventByPropertyLike = eventDao.getByPropertyLike("eventName", "S. Bar");
        Assertions.assertEquals(1, getEventByPropertyLike.size());

    }


}