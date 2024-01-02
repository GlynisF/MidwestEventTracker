package com.googleapis.maps.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

	@JsonProperty("address_components")
	private List<AddressComponentsItem> addressComponents;

	@JsonProperty("formatted_phone_number")
	private String formattedPhoneNumber;

	@JsonProperty("name")
	private String name;

	@JsonProperty("website")
	private String website;

	@JsonProperty("wheelchair_accessible_entrance")
	private Boolean wheelchairAccessibleEntrance;

	@JsonProperty("place_id")
	private String placeId;



	public Result() {

	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}


	public Boolean getWheelchairAccessibleEntrance() {
		return wheelchairAccessibleEntrance;
	}

	public void setWheelchairAccessibleEntrance(Boolean wheelchairAccessibleEntrance) {
		this.wheelchairAccessibleEntrance = wheelchairAccessibleEntrance;
	}

	public List<AddressComponentsItem> getAddressComponents() {
		return addressComponents;
	}

	public void setAddressComponents(List<AddressComponentsItem> addressComponents) {
		this.addressComponents = addressComponents;
	}
	

	public String getFormattedPhoneNumber() {
		return formattedPhoneNumber;
	}

	public void setFormattedPhoneNumber(String formattedPhoneNumber) {
		this.formattedPhoneNumber = formattedPhoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "Result{" +
				"addressComponents=" + addressComponents +
				", formattedPhoneNumber='" + formattedPhoneNumber + '\'' +
				", name='" + name + '\'' +
				", website='" + website + '\'' +
				", wheelchairAccessibleEntrance=" + wheelchairAccessibleEntrance +
				", placeId='" + placeId + '\'' +
				'}';
	}

}