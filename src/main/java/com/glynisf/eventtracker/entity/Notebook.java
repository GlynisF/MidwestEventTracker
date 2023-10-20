package com.glynisf.eventtracker.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * The type Notebook.
 */
@Entity(name = "Notebook")
@Table(name = "notebook")
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    @Column(name = "notebook_id")
    private int id;
    private String title;
    @ManyToOne
    private User user;

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
    public Notebook(String title, User user) {
        this.title = title;
        this.user = user;
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
                ", user=" + user +
                '}';
    }
}