package com.glynisf.eventtracker.app;

import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.EventDetails;
import com.glynisf.eventtracker.entity.Location;
import com.glynisf.eventtracker.entity.Notebook;
import com.glynisf.eventtracker.persistence.GenericDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Path("/events")
public class Events {

	private GenericDao<Event, Serializable> eventDao;
	private GenericDao<Notebook, Serializable> notebookDao;
	private GenericDao<EventDetails, Serializable> detailsDao;
	private GenericDao<Location, Serializable> locationDao;
	private final ExecutorService executorService = Executors.newFixedThreadPool(15);

	@Context
	HttpSession session;

	@Context
	HttpServletRequest request;

	public Events() {
		eventDao = new GenericDao<>(Event.class);
		notebookDao = new GenericDao<>(Notebook.class);
		detailsDao = new GenericDao<>(EventDetails.class);
		locationDao = new GenericDao<>(Location.class);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotebookEvents(@PathParam("id") int id) {
		Event event = eventDao.getById(id);
		Set<EventDetails> eventDetails = event.getEventDetails();
		return Response.ok(eventDetails).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{eventName}")
	public Response addEvent(@PathParam("eventName") String eventName, Event event) {
		int eventId = eventDao.insert(event);
		Notebook notebook = new Notebook();
		notebook.addEvent(event);
		session = request.getSession();
		session.setAttribute("event", event);
		return Response.ok(event).build();
	}


	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update/{eventId}")
	public CompletableFuture<Response> updateEventAsync(
			@PathParam("eventId") int eventId,
			@FormParam("eventName") String eventName) {

		return CompletableFuture.supplyAsync(new Supplier<Response>() {
			@Override
			public Response get() {
				try {
					// Simulate asynchronous task
					Thread.sleep(2000); // Replace this with your actual asynchronous task

					// Original code
					Event event = eventDao.getById(eventId);
					event.setEventName(eventName);
					eventDao.saveOrUpdate(event);
					System.out.println("UPDATE/{eventId} METHOD: " + event);

					return Response.ok(event).build();
				} catch (InterruptedException e) {
					return Response.serverError().entity(e.getMessage()).build();
				}
			}
		}, executorService);
	}


	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/details/{detailsId}")
	public CompletableFuture<Response> updateDetailsAsyncEndpoint(
			@PathParam("detailsId") int id,
			@FormParam("eventDate") String eventDate,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime) {

		return CompletableFuture.supplyAsync(() -> {
			try {
				EventDetails details = (EventDetails) detailsDao.getById(id);
				details.setEventDate(LocalDate.parse(eventDate));
				details.setStartTime(LocalTime.parse(startTime));
				details.setEndTime(LocalTime.parse(endTime));
				System.out.println("DETAILS/{detailsId} METHOD: " + details);
				detailsDao.saveOrUpdate(details);
				return Response.ok(details).build();
			} catch (Exception e) {
				// Handle exceptions if needed
				return Response.serverError().entity(e.getMessage()).build();
			}
		}, executorService);
	}
}