package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.eventtracker.util.PropertiesLoader;
import com.googleapis.maps.PlacesSearch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Properties;


public class PlacesSearchDao implements PropertiesLoader {

    private Properties properties;
    private final Logger logger = (Logger) LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new Weather dao.
     */
    public PlacesSearchDao() {
        properties = new Properties(loadProperties("/googleapi.properties"));
    }

    /**
     * Gets response.
     *
     * @return the response
     * @throws IOException the io exception
     */
    public PlacesSearch getResponse() throws IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=High%20Noon%20Saloon%20Madison&types=establishment&key=AIzaSyAycyEBkbwSQhw-ePUXhldhdpSz3XztlvU");
        String response = (target.request(MediaType.APPLICATION_JSON).get(String.class));
        ObjectMapper mapper = new ObjectMapper();

        PlacesSearch placesSearch = null;

        try {
            // handles unrecognized property exception
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            placesSearch = mapper.readValue(response, PlacesSearch.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            logger.debug(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return placesSearch;
    }
}