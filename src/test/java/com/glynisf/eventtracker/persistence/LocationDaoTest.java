package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.Location;

import com.glynisf.test.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class LocationDaoTest {

    GenericDao eventDao;

    GenericDao locationDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new NotebookDao.
     */
    @BeforeEach
    void setUp() {
        locationDao = new GenericDao(Location.class);
        eventDao = new GenericDao(Event.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    @Test
    void getAllLocationSuccess() {
        List<Location> locations = locationDao.getAll();
        assertNotNull(locations);
        assertEquals(21, locations.size());
    }

    @Test
    void getLocationByIdSuccess() {
        Location locationById = (Location) locationDao.getById(4);
        assertNotNull(locationById);
        assertTrue(locationById.getName().equals("Smart Bar"));
        assertTrue(locationDao.getById(4).equals(locationById));
    }

    @Test
    void insertLocationSuccess() {
        Event event = (Event) eventDao.getById(1);
        Location newLocationNotebook = new Location("The Orpheum", "608-222-0989", "3001 State st", null, "Madison", "WI", "53704" , true, "https://www.orpheum.com", event, "18375766-GFx-47");
        int id = locationDao.insert(newLocationNotebook);
        event.addLocation(newLocationNotebook);

        assertTrue(event.getLocations().contains(newLocationNotebook));
        assertTrue(true, String.valueOf(locationDao.getById(12).equals(newLocationNotebook)));

        assertTrue(locationDao.getByPropertyLike("locationName", "The Orpheum").contains(newLocationNotebook));

        assertNotEquals(0,id);
    }

    @Test
    void deleteSuccess() {
        locationDao.delete(locationDao.getById(3));
        assertNull(locationDao.getById(3));
    }

    @Test
    void deleteUserFromLocationSuccess() {
        Event event = (Event) eventDao.getById(1);
        eventDao.delete(event);
        assertNull(eventDao.getById(1));

        assertNull(locationDao.getById(1));
    }
}