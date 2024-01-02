package com.googleapis.maps.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.net.URL;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetails {
	@JsonProperty("name")
	private String name;

	@JsonProperty("icon")
	private String icon;

	@JsonProperty("url")
	private URL url;

	@JsonProperty("formatted_address")
	private String formattedAddress;

	@JsonProperty("formatted_phone_number")
	private String phoneNumber;

	@JsonProperty("address_component_item")
	private List<String> addressItem;

	@JsonProperty("wheelchair_accessible_entrance")
	public Boolean wheelchairAccessibleEntrance;

	public PlaceDetails(List<String> addressItem) {this.addressItem = addressItem;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getAddressItem() {
		return addressItem;
	}


	public void setAddressItem(List<String> addressItem) {
		this.addressItem = addressItem;
	}

	public Boolean getWheelchairAccessibleEntrance() {
		return wheelchairAccessibleEntrance;
	}

	public void setWheelchairAccessibleEntrance(Boolean wheelchairAccessibleEntrance) {
		this.wheelchairAccessibleEntrance = wheelchairAccessibleEntrance;
	}

	@Override
	public String toString() {
		return "PlaceDetails{" +
				"name='" + name + '\'' +
				", icon='" + icon + '\'' +
				", url=" + url +
				", formattedAddress='" + formattedAddress + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", addressItem=" + addressItem +
				'}';
	}
}