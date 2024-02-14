package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Artist")
@Table(name = "artist")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy="native")
	@JsonProperty("id")
	private int id;

	@JsonProperty("moniker")
	private String moniker;

	@JsonProperty("artist_first_name")
	@Column(name = "first_name")
	private String firstName;

	@JsonProperty("artist_last_name")
	@Column(name = "last_name")
	private String lastName;

	@JsonProperty("artist_email")
	private String email;

	@JsonProperty("booking_fee")
	@Column(name = "booking_fee", precision = 10, scale = 2)
	private Double bookingFee;

	@ManyToOne
	@JoinColumn(name = "event_id",
			foreignKey = @ForeignKey(name = "event_id") )
	private Event event;

	public Artist() {

	}

	public Artist(String moniker, String firstName, String lastName, String email, Double bookingFee, Event event) {
		this.moniker = moniker;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.bookingFee = bookingFee;
		this.event = event;
	}

	public void updateArtist(String moniker, String firstName, String lastName, String email, Double bookingFee, Event event) {
		setMoniker(moniker);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setBookingFee(bookingFee);
		setEvent(event);
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMoniker() {
		return moniker;
	}

	public void setMoniker(String moniker) {
		this.moniker = moniker;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(Double bookingFee) {
		this.bookingFee = bookingFee;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getBookingFeeFormatted() {
		if (bookingFee != null) {
			return String.format("%.2f", bookingFee);
		} else {
			return null;
		}
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Artist artist = (Artist) o;
		return id == artist.id && Objects.equals(moniker, artist.moniker) && Objects.equals(firstName, artist.firstName) && Objects.equals(lastName, artist.lastName) && Objects.equals(email, artist.email) && Objects.equals(bookingFee, artist.bookingFee) && Objects.equals(event, artist.event);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, moniker, firstName, lastName, email, bookingFee, event);
	}

	@Override
	public String toString() {
		return "Artist{" +
				"id=" + id +
				", moniker='" + moniker + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", bookingFee=" +  getBookingFeeFormatted() +
				'}';
	}
}