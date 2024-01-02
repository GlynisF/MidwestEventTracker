package com.glynisf.eventtracker.controller;

import com.glynisf.eventtracker.entity.PlacesSearchDao;
import com.glynisf.eventtracker.util.PropertiesLoader;


import com.google.maps.PlaceAutocompleteRequest;


import com.googleapis.maps.places.AddressComponent;
import com.googleapis.maps.places.AddressComponentsItem;

import com.googleapis.maps.places.Places;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import java.io.IOException;

import java.util.*;


public class PlacesDao implements PropertiesLoader, MapObject {

	final Logger logger = (Logger) LogManager.getLogger(this.getClass());
	private final Properties properties;
	private PlacesSearchDao searchDao;
	private Places places;
	private Map<String, String> addressMap;


	public PlacesDao(PlacesSearchDao searchDao, Places places) {
		this.properties = new Properties(loadProperties("/google.api.properties"));
		this.searchDao = searchDao;
		this.places = places;
		this.addressMap = Collections.synchronizedMap(new HashMap<>());

	}

	public Places getPlaceWithIdResponse(String placeId) throws IOException {
		Client client = ClientBuilder.newClient();
		PlaceAutocompleteRequest.SessionToken token = searchDao.getToken();
		WebTarget target = client.target(properties.getProperty("base.uri"))
				.queryParam("sessionToken", token)
				.queryParam("place_id", placeId)
				.queryParam("fields", properties.getProperty("details.field.params"))
				.queryParam("key", properties.getProperty("api.key"));

		ObjectMapperHelper mapper = new ObjectMapperHelper();

		String response = mapper.getApiResponse(target, String.class);

		return places = mapper.convertJsonToJava(response, Places.class);
	}

	public Map<String, String> getAddressDetails(String placeId) throws IOException {
		for (AddressComponentsItem item : getPlaceWithIdResponse(placeId).getResult().getAddressComponents()) {
			List<String> types = item.getTypes();
			for (String type : types) {
				switch (type) {
					case AddressComponent.STREET_NUMBER:
						addressMap.put("street_number", item.getLongName());
						break;
					case AddressComponent.ROUTE:
						addressMap.put("route", item.getLongName());
						break;
					case AddressComponent.LOCALITY:
						addressMap.put("city", item.getLongName());
						break;
					case AddressComponent.ADMIN_AREA_LEVEL_1:
						addressMap.put("state", item.getShortName());
						break;
					case AddressComponent.POSTAL_CODE:
						addressMap.put("zip", item.getShortName());
						break;
					// Add other cases as needed
				}
			}
		}
		return addressMap;
	}

	public Map<String, String> getPlaceDetailsResultsMap(String placeId) throws IOException {
		addressMap = getAddressDetails(placeId);
		addressMap.put("address", addressMap.get("street_number") + " " + addressMap.get("route"));
		addressMap.put("name", places.getResult().getName());
		addressMap.put("phone_number", places.getResult().getFormattedPhoneNumber());
		addressMap.put("website", places.getResult().getWebsite());
		addressMap.put("wheelchair_accessible_entrance", String.valueOf(places.getResult().getWheelchairAccessibleEntrance()));
		addressMap.put("place_id", placeId);
		return addressMap;
	}

}