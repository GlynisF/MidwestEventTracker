package com.glynisf.eventtracker.entity;

import com.glynisf.eventtracker.controller.MapObject;
import com.glynisf.eventtracker.util.PropertiesLoader;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;


import java.io.IOException;
import java.util.*;


public class PlacesSearchDao implements PropertiesLoader, MapObject {

	private final Logger logger = (Logger) LogManager.getLogger(this.getClass());
	private Properties properties;
	private String input;

	private PlaceAutocompleteRequest.SessionToken token;
	private GeoApiContext context;
	private List<AutocompletePrediction> predictions;

	public PlacesSearchDao() {

	}

	public PlacesSearchDao(String input) {
		this.input = input;
		this.properties = new Properties(loadProperties("/google.api.properties"));
		this.token = new PlaceAutocompleteRequest.SessionToken();
		this.context = new GeoApiContext.Builder().apiKey(properties.getProperty("api.key")).build();
		this.predictions = Collections.synchronizedList(new ArrayList<>());

	}

	public void setToken(PlaceAutocompleteRequest.SessionToken token) {
		this.token = token;
	}

	public PlaceAutocompleteRequest.SessionToken getToken() {
		return token;
	}


	public AutocompletePrediction[] placeAutocompleteRequest(String input) {
		try {
			AutocompletePrediction[] fetchedPredictions = PlacesApi.placeAutocomplete(context, input, token)
					.types(PlaceAutocompleteType.ESTABLISHMENT)
					.components(ComponentFilter.country("US"))
					.offset(input.length())
					.header("sessionToken", String.valueOf(token))
					.await();
			// Synchronize access to the predictions list
			synchronized (predictions) {
				predictions.addAll(Arrays.asList(fetchedPredictions));
				System.out.println(token);
			}
		} catch (ApiException | InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}
			// Convert synchronized list to array for return
			return predictions.toArray(new AutocompletePrediction[0]);
		}


	}