package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googleapis.maps.Places;
import com.googleapis.maps.PlacesSearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class PlacesDao {
    private final Logger logger = (Logger) LogManager.getLogger(this.getClass());

    public Places getPlaceWithIdResponse() throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://places.googleapis.com/v1/places/ChIJAyWDx2tTBogRW7GNdv81-qE?fields=id,displayName,address_components,name&key=AIzaSyAycyEBkbwSQhw-ePUXhldhdpSz3XztlvU");
        String response = (target.request(MediaType.APPLICATION_JSON).get(String.class));
        ObjectMapper mapper = new ObjectMapper();

        Places places = null;

        try {
            // handles unrecognized property exception
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            places = mapper.readValue(response, Places.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            logger.debug(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return places;
    }
}