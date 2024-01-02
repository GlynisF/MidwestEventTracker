/**package com.glynisf.eventtracker.entity;

import com.glynisf.eventtracker.entity.PlacesSearchDao;
import com.glynisf.eventtracker.persistence.SessionFactoryProvider;
import com.googleapis.maps.PlacesSearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

class PlacesSearchDaoTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    private PlacesSearchDao searchDao;
    private PlacesSearch search;

    @BeforeEach
    void setup() throws IOException {
        searchDao = new PlacesSearchDao();
        search = searchDao.citySearchResponse("HighNoonSaloonMadison");
    }

    @Test
    void cityPlaceSearchSuccess() throws IOException {
        PlacesSearch search = searchDao.citySearchResponse("HighNoonSaloonMadison");
        assertNotNull(search);
    }
    //@Test
    /**void getPlaceIdFromSearch() throws IOException {
        List<AutocompletePrediction> predictionsList = search.getPredictions();
           String id = predictionsList.get(0).getPlaceId();
           assertEquals(search.getPredictions().get(0).getPlaceId(), id);
    }*/