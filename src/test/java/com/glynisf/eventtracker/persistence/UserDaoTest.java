package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.Notebook;
import com.glynisf.eventtracker.entity.User;
import com.glynisf.test.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type User dao test.
 * @author gfisher
 */
class UserDaoTest {
    /**
     * The UserDao.
     */
    UserDao dao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new UserDao.
     */
    @BeforeEach
    void setUp() {
        dao = new UserDao();
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    /**
     * verifies retrieval of all user's is successful.
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = dao.getAllUsers();
        assertEquals(6, users.size());
    }

    /**
     * verifies insertion of new user is successful.
     */
    @Test
    void insertSuccess() {
        User newUser = new User("Glynis", "Fisher", "gfisher", "gcadagfisher@email.com", 0, "wahoo11", LocalDate.parse("1992-08-11"), "F");
        int id = dao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = dao.getById(id);
        logger.info(newUser);
        assertEquals(id, insertedUser.getId());
    }
    /**
     * verifies insertion of new user is successful.
     */
    @Test
    void insertWithNotebookSuccess() {
        User newUser = new User("Glynis", "Fisher", "gfisher", "gcadagfisher@email.com", 0, "wahoo11", LocalDate.parse("1992-08-11"), "F");

        String title = "October Events";
        Notebook notebook = new Notebook(title, newUser);

        newUser.addNotebook(notebook);
        int id = dao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = dao.getById(id);
        logger.info(newUser);
        assertEquals(id, insertedUser.getId());
        assertEquals(1, insertedUser.getNotebooks().size());
    }

    /**
     * Verifies deletion of user is successful.
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(3));
        assertNull(dao.getById(3));
        logger.info(dao.getById(3));
    }

    /**
     * Verify successful retrieval of a user by id
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = dao.getById(3);
        assertEquals("Barney", retrievedUser.getFirstName());
        assertEquals("Curry", retrievedUser.getLastName());
        assertEquals("bcurry", retrievedUser.getUserName());

    }

    /**
     * Verifies Save or update of user is successful.
     */
    @Test
    void saveOrUpdateSuccess() {
        String newUser = "chupi";
        User userToUpdate = dao.getById(3);
        userToUpdate.setFirstName(newUser);
        dao.saveOrUpdate(userToUpdate);
        User retrievedUser = dao.getById(3);
        assertEquals(userToUpdate, retrievedUser);
        logger.info(retrievedUser);
        logger.info(userToUpdate);
    }



    /**
     * Verifies gets users by last name successfully.
     */
    @Test
    void getUsersByLastNameSuccess() {
        List<User> users = dao.getUsersByLastName("c");
        assertEquals(3, users.size());
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = dao.getByPropertyEqual("lastName", "Curry");
        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = dao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }

}