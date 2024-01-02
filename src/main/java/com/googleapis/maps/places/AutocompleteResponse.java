package com.googleapis.maps.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutocompleteResponse {

	@JsonProperty("predictions")
	private AutocompletePrediction[] predictions;

	@JsonProperty("status")
	private String status;

	public AutocompletePrediction[] getPredictions() {
		return predictions;
	}

	public void setPredictions(AutocompletePrediction[] predictions) {
		this.predictions = predictions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AutocompleteResponse{" +
				"predictions=" + Arrays.toString(predictions) +
				", status='" + status + '\'' +
				'}';
	}
}