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
    GenericDao genericDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new UserDao.
     */
    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(User.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    /**
     * verifies retrieval of all user's is successful.
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = genericDao.getAll();
        assertEquals(6, users.size());
    }

    /**
     * verifies insertion of new user is successful.
     */
    @Test
    void insertSuccess() {
        User newUser = new User("Glynis", "Fisher", "gfisher", "gcadagfisher@email.com", 0, "wahoo11", LocalDate.parse("1992-08-11"), "F");
        int id = genericDao.insert(newUser);
        assertNotEquals(0, id);
        User insertedUser = (User) genericDao.getById(id);
        assertEquals(newUser, insertedUser);
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
        int id = genericDao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = (User) genericDao.getById(id);
        assertEquals(id, insertedUser.getId());
        assertEquals(1, insertedUser.getNotebooks().size());
        assertEquals(newUser, insertedUser);
    }

    /**
     * Verifies deletion of user is successful.
     */
    @Test
    void deleteSuccess() {
        genericDao.delete(genericDao.getById(3));
        assertNull(genericDao.getById(3));
    }

    /**
     * Verify successful retrieval of a user by id
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = (User) genericDao.getById(3);
        assertNotNull(retrievedUser);
        assertEquals("Barney", retrievedUser.getFirstName());
        assertEquals("Curry", retrievedUser.getLastName());
        assertEquals("bcurry", retrievedUser.getUserName());

    }

    /**
     * Verifies Save or update of user is successful.
     */
    @Test
    void saveOrUpdateSuccess() {
        String firstName = "chupi";
        User userToUpdate = (User) genericDao.getById(3);
        userToUpdate.setFirstName(firstName);
        genericDao.saveOrUpdate(userToUpdate);
        List<User> expectedUser =  genericDao.getByPropertyEqual("id", "3");
        List<User> retrievedUser = genericDao.getByPropertyEqual("firstName", firstName);
        assertEquals(expectedUser, retrievedUser);
        logger.info(retrievedUser);
        logger.info(userToUpdate);
    }



    /**
     * Verifies gets users by last name successfully.
     */
    @Test
    void getUsersByLastNameSuccess() {
        List<User> users = genericDao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = genericDao.getByPropertyEqual("lastName", "Curry");
        assertEquals(1, users.size());
        assertEquals(3, users.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = genericDao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }

}