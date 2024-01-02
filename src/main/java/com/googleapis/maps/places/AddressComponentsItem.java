package com.googleapis.maps.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponentsItem {

	@JsonProperty("types")
	private List<String> types;

	@JsonProperty("long_name")
	private String longName;

	@JsonProperty("short_name")
	private String shortName;

	@JsonProperty("languageCode")
	private String languageCode;

	public AddressComponentsItem() {
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Override
	public String toString() {
		return "AddressComponentsItem{" +
				"longName='" + longName + '\'' +
				", shortName='" + shortName + '\'' +
				", types=" + types +
				'}';
	}
}