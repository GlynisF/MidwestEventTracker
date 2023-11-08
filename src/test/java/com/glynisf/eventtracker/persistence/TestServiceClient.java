package com.glynisf.eventtracker.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import com.glynisf.swapi.Planet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestServiceClient {

    @Test
    public void testswapiJSON() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target =
                client.target("https://swapi.dev/api/planets/1");
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        // how we specify what we want to map and to where
        ObjectMapper mapper = new ObjectMapper();
        Planet planet = mapper.readValue(response, Planet.class);
        assertEquals("Tatooine", planet.getName());
    }
}