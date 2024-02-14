package com.glynisf.eventtracker.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.Location;
import com.glynisf.eventtracker.entity.User;
import com.glynisf.eventtracker.persistence.GenericDao;
import lombok.SneakyThrows;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/locations")
public class Locations {
	private final ExecutorService executorService = Executors.newFixedThreadPool(15);


	private GenericDao locationDao;
	private GenericDao eventDao;
	private GenericDao userDao;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public Locations() {
		locationDao = new GenericDao(Location.class);
		eventDao = new GenericDao(Event.class);
		userDao = new GenericDao(User.class);
	}

	@SneakyThrows
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLocation(Location location) {
		String name = location.getName();
		String phoneNumber = location.getPhoneNumber();
		String address = location.getAddress();
		String apartment = location.getApartment();
		String city = location.getCity();
		String state = location.getState();
		String zip = location.getZip();
		Boolean wheelchairAccessibleEntrance = location.isWheelchairAccessibleEntrance();
		String placeId = location.getPlaceId();
		String website = location.getWebsite();

		Event event = (Event) eventDao.getById(4);
		location = new Location(name, phoneNumber, address, apartment, city, state, zip, wheelchairAccessibleEntrance, website, event , placeId);

		int id = locationDao.insert(location);
		event.addLocation(location);

		List<Location> locationList = new ArrayList<>();
		locationList.add(location);

		return Response.ok(location).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{locationId}")
	public CompletableFuture<Response> updateDetailsAsync(
			@PathParam("locationId") int id,
			Location updatedLocation) {

		return CompletableFuture.supplyAsync(() -> {
			try {
				Location location = (Location) locationDao.getById(id);
				location.setName(updatedLocation.getName());
				location.setPhoneNumber(updatedLocation.getPhoneNumber());
				location.setAddress(updatedLocation.getAddress());
				location.setApartment(updatedLocation.getApartment());
				location.setCity(updatedLocation.getCity());
				location.setState(updatedLocation.getState());
				location.setZip(updatedLocation.getZip());
				location.setWebsite(updatedLocation.getWebsite());
				location.setWheelchairAccessibleEntrance(Boolean.valueOf((updatedLocation.isWheelchairAccessibleEntrance())));
				locationDao.saveOrUpdate(location);

				String locationJson = objectMapper.writeValueAsString(location);
				System.out.println(location);

				return Response.ok(locationJson).build();
			} catch (Exception e) {
				// Handle exceptions if needed
				return Response.serverError().entity(e.getMessage()).build();
			}
		}, executorService);
	}
}