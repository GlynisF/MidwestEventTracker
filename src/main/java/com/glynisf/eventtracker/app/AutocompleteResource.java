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
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/autocomplete")
public class AutocompleteResource {

	private final ExecutorService executorService = Executors.newFixedThreadPool(15);
	private PlacesSearchDao searchDao;

	@Inject
	public AutocompleteResource(@Context PlacesSearchDao searchDao) {
		this.searchDao = new PlacesSearchDao("");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CompletableFuture<Response> autocomplete(@QueryParam("userInput") String userInput) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				PlacesSearchDao searchDao = new PlacesSearchDao(userInput);
				AutocompletePrediction[] predictions = searchDao.placeAutocompleteRequest(userInput);
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonPredictions = objectMapper.writeValueAsString(predictions);
				System.out.println(jsonPredictions);
				return Response.ok(jsonPredictions).build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
		}, executorService);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{placeId}")
	public CompletableFuture<Response> getPlaceDetails(@PathParam("placeId") String placeId) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				PlacesDao placesDao = new PlacesDao(searchDao, new Places());
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, String> map = placesDao.getPlaceDetailsResultsMap(placeId);
				String jsonMap = objectMapper.writeValueAsString(map);
				return Response.ok(jsonMap).build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().build();
			}
		}, executorService);
	}
}