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
    GenericDao notebookDao;
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
        Notebook newNotebook = new Notebook("October Events", user);
        user.addNotebook(newNotebook);

        int id = notebookDao.insert(newNotebook);

        assertNotEquals(0,id);
        Notebook insertedNotebook = (Notebook) notebookDao.getById(id);
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
        notebookDao.delete(notebookDao.getById(3));
        assertNull(notebookDao.getById(3));
    }

    /**
     * Verify successful retrieval of a Notebook by id
     */
    @Test
    void getByIdSuccess() {
        Notebook retrievedNotebook = (Notebook) notebookDao.getById(3);
        assertNotNull(retrievedNotebook);
        assertEquals("October Events", retrievedNotebook.getTitle());
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
        List<Notebook> retrievedNotebook = notebookDao.getByPropertyEqual("title", title);
        List<Notebook> notebook2 = notebookDao.getByPropertyEqual("id", "3");
        assertEquals(retrievedNotebook, notebook2);
        assertEquals(title, notebookToUpdate.getTitle());
        assertEquals(3, retrievedNotebook.get(0).getId());
    }



    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyEqualSuccess() {
        List<Notebook> notebooks = notebookDao.getByPropertyEqual("title", "October Events");
        assertEquals(1, notebooks.size());
        assertEquals(3, notebooks.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Notebook> getNotebookByPropertyLike = notebookDao.getByPropertyLike("title", "D");
        List<Notebook> getNotebookByUserId = notebookDao.getByPropertyEqual("title", "December Events");
        assertEquals(getNotebookByPropertyLike, getNotebookByUserId);
        assertEquals(1, getNotebookByPropertyLike.size());
    }





}