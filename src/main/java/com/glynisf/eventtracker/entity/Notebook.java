package com.glynisf.eventtracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Notebook.
 */
@Entity(name = "Notebook")
@Table(name = "notebook")
public class Notebook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    @Column(name="notebook_id")
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_user_id",
        foreignKey = @ForeignKey(name = "user_id"))
    private User user;

    @OneToMany(mappedBy = "notebook", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Event> events = new HashSet<Event>();

    /**
     * Instantiates a new Notebook.
     */
    public Notebook(){

    }

    /**
     * Instantiates a new Notebook.
     *
     * @param id    the id
     * @param title the title
     * @param user  the user
     */
    public Notebook(int id, String title, User user) {
        this.id = id;
        this.title = title;
        this.user = user;
    }


    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
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
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void addEvent(Event event) {
        events.add(event);
        event.setNotebook(this);
    }

    /**
     * Instantiates a new Remove notebook.
     *
     * @param notebook the notebook
     */
    public void removeEvent(Event event) {
        events.remove(event);
        event.setNotebook(null);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notebook notebook = (Notebook) o;
        return id == notebook.id && Objects.equals(title, notebook.title) && Objects.equals(user, notebook.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, user);
    }

    @Override
    public String toString() {
        return "Notebook{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}