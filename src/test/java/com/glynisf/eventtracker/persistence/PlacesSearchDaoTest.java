package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.PlacesSearchDao;
import com.googleapis.maps.PlacesSearch;
import com.googleapis.maps.PredictionsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlacesSearchDaoTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test
    void getPlacesSearchLocationResponseSuccess() throws IOException {
        PlacesSearchDao placesDao = new PlacesSearchDao();
        PlacesSearch search = placesDao.getResponse();

        List<PredictionsItem> itemList = search.getPredictions();

        assertEquals("ChIJAyWDx2tTBogRW7GNdv81-qE", itemList.get(0).getPlaceId());
    }

}