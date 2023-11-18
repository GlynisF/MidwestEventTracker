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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type User dao test.
 *
 * @author gfisher
 */
class UserDaoTest {
    /**
     * The UserDao.
     */
    GenericDao userDao;
    /**
     * The Notebook dao.
     */
    GenericDao notebookDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new UserDao.
     */
    @BeforeEach
    void setUp() {
        userDao = new GenericDao(User.class);
        notebookDao = new GenericDao(Notebook.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    /**
     * verifies retrieval of all user's is successful.
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = userDao.getAll();
        assertNotNull(users);
        assertEquals(6, users.size());
    }

    /**
     * verifies insertion of new user is successful.
     */
    @Test
    void insertSuccess() {
        User newUser = new User("Glynis", "Fisher", "gfisher", "gcadagfisher@email.com", 0, "wahoo11",
                LocalDate.parse("1992-08-11"), "F");

        int id = userDao.insert(newUser);
        assertNotEquals(0, id);

        User insertedUser = (User) userDao.getById(id);
        assertTrue(newUser.equals(insertedUser));
    }

    /**
     * verifies insertion of new user is successful.
     */
    @Test
    void insertWithNotebookSuccess() {
        User userWithNotebook =  (User) userDao.getById(4);

        String title = "October Events";
        Notebook notebook = new Notebook(title, userWithNotebook);
        assertNotNull(notebook);

        userWithNotebook.addNotebook(notebook);
        notebook.setUser(userWithNotebook);

        assertTrue(notebook.getUser().equals(userWithNotebook));
        assertTrue(notebook.getUser().equals(userDao.getById(4)));
        assertNotNull(notebook.getUser());
    }

    /**
     * Verifies deletion of user is successful.
     */
    @Test
    void deleteSuccess() {
        userDao.delete(userDao.getById(3));
        assertNull(userDao.getById(3));

        User user = (User) userDao.getById(3);
        assertNull(user);
    }

    /**
     * Delete notebook success.
     */
    @Test
    void deleteNotebookSuccess() {
        Notebook notebook = (Notebook) notebookDao.getById(4);
        Set<Notebook> retrievedNotebook = notebook.getUser().getNotebooks();

        User user = (User) userDao.getById(3);

        user.removeNotebook(notebook);
        notebook.setUser(null);

        assertFalse(retrievedNotebook.contains(user));
    }

    /**
     * Verify successful retrieval of a user by id
     */
    @Test
    void getByIdSuccess() {
        User retrievedUser = (User) userDao.getById(3);
        assertNotNull(retrievedUser);
        assertTrue(userDao.getById(3).equals(retrievedUser));
    }

    /**
     * Verifies Save or update of user is successful.
     */
    @Test
    void saveOrUpdateSuccess() {
        String firstName = "chupi";
        User userToUpdate = (User) userDao.getById(3);
        assertNotNull(userToUpdate);

        userToUpdate.setFirstName(firstName);
        userDao.saveOrUpdate(userToUpdate);
        assertTrue(userToUpdate.getFirstName().equals("chupi"));

        List<User> listOfUserDetails =  userDao.getByPropertyEqual("id", "3");
        assertNotNull(listOfUserDetails);

        assertTrue(listOfUserDetails.contains(userToUpdate));
        assertTrue(listOfUserDetails.get(0).equals(userToUpdate));
    }

    /**
     * Verifies gets users by last name successfully.
     */
    @Test
    void getUsersByLastNameSuccess() {
        List<User> users = userDao.getByPropertyLike("lastName", "c");
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<User> users = userDao.getByPropertyEqual("lastName", "Curry");
        assertNotNull(users);
        assertEquals(1, users.size());
        assertTrue(users.contains(userDao.getById(3)));
        assertTrue(users.get(0).equals(userDao.getById(3)));
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = userDao.getByPropertyLike("lastName", "c");
        assertEquals(3, users.size());
    }

}