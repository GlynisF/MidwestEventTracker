package com.glynisf.eventtracker.persistence;

import com.glynisf.eventtracker.entity.User;
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
 * The type User dao.
 */
public class UserDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The Session factory.
     */
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<User> users = session.createQuery(query).getResultList();
        session.close();
        return users;
    }

    /**
     * Gets all users by last name.
     *
     * @param firstName the first name
     * @return the all users
     */
    public List<User> getUsersByFirstName(String firstName) {

        logger.debug("Searching for: {}", firstName);

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression<String> propertyPath = root.get("firstName");
        query.where(builder.like(propertyPath, "%" + firstName + "%"));
        List<User> users = session.createQuery(query).getResultList();
        session.close();
        return users;
    }

    public List<User> getUserByLastName(String lastName) {
        logger.debug("Searching for: {}", lastName);

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression<String> propertyPath = root.get("lastName");
        query.where(builder.like(propertyPath, "=" + lastName));
        List<User> users = session.createQuery(query).getResultList();
        session.close();
        return users;

    }

    /**
     * Gets a user by id
     *
     * @param id users id to search by
     * @return a user
     */
    public User getById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    public List<User> getByEmail(String email) {
        logger.debug("Searching for: {}", email);

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression<String> propertyPath = root.get("firstName");
        query.where(builder.like(propertyPath, "=" + email));
        List<User> users = session.createQuery(query).getResultList();
        session.close();
        return users;
    }


    /**
     * update user
     *
     * @param user User to be inserted or updated
     */
    public void saveOrUpdate(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }

    public List<User> getByUsername(String userName) {
        logger.debug("Searching for: {}", userName);

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Expression<String> propertyPath = root.get("userName");
        query.where(builder.like(propertyPath, "=" + userName));
        List<User> users = session.createQuery(query).getResultList();
        session.close();
        return users;
    }

    /**
     * update user
     *
     * @param user User to be inserted or updated
     * @return id of the inserted user
     */
    public int insert(User user) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        id = (int) session.save(user);
        transaction.commit();
        session.close();
        return id;
    }


    /**
     * Delete a user
     *
     * @param user User to be deleted
     */
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }
}