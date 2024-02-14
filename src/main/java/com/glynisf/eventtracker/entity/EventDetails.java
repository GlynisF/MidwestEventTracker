package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Entity(name = "EventDetails")
@Table(name = "event_details")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name="native", strategy="native")
	@Column(name = "details_id")
	@JsonProperty("id")
	private int id;

	@JsonProperty("startTime")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@Column(name = "start_time")
	private LocalTime startTime;

	@JsonProperty("endTime")
	@JsonSerialize(using = LocalTimeSerializer.class)
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	@Column(name = "end_time")
	private LocalTime endTime;

	@JsonProperty("eventDate")
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@Column(name = "date_of_event")
	private LocalDate eventDate;

	@ManyToOne
	@JoinColumn(name = "event_id",
			foreignKey = @ForeignKey(name = "event_details_fk"))
	private Event event;

	public EventDetails() {

	}

	public EventDetails(LocalDate eventDate, LocalTime startTime, LocalTime endTime, Event event) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.eventDate = eventDate;
		this.event = event;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			String rawDate = p.getValueAsString();
			try {
				LocalDate parsedDate = LocalDate.parse(rawDate, DateTimeFormatter.ISO_LOCAL_DATE);
				System.out.println("Successfully deserialized LocalDate: " + parsedDate);
				return parsedDate;
			} catch (DateTimeParseException e) {
				System.err.println("Error parsing LocalDate. Raw value: " + rawDate);
				throw new IOException("Error parsing LocalDate", e);
			}
		}
	}

	// Custom serializer for LocalDate
	public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
		@Override
		public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
	}


	public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
		@Override
		public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			String rawTime = p.getValueAsString();
			try {
				LocalTime parsedTime = LocalTime.parse(rawTime, DateTimeFormatter.ISO_LOCAL_TIME);

				// Customize the output format
				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
				String formattedTime = parsedTime.format(outputFormatter);

				System.out.println("Successfully deserialized LocalTime: " + formattedTime);
				return parsedTime;
			} catch (DateTimeParseException e) {
				System.err.println("Error parsing LocalTime. Raw value: " + rawTime);
				throw new IOException("Error parsing LocalTime", e);
			}
		}
	}



	// Custom serializer for LocalTime
	public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
		@Override
		public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
		}
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EventDetails that = (EventDetails) o;
		return id == that.id && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(eventDate, that.eventDate) && Objects.equals(event, that.event);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, startTime, endTime, eventDate, event);
	}

	@Override
	public String toString() {
		return "EventDetails{" +
				"id=" + id +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", eventDate=" + eventDate +
				'}';
	}
}