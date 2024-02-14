package com.glynisf.eventtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.eventtracker.entity.Artist;
import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.Location;
import com.glynisf.eventtracker.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@WebServlet (
		urlPatterns = {"/edit"}
)
public class EditEvent extends HttpServlet {
	private ObjectMapper mapper;
	private Event event;
	private GenericDao<Event, Serializable> eventDao;

	@Override
	public void init() throws ServletException {
		super.init();
		mapper = new ObjectMapper();
		eventDao = new GenericDao<>(Event.class);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("eventId"));
		event = getArtistByEventId(id);
		Set<Artist> artists = getArtistSetFromEvent(event);
		Set<Location> location = event.getLocations();

		String jsonData = mapper.writeValueAsString(artists);
		HttpSession session = request.getSession();
		session.setAttribute("artists", artists);
		session.setAttribute("events", event);
		session.setAttribute("locations", location);
		request.setAttribute("artists", artists);
		request.setAttribute("eventId", id);

		RequestDispatcher dispatcher = request.getRequestDispatcher("location.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Read the response body
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
			String line;
			StringBuilder responseBody = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				responseBody.append(line);
			}
			// Deserialize the JSON response into an Artist object
			Artist artist = mapper.readValue(responseBody.toString(), Artist.class);
			event = getArtistByEventId(artist.getId());
			Set<Artist> artists = event.getArtists();
			// Print or process the Artist object
			System.out.println("Received Artist: " + artist);
			System.out.println("Set of Artist: " + artists);

			// Your logic here...
			// Send a response
			response.getWriter().write("Artist Received Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Error handling the response");
		}
	}

	public Event getArtistByEventId(int eventId) {
		if (eventId > 0) {
			return eventDao.getById(eventId);
		} else {
			return null;
		}
	}

	public Set<Artist> getArtistSetFromEvent(Event event) {
		if (event != null) {
			Set<Artist> artists = event.getArtists();
			return artists;
		} else {
			return Collections.emptySet();
		}
	}
}