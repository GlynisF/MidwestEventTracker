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
 *
 * @author gfisher
 */
class NotebookDaoTest {
    /**
     * The NotebookDao.
     */
    GenericDao notebookDao;
    /**
     * The User dao.
     */
    GenericDao userDao;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up the connection to database and instantiates a new NotebookDao.
     */
    @BeforeEach
    void setUp() {
        notebookDao = new GenericDao(Notebook.class);
        userDao = new GenericDao(User.class);
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        logger.info(database);
    }

    /**
     * verifies retrieval of all Notebook's is successful.
     */
    @Test
    void getAllNotebooksSuccess() {
        List<Notebook> notebooks = notebookDao.getAll();
        assertEquals(6, notebooks.size());
    }

    /**
     * verifies insertion of new Notebook is successful.
     */
    @Test
    void insertNotebookSuccess() {
        User user = (User) userDao.getById(1);
        Notebook newNotebook = new Notebook(0, "October Events", user);

        int id = notebookDao.insert(newNotebook);
        user.addNotebook(newNotebook);

        assertTrue(user.getNotebooks().contains(newNotebook));

        assertTrue(true, String.valueOf(notebookDao.getById(3).equals(newNotebook)));

        assertTrue(notebookDao.getByPropertyLike("title", "October Events").contains(newNotebook));

        assertNotEquals(0,id);
    }

    /**
     * Verifies deletion of Notebook is successful.
     */
    @Test
    void deleteSuccess() {
        notebookDao.delete(notebookDao.getById(3));
        assertNull(notebookDao.getById(3));
    }

    /**
     * Verify successful retrieval of a Notebook by id
     */
    @Test
    void getByIdSuccess() {
        Notebook notebookDaoById = (Notebook) notebookDao.getById(3);
        assertNotNull(notebookDaoById);
        assertTrue(notebookDaoById.getTitle().equals("October Events"));
        assertTrue(notebookDao.getById(3).equals(notebookDaoById));
    }

    /**
     * Verifies Save or update of Notebook is successful.
     */
    @Test
    void saveOrUpdateSuccess() {
        String title = "October 2023";
        Notebook notebookToUpdate = (Notebook) notebookDao.getById(3);

        notebookToUpdate.setTitle(title);
        notebookDao.saveOrUpdate(notebookToUpdate);

        assertTrue(notebookToUpdate.getTitle().equals("October 2023"));

        assertTrue(notebookDao.getById(3).equals(notebookToUpdate));
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<Notebook> notebookByTitle = (List<Notebook>) notebookDao.getByPropertyEqual("title", "September");
        assertNotNull(notebookByTitle);
        assertTrue(true, String.valueOf(notebookByTitle.contains("September Events")));
        assertTrue(notebookDao.getByPropertyEqual("title", "September").equals(notebookByTitle));
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Notebook> getNotebookByPropertyLike = notebookDao.getByPropertyLike("title", "Dec");
        assertEquals(1, getNotebookByPropertyLike.size());
        assertTrue(true, String.valueOf(getNotebookByPropertyLike.contains("December")));
        assertTrue(getNotebookByPropertyLike.equals(notebookDao.getByPropertyEqual("id", "5")));
    }

}