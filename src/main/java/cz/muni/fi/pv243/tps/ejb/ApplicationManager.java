package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Acceptation;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.StatusChange;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationAttemptException;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Stateless
public class ApplicationManager {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @Create
    Event<ApplicationEvent> createEvent;

    @Inject
    @Acceptation
    Event<ApplicationEvent> acceptationEvent;

    @Inject
    @StatusChange
    Event<ApplicationEvent> statusEvent;

    public Application getApplication(Long id){
        Application application = entityManager.find(Application.class, id);
        if (application == null){
            throw new InvalidEntityIdException();
        }
        return application;
    }

    public List<Application> getApplicationsByTopic(ThesisTopic topic) {
        return entityManager.createQuery("SELECT a FROM Application a WHERE a.topic = :topic", Application.class)
                .setParameter("topic", topic)
                .getResultList();
    }

    public void apply(User user, ThesisTopic topic) {
        try {
            Application application = new Application();
            application.setApplicant(user);
            application.setTopic(topic);
            entityManager.persist(application);
            entityManager.flush();
            createEvent.fire(new ApplicationEvent(application));
        } catch (Exception e) {
            throw new InvalidApplicationAttemptException();
        }
    }

    public void apply(Long userId, Long topicId) {
        User user = new User();
        user.setId(userId);
        ThesisTopic topic = new ThesisTopic();
        topic.setId(topicId);
        apply(user, topic);
    }

    public void acceptApplication(Application application) {
        application.setStatus(Application.Status.ACCEPTED);
        entityManager.merge(application);
        entityManager.flush();

        acceptationEvent.fire(new ApplicationEvent(application));
        statusEvent.fire(new ApplicationEvent(application));
    }

    public void declineApplication(Application application) {
        application.setStatus(Application.Status.DECLINED);
        entityManager.merge(application);
        entityManager.flush();

        statusEvent.fire(new ApplicationEvent(application));
    }
}
