package com.glynisf.eventtracker.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.eventtracker.controller.PlacesDao;
import com.glynisf.eventtracker.entity.PlacesSearchDao;
import com.google.maps.model.AutocompletePrediction;
import com.googleapis.maps.places.Places;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/autocomplete")
public class AutocompleteResource {

	private final ExecutorService executorService = Executors.newFixedThreadPool(10);
	private PlacesSearchDao searchDao;

	@Inject
	public AutocompleteResource(@Context PlacesSearchDao searchDao) {
		this.searchDao = new PlacesSearchDao("");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response autocomplete(@QueryParam("userInput") String userInput) {
		try {
			CompletableFuture<AutocompletePrediction[]> autocompleteFuture = CompletableFuture.supplyAsync(() -> {
				try {
					PlacesSearchDao searchDao = new PlacesSearchDao(userInput); // You might want to inject this dependency.
					AutocompletePrediction[] predictions = searchDao.placeAutocompleteRequest(userInput);
					return predictions;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}, executorService);

			AutocompletePrediction[] predictions = autocompleteFuture.get();
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonPredictions = objectMapper.writeValueAsString(predictions);
			System.out.println(jsonPredictions);
			return Response.ok(jsonPredictions).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{placeId}")
	public Response getPlaceDetails(@PathParam("placeId") String placeId) throws IOException {
		PlacesDao placesDao = new PlacesDao(searchDao, new Places());
		Places place = placesDao.getPlaceWithIdResponse(placeId);
		Map<String, String> map = placesDao.getPlaceDetailsResultsMap(placeId);
		ObjectMapper objectMapper = new ObjectMapper();
			String jsonMap = objectMapper.writeValueAsString(map);
		return Response.ok(map).build();

	}

}