package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    final static SessionFactory sf = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session sessionObj = sf.openSession()) {
            Transaction transactionObj = sessionObj.beginTransaction();
            NativeQuery query = sessionObj
                    .createSQLQuery("CREATE TABLE IF NOT EXISTS user " +
                            "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                            " name VARCHAR(32), " +
                            " lastname VARCHAR(32), " +
                            " age BIT(8), " +
                            " PRIMARY KEY ( id ))");
            try {
                query.executeUpdate();
                transactionObj.commit();
            } catch (Exception e) {
                if (transactionObj != null) {
                    transactionObj.rollback();
                }
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session sessionObj = sf.openSession()) {
            Transaction transactionObj = sessionObj.beginTransaction();
            NativeQuery query = sessionObj
                    .createSQLQuery("DROP TABLE IF EXISTS user");
            try {
                query.executeUpdate();
                transactionObj.commit();
            } catch (Exception e) {
                if (transactionObj != null) {
                    transactionObj.rollback();
                }
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session sessionObj = sf.openSession()) {
            Transaction transactionObj = sessionObj.beginTransaction();
            User newUser = new User(name, lastName, age);
            try {
                Long id = (Long) sessionObj.save(newUser);
                if (id != null) {
                    transactionObj.commit();
                    System.out.println("User с именем – " + name + " добавлен в базу данных");
                } else {
                    transactionObj.rollback();
                }

            } catch (Exception e) {
                if (transactionObj != null) {
                    transactionObj.rollback();
                }
            }
        } catch (HibernateException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session sessionObj = sf.openSession()) {
            Transaction transactionObj = sessionObj.beginTransaction();
            User userRemove = sessionObj.get(User.class, id);
            try {
                sessionObj.remove(userRemove);
                transactionObj.commit();
            } catch (Exception e) {
                if (transactionObj != null) {
                    transactionObj.rollback();
                }
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session sessionObj = sf.openSession()) {
            Transaction transactionObj = sessionObj.beginTransaction();
            try {
                userList = sessionObj
                        .createQuery("SELECT u FROM User u", User.class)
                        .getResultList();
                transactionObj.commit();
                return userList;
            } catch (Exception e) {
                if (transactionObj != null) {
                    transactionObj.rollback();
                }
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session sessionObj = sf.openSession()) {
            Transaction transactionObj = sessionObj.beginTransaction();
            NativeQuery query = sessionObj.createSQLQuery("DELETE FROM user");
            try {
                query.executeUpdate();
                transactionObj.commit();
            } catch (Exception e) {
                if (transactionObj != null) {
                    transactionObj.rollback();
                }
            }
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}
