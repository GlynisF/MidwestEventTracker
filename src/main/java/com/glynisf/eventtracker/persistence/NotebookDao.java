package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.Notebook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;
    /**
     * The type Notebook dao.
     */

    public class NotebookDao {

        private final Logger logger = LogManager.getLogger(this.getClass());
        /**
         * The Session factory.
         */
        SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

        /**
         * Gets all Notebooks.
         *
         * @return the all Notebooks
         */
        public List<Notebook> getAllNotebooks() {

            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Notebook> query = builder.createQuery(Notebook.class);
            Root<Notebook> root = query.from(Notebook.class);
            List<Notebook> Notebooks = session.createQuery(query).getResultList();
            session.close();
            return Notebooks;
        }
        
        

        /**
         * Gets a Notebook by id
         *
         * @param id Notebooks id to search by
         * @return a Notebook
         */
        public Notebook getById(int id) {
            Session session = sessionFactory.openSession();
            Notebook Notebook = session.get(Notebook.class, id);
            session.close();
            return Notebook;
        }
        


        /**
         * update Notebook
         *
         * @param Notebook Notebook to be inserted or updated
         */
        public void saveOrUpdate(Notebook Notebook) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(Notebook);
            transaction.commit();
            session.close();
        }

        public List<Notebook> getNotebookByTitle(String title) {
            logger.debug("Searching for: {}", title);

            Session session = sessionFactory.openSession();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Notebook> query = builder.createQuery(Notebook.class);
            Root<Notebook> root = query.from(Notebook.class);
            Expression<String> propertyPath = root.get("title");
            query.where(builder.like(propertyPath, "=" + title));
            List<Notebook> Notebooks = session.createQuery(query).getResultList();
            session.close();
            return Notebooks;
        }

        /**
         * update Notebook
         *
         * @param Notebook Notebook to be inserted or updated
         * @return id of the inserted Notebook
         */
        public int insert(Notebook Notebook) {
            int id = 0;
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            id = (int) session.save(Notebook);
            transaction.commit();
            session.close();
            return id;
        }


        /**
         * Delete a Notebook
         *
         * @param Notebook Notebook to be deleted
         */
        public void delete(Notebook Notebook) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(Notebook);
            transaction.commit();
            session.close();
        }

        /**
         * Get Notebook by property (exact match)
         * sample usage: getByPropertyEqual("lastName", "Curry")
         *
         * @param propertyName entity property to search by
         * @param value value of the property to search for
         * @return list of Notebooks meeting the criteria search
         */
        public List<Notebook> getByPropertyEqual(String propertyName, String value) {
            Session session = sessionFactory.openSession();

            logger.debug("Searching for Notebook with " + propertyName + " = " + value);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Notebook> query = builder.createQuery( Notebook.class );
            Root<Notebook> root = query.from( Notebook.class );
            query.select(root).where(builder.equal(root.get(propertyName), value));
            List<Notebook> Notebooks = session.createQuery( query ).getResultList();

            session.close();
            return Notebooks;
        }

        /**
         * Get Notebook by property (like)
         * sample usage: getByPropertyLike("lastName", "C")
         *
         * @param propertyName entity property to search by
         * @param value value of the property to search for
         * @return list of Notebooks meeting the criteria search
         */
        public List<Notebook> getByPropertyLike(String propertyName, String value) {
            Session session = sessionFactory.openSession();

            logger.debug("Searching for Notebook with {} = {}",  propertyName, value);

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Notebook> query = builder.createQuery( Notebook.class );
            Root<Notebook> root = query.from( Notebook.class );
            Expression<String> propertyPath = root.get(propertyName);

            query.where(builder.like(propertyPath, "%" + value + "%"));

            List<Notebook> Notebooks = session.createQuery( query ).getResultList();
            session.close();
            return Notebooks;
        }

    }