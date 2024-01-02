package com.glynisf.eventtracker.app;

import com.glynisf.eventtracker.entity.PlacesSearchDao;
import com.google.maps.model.AutocompletePrediction;
import lombok.SneakyThrows;


import java.util.List;
import java.util.concurrent.Callable;

public class AutocompleteCallable implements Callable<AutocompletePrediction[]> {

	private String input;
	private PlacesSearchDao searchDao;

	public AutocompleteCallable(String input) {
		this.input = input;
		this.searchDao = new PlacesSearchDao(input);
	}

	@Override
	@SneakyThrows
	public AutocompletePrediction[] call() {
		try {
			AutocompletePrediction[] predictions = searchDao.placeAutocompleteRequest(input);
			return predictions;
		} catch (Exception e) {
			// Log the exception for debugging
			e.printStackTrace();
			return null;
		}
	}
}