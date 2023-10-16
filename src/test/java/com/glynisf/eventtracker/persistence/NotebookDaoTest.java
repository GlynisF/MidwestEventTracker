package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.Notebook;
import com.glynisf.eventtracker.entity.User;
import com.glynisf.test.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Notebook dao test.
 * @author gfisher
 */
class NotebookDaoTest {
    /**
     * The NotebookDao.
     */
    NotebookDao dao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new NotebookDao.
     */
    @BeforeEach
    void setUp() {
        dao = new NotebookDao();
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    /**
     * verifies retrieval of all Notebook's is successful.
     */
    @Test
    void getAllNotebooksSuccess() {
        List<Notebook> Notebooks = dao.getAllNotebooks();
        assertEquals(6, Notebooks.size());
    }

    /**
     * verifies insertion of new Notebook is successful.
     */
    @Test
    void insertNotebookSuccess() {
        UserDao userDao = new UserDao();
        User user = userDao.getById(1);
        Notebook newNotebook = new Notebook("October Events", user);
        user.addNotebook(newNotebook);

        int id = dao.insert(newNotebook);

        assertNotEquals(0,id);
        Notebook insertedNotebook = dao.getById(id);
        logger.info(newNotebook);
        assertEquals("October Events", insertedNotebook.getTitle());
        assertNotNull(insertedNotebook.getUser());
        assertEquals("Joe", insertedNotebook.getUser().getFirstName());
    }

    /**
     * Verifies deletion of Notebook is successful.
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(3));
        assertNull(dao.getById(3));
        logger.info(dao.getById(3));
    }

    /**
     * Verify successful retrieval of a Notebook by id
     */
    @Test
    void getByIdSuccess() {
        Notebook retrievedNotebook = dao.getById(3);
        assertNotNull(retrievedNotebook);
        assertEquals("October Events", retrievedNotebook.getTitle());


    }

    /**
     * Verifies Save or update of Notebook is successful.
     */
    @Test
    void saveOrUpdateSuccess() {
        String title = "October 2023";
        Notebook NotebookToUpdate = dao.getById(3);
        NotebookToUpdate.setTitle(title);
        dao.saveOrUpdate(NotebookToUpdate);
        Notebook retrievedNotebook = dao.getById(3);
        assertEquals(title, retrievedNotebook.getTitle());
        logger.info(retrievedNotebook);
        logger.info(NotebookToUpdate);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<Notebook> notebooks = dao.getByPropertyEqual("title", "October Events");
        assertEquals(1, notebooks.size());
        assertEquals(3, notebooks.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Notebook> notebooks = dao.getByPropertyLike("title", "O");
        assertEquals(3, notebooks.size());
    }





}