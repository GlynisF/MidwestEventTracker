package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Holds details to create a new User.
 *
 * @author gfisher
 */
@Entity(name = "EventTracker")
@Table(name = "user")
public class User implements Serializable {

	private String password;
    //private int age;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

	@JsonSerialize(using = EventDetails.LocalDateSerializer.class)
	@JsonDeserialize(using = EventDetails.LocalDateDeserializer.class)
	@JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email_address")
    private String email;

    private String gender;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    @Column(name = "user_id")
    private int id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Notebook> notebooks = new HashSet<>();

    /**
     * Instantiates a new User.
     */
    public User() {

    }

    /**
     * Instantiates a new User.
     *
     * @param firstName   the first name
     * @param lastName    the last name
     * @param userName    the username
     * @param email       the email
     * @param id          the id
     * @param password    the password
     * @param dateOfBirth the date of birth
     * @param gender      the gender
     */
    public User(String firstName, String lastName, String userName, String email, int id, String password, LocalDate dateOfBirth, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.id = id;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    /**
     * Gets notebook.
     *
     * @return the notebook
     */
    public Set<Notebook> getNotebooks() {
        return notebooks;
    }

    /**
     * Sets notebook.
     *
     * @param notebooks the notebook
     */
    public void setNotebooks(Set<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Instantiates a new Add notebook.
     *
     * @param notebook the notebook
     */
    public void addNotebook(Notebook notebook) {
        notebooks.add(notebook);
        notebook.setUser(this);
    }

    /**
     * Instantiates a new Remove notebook.
     *
     * @param notebook the notebook
     */
    public void removeNotebook(Notebook notebook) {
        notebooks.remove(notebook);
        notebook.setUser(null);
    }

	public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
		@Override
		public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
	}

	// Custom deserializer for LocalDate
	public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			return LocalDate.parse(p.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
		}
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(userName, user.userName) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(email, user.email) && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, firstName, lastName, userName, dateOfBirth, email, gender, id);
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", id=" + id +
		        ", notebooks=" + notebooks +
                '}';
    }

}