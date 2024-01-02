package com.googleapis.maps.places;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressComponent {

	public static final String STREET_NUMBER = "street_number";
	public static final String ROUTE = "route";
	public static final String LOCALITY = "locality";
	public static final String ADMIN_AREA_LEVEL_2 = "administrative_area_level_2";
	public static final String ADMIN_AREA_LEVEL_1 = "administrative_area_level_1";
	public static final String COUNTRY = "country";
	public static final String POSTAL_CODE = "postal_code";

	private String longName;
	private List<String> types;

	@JsonProperty("address_components_item")
	private AddressComponentsItem addressComponentsItem;

	public AddressComponent(
			@JsonProperty("long_name") String longName,
			@JsonProperty("types") List<String> types,
			@JsonProperty("address_components_item") AddressComponentsItem addressComponentsItem
	) {
		this.longName = longName;
		this.types = types;
		this.addressComponentsItem = addressComponentsItem;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public AddressComponentsItem getAddressComponentsItem() {
		return addressComponentsItem;
	}

	public void setAddressComponentsItem(AddressComponentsItem addressComponentsItem) {
		this.addressComponentsItem = addressComponentsItem;
	}

	public Map<String, String> getAddressComponentTypes() {
		Map<String, String> typeMap = new HashMap<>();
		if (types != null) {
			for (String type : types) {
				typeMap.put(type, getTypeName(type));
			}
		}
		return typeMap;
	}

	public String getTypeName(String type) {
		switch (type) {
			case STREET_NUMBER:
				return "Street Number";
			case ROUTE:
				return "Route";
			case LOCALITY:
				return "Locality";
			case ADMIN_AREA_LEVEL_2:
				return "Admin Area Level 2";
			case ADMIN_AREA_LEVEL_1:
				return "Admin Area Level 1";
			case COUNTRY:
				return "Country";
			case POSTAL_CODE:
				return "Postal Code";
			default:
				return "Unknown Type";
		}
	}

	@Override
	public String toString() {
		return "AddressComponent{" +
				"longName='" + longName + '\'' +
				", types=" + types +
				", addressComponentsItem=" + addressComponentsItem +
				'}';
	}
}