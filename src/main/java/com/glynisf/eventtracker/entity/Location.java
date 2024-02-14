package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Location")
@Table(name = "location")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@JsonProperty("id")
	private int id;

	@JsonProperty("place_id")
	@Column(name = "place_id")
	private String placeId;

	@JsonProperty("name")
	@Column(name = "location_name")
	private String locationName;

	@JsonProperty("phone_number")
	@Column(name = "phone_number")
	private String phoneNumber;

	private String address;

	private String apartment;

	private String city;

	private String state;

	private String zip;

	@Column(name = "wheelchair_accessible_entrance")
	@JsonProperty("wheelchair_accessible_entrance")
	private boolean wheelchairAccessibleEntrance;
	
	private String website;

	@ManyToOne
	@JoinColumn(name = "event_fk",
	foreignKey = @ForeignKey(name = "event_id"))
	private Event event;

	public Location() {
	}

	public Location(String locationName, String phoneNumber, String address, String apartment, String city, String state, String zip, boolean wheelchairAccessibleEntrance, String website, Event event, String placeId) {
		this.locationName = locationName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.apartment = apartment;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.wheelchairAccessibleEntrance = wheelchairAccessibleEntrance;
		this.website = website;
		this.event = event;
		this.placeId = placeId;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return locationName;
	}

	public void setName(String locationName) {
		this.locationName = locationName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean isWheelchairAccessibleEntrance() {
		return wheelchairAccessibleEntrance;
	}

	public void setWheelchairAccessibleEntrance(boolean wheelchairAccessibleEntrance) {
		this.wheelchairAccessibleEntrance = wheelchairAccessibleEntrance;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPlaceId() {
		return placeId;
	}


	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return id == location.id && wheelchairAccessibleEntrance == location.wheelchairAccessibleEntrance && Objects.equals(placeId, location.placeId) && Objects.equals(locationName, location.locationName) && Objects.equals(phoneNumber, location.phoneNumber) && Objects.equals(address, location.address) && Objects.equals(apartment, location.apartment) && Objects.equals(city, location.city) && Objects.equals(state, location.state) && Objects.equals(zip, location.zip) && Objects.equals(website, location.website) && Objects.equals(event, location.event);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, placeId, locationName, phoneNumber, address, apartment, city, state, zip, wheelchairAccessibleEntrance, website, event);
	}

	@Override
	public String toString() {
		return "Location{" +
				"id=" + id +
				", placeId='" + placeId + '\'' +
				", locationName='" + locationName + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", address='" + address + '\'' +
				", apartment='" + apartment + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zip='" + zip + '\'' +
				", wheelchairAccessibleEntrance=" + wheelchairAccessibleEntrance +
				", website='" + website + '\'' +
				'}';
	}
}