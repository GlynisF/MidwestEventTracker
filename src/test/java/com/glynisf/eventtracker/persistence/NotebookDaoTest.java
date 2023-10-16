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
    GenericDao genericDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new NotebookDao.
     */
    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(Notebook.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    /**
     * verifies retrieval of all Notebook's is successful.
     */
    @Test
    void getAllNotebooksSuccess() {
        List<Notebook> notebooks = genericDao.getAll();
        assertEquals(6, notebooks.size());
    }

    /**
     * verifies insertion of new Notebook is successful.
     */
    @Test
    void insertNotebookSuccess() {
        GenericDao userDao = new GenericDao(User.class);
        User user = (User) userDao.getById(1);
        Notebook newNotebook = new Notebook("October Events", user);
        user.addNotebook(newNotebook);

        int id = genericDao.insert(newNotebook);

        assertNotEquals(0,id);
        Notebook insertedNotebook = (Notebook) genericDao.getById(id);
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
        genericDao.delete(genericDao.getById(3));
        assertNull(genericDao.getById(3));
    }

    /**
     * Verify successful retrieval of a Notebook by id
     */
    @Test
    void getByIdSuccess() {
        Notebook retrievedNotebook = (Notebook) genericDao.getById(3);
        assertNotNull(retrievedNotebook);
        assertEquals("October Events", retrievedNotebook.getTitle());
    }

    /**
     * Verifies Save or update of Notebook is successful.
     */
    @Test
    void saveOrUpdateSuccess() {
        String title = "October 2023";
        Notebook notebookToUpdate = (Notebook) genericDao.getById(3);
        notebookToUpdate.setTitle(title);
        genericDao.saveOrUpdate(notebookToUpdate);
        List<Notebook> retrievedNotebook = genericDao.getByPropertyEqual("title", title);
        List<Notebook> notebook2 = genericDao.getByPropertyEqual("id", "3");
        assertEquals(retrievedNotebook, notebook2);
        assertEquals(title, notebookToUpdate.getTitle());
        assertEquals(3, retrievedNotebook.get(0).getId());
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<Notebook> notebooks = genericDao.getByPropertyEqual("title", "October Events");
        assertEquals(1, notebooks.size());
        assertEquals(3, notebooks.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Notebook> getNotebookByPropertyLike = genericDao.getByPropertyLike("title", "D");
        List<Notebook> getNotebookByUserId = genericDao.getByPropertyEqual("title", "December Events");
        assertEquals(getNotebookByPropertyLike, getNotebookByUserId);
        assertEquals(1, getNotebookByPropertyLike.size());
    }





}