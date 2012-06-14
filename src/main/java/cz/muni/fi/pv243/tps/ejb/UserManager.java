package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Update;
import cz.muni.fi.pv243.tps.events.UserEvent;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;
import cz.muni.fi.pv243.tps.exceptions.InvalidUserIdentityException;
import cz.muni.fi.pv243.tps.security.UserIdentity;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Stateless
public class UserManager {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @Create
    Event<UserEvent> createEvent;

    @Inject
    @Update
    Event<UserEvent> updateEvent;

    public void createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        createEvent.fire(new UserEvent(user));
    }

    public User getUser(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null){
            throw new InvalidEntityIdException();
        }
        return user;
    }

    public User getUserByUserIdentity(UserIdentity userIdentity) {
        User user;
        try {
            user = entityManager.createQuery(
                                    "SELECT u FROM User u " +
                                             "WHERE u.userIdentity.username = :username " +
                                             "AND u.userIdentity.password = :password",
                                    User.class)
                    .setParameter("username", userIdentity.getUsername())
                    .setParameter("password", userIdentity.getPassword())
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidUserIdentityException();
        }

        return user;
    }

    public void editUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
        updateEvent.fire(new UserEvent(user));
    }

    public List<User> getUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
