package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Event")
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    @Column(name = "event_id")
    private int id;

    @Column(name = "event_name")
    private String eventName;

    @ManyToOne
    @JoinColumn(name = "notebook_id",
		    foreignKey = @ForeignKey(name = "notebook_id") )
    private Notebook notebook;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Location> locations = new HashSet<Location>();

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<EventDetails> eventDetails;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Artist> artists;

    public Event() {

    }

    public Event(String eventName, Notebook notebook) {
        this.eventName = eventName;
        this.notebook = notebook;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

	public Set<EventDetails> getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(Set<EventDetails> eventDetails) {
		this.eventDetails = eventDetails;
	}

	public Set<Artist> getArtists() {
		return artists;
	}

	public void setArtists(Set<Artist> artists) {
		this.artists = artists;
	}

	public void addArtist(Artist artist) {
		artists.add(artist);
		artist.setEvent(this);
	}

	public void removeArtist(Artist artist) {
		artists.remove(artist);
		artist.setEvent(null);
	}

	public void addEventDetails(EventDetails details) {
		eventDetails.add(details);
		details.setEvent(this);
	}

	public void removeEventDetails(EventDetails details) {
		eventDetails.remove(details);
		details.setEvent(null);
	}

	public void addLocation(Location location) {
        locations.add(location);
        location.setEvent(this);
    }

    /**
     * Instantiates a new Remove notebook.
     *
     * @param location the notebook
     */
    public void removeLocation(Location location) {
        locations.remove(location);
        location.setEvent(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(eventName, event.eventName) && Objects.equals(notebook, event.notebook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, notebook);
    }

	@Override
	public String toString() {
		return "Event{" +
				"id=" + id +
				", eventName='" + eventName + '\'' +
				", location=" + locations +
				", eventDetails=" + eventDetails +
				'}';
	}
}