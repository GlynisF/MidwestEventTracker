package com.glynisf.eventtracker.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.Location;
import com.glynisf.eventtracker.entity.User;
import com.glynisf.eventtracker.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/location")
public class Locations {

	private GenericDao locationDao;
	private GenericDao eventDao;
	private GenericDao userDao;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public Locations() {
		locationDao = new GenericDao(Location.class);
		eventDao = new GenericDao(Event.class);
		userDao = new GenericDao(User.class);
	}

	@GET
	@Path("/{id}")
	public Event getEvent(@PathParam("id") String id) {
		Event event = (Event) eventDao.getById(id);
		return event;
	}



	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLocation(@FormParam("name") String name,
	                            @FormParam("phone_number") String phoneNumber,
	                            @FormParam("address") String address,
	                            @FormParam("apartment") String apartment,
	                            @FormParam("city") String city,
	                            @FormParam("state") String state,
	                            @FormParam("zip") String zip,
	                            @FormParam("website") String website,
	                            @FormParam("wheelchair_accessible_entrance") boolean wheelchairAccessibleEntrance,
	                            @FormParam("place_id") String placeId) {

		System.out.println(address);

		Event event = (Event) eventDao.getById(4);
		Location location = new Location(name, phoneNumber, address, apartment, city, state, zip, wheelchairAccessibleEntrance, website, event , placeId);
		int id = locationDao.insert(event);
		return Response.ok(location).build();
	}
}