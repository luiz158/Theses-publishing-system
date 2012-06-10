package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.exceptions.InvalidCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Stateless
public class UserManager implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    public User getUserByCredentials(User.Credentials credentials) {
        User user;
        try {
            user = entityManager
                    .createQuery("SELECT u FROM User u WHERE u.credentials = :credentials", User.class)
                    .setParameter("credentials", credentials)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    public void editUser(User user) {
        entityManager.merge(user);
    }

    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
