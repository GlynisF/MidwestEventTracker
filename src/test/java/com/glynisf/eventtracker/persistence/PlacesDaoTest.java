package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.PlacesDao;
import com.googleapis.maps.AddressComponentsItem;
import com.googleapis.maps.Places;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlacesDaoTest {

    @Test
    void getPlaceWithIdResponseSuccess() throws IOException {

        PlacesDao placesDao = new PlacesDao();
        Places places = placesDao.getPlaceWithIdResponse();

        List<AddressComponentsItem> itemList = places.getAddressComponents();

        assertEquals("E Washington Ave", itemList.get(1).getShortText());
        assertEquals("Madison", itemList.get(3).getShortText());
        assertEquals("WI", itemList.get(5).getShortText());
        assertEquals("US", itemList.get(6).getShortText());
        assertEquals("53703", itemList.get(7).getShortText());
        assertEquals("High Noon Saloon", places.getDisplayName().getText());

    }
}