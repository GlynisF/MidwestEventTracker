package com.glynisf.eventtracker.app;

import com.glynisf.eventtracker.entity.*;
import com.glynisf.eventtracker.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.*;



@Path("/notebooks")
public class Notebooks {
	private final Logger logger = LogManager.getLogger(this.getClass());

	private GenericDao<User, Serializable> userDao;

	private GenericDao<Notebook, Serializable> notebookDao;

	@Context
	HttpServletRequest req;

	@Context
	HttpSession session;

	public Notebooks() {
		this.userDao = new GenericDao(User.class);
		this.notebookDao = new GenericDao(Notebook.class);

	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addNotebook(@FormParam("title") String title) {
		User user = (User) req.getSession().getAttribute("user");
		Notebook notebook = new Notebook(0, title, user);
		int notebookId = notebookDao.insert(notebook);
		user.addNotebook(notebook);
		session = req.getSession();
		session.setAttribute("notebook", notebook);
		logger.info("session in notebook rest end " + session.getAttribute("notebook"));
		return Response.ok(notebook).build();

	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotebooks() {
		User user = (User) req.getSession().getAttribute("user");
		Set<Notebook> notebooks = user.getNotebooks();
		Map<Integer, String> notebookMap = new HashMap<>();
		for(Notebook notebook : notebooks) {
			System.out.println(notebook.getId());
			System.out.println(notebook.getTitle());
			notebookMap.put(notebook.getId(), notebook.getTitle());
		}
		return Response.ok(notebookMap).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getNotebookEvents(@PathParam("id") int id) {
		Notebook notebook = (Notebook) notebookDao.getById(id);
		Set<Event> events = notebook.getEvents();

		// Create a Map to represent the JSON structure
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("notebookId", notebook.getId());
		responseMap.put("notebookTitle", notebook.getTitle());

		// Convert the set of events to a List (or any other collection type) to maintain order
		List<Event> eventList = new ArrayList<>(events);


		for(Event event : eventList) {
			responseMap.put("events", eventList);
			Set<Location> locations = event.getLocations();
			List<Location> locationList = new ArrayList<>(locations);
			Set<EventDetails> details = event.getEventDetails();
			List<EventDetails> detailsList = new ArrayList<>(details);
			Set<Artist> artists = event.getArtists();
			List<Artist> artistList = new ArrayList<>(artists);
			responseMap.put("artists", artistList);
			System.out.println(locationList.toString());
			responseMap.put("details", detailsList);
			responseMap.put("locations", locationList);

		}
		System.out.println(responseMap);
		return Response.ok(responseMap).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/details/{id}")
	public Response getEventDetails(@PathParam("id") int id)  {
		GenericDao<Event, Serializable> eventDao = new GenericDao<>(Event.class);
		Event event = eventDao.getById(id);

		if(event != null) {
			Set<EventDetails> details = new HashSet<>(event.getEventDetails());
			List<EventDetails> detailsList = new ArrayList<>(details);
			return Response.ok(detailsList).build();

		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}
}