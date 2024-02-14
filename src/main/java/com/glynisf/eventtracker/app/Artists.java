package com.glynisf.eventtracker.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glynisf.eventtracker.entity.Artist;
import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/artists")
public class Artists {
	private final ExecutorService executorService = Executors.newFixedThreadPool(15);

	private GenericDao<Artist, Serializable> artistDao;
	private GenericDao<Event, Serializable> eventDao;


	public Artists() {
		artistDao = new GenericDao<>(Artist.class);
		eventDao = new GenericDao<>(Event.class);

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{artistId}")
	public CompletableFuture<Response> editArtist(
			@PathParam("artistId") int id,
			Artist updatedArtist) {
ObjectMapper mapper = new ObjectMapper();
		return CompletableFuture.supplyAsync(() -> {
			try {
				Artist artist = artistDao.getById(id);
				// Check for null values in updatedArtist and update only non-null properties
				if (updatedArtist.getFirstName() != null) {
					artist.setFirstName(updatedArtist.getFirstName());
				}
				if (updatedArtist.getLastName() != null) {
					artist.setLastName(updatedArtist.getLastName());
				}
				if (updatedArtist.getMoniker() != null) {
					artist.setMoniker(updatedArtist.getMoniker());
				}
				if (updatedArtist.getEmail() != null) {
					artist.setEmail(updatedArtist.getEmail());
				}
				if (updatedArtist.getBookingFee() != null) {
					artist.setBookingFee(Double.valueOf(updatedArtist.getBookingFee()));
				}
				// Update artist
				artistDao.saveOrUpdate(artist);
				// Log the updated artist
				System.out.println(artist);
				String artistJson = mapper.writeValueAsString(artist);
				return Response.ok(artistJson).build();
			} catch (Exception e) {
				e.printStackTrace(); // Log the exception for debugging
				return Response.serverError().entity(e.getMessage()).build();
			}
		}, executorService);
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{eventId}")
	public CompletableFuture<Response> getArtist(
			@PathParam("eventId") int id) {
	ObjectMapper mapper =  new ObjectMapper();
		return CompletableFuture.supplyAsync(() -> {
			try {
				Event event = eventDao.getById(id);
				Set<Artist> artists =  event.getArtists();
				// Log the updated artist
				String json = mapper.writeValueAsString(artists);
				System.out.println(artists);

				return Response.ok(json).build();
			} catch (Exception e) {
				e.printStackTrace(); // Log the exception for debugging
				return Response.serverError().entity(e.getMessage()).build();
			}
		}, executorService);
	}

}