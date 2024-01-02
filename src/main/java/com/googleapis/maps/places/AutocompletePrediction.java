package com.googleapis.maps.places;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutocompletePrediction {

	@JsonProperty("reference")
	private String reference;

	@JsonProperty("description")
	private String description;

	@JsonProperty("place_id")
	private String placeId;

	// Other fields as needed...

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	// Getter and setter methods for other fields...

	@Override
	public String toString() {
		return "AutocompletePrediction{" +
				"reference='" + reference + '\'' +
				", description='" + description + '\'' +
				", placeId='" + placeId + '\'' +
				'}';
	}
}