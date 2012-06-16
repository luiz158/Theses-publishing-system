package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Acceptation;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.StatusChange;
import cz.muni.fi.pv243.tps.exceptions.ApplicationInProgressException;
import cz.muni.fi.pv243.tps.exceptions.ApplicationProcessedException;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationAttemptException;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import org.joda.time.DateTime;

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
    TopicManager topicManager;

    @Inject
    ThesisManager thesisManager;

    @Inject
    UserManager userManager;

    @Inject
    @Create
    Event<ApplicationEvent> createEvent;

    @Inject
    @Acceptation
    Event<ApplicationEvent> acceptationEvent;

    @Inject
    @StatusChange
    Event<ApplicationEvent> statusEvent;


    public List<Application> getApplications(ThesisTopic topic, Application.Status status, User user) {
        return entityManager.createQuery("SELECT a FROM  Application a " +
                "WHERE a.applicant = :user " +
                "AND a.topic = :topic " +
                "AND a.status = :status " +
                "ORDER BY a.applicationDate ASC", Application.class)
                .setParameter("user", user)
                .setParameter("topic", topic)
                .setParameter("status", status)
                .getResultList();
    }

    public Application getApplication(Long id) {
        Application application = entityManager.find(Application.class, id);
        if (application == null) {
            throw new InvalidEntityIdException();
        }
        return application;
    }

    public Application getWaitingApplication(ThesisTopic topic, UserIdentity identity){
        User user = userManager.getUserByUserIdentity(identity);
        List<Application> applications = this.getApplications(topic, Application.Status.WAITING, user);
        if(applications.isEmpty()){
            return null;
        }
        return applications.get(0);
    }

    public List<Application> getApplicationsByTopic(ThesisTopic topic) {
        return entityManager.createQuery("SELECT a FROM Application a " +
                "WHERE a.topic = :topic", Application.class)
                .setParameter("topic", topic)
                .getResultList();
    }

    public List<Application> getApplicationsByTopic(ThesisTopic topic, Application.Status status) {
        return entityManager.createQuery("SELECT a FROM Application a " +
                "WHERE a.topic = :topic " +
                "AND a.status = :status " +
                "ORDER BY a.applicationDate ASC", Application.class)
                .setParameter("topic", topic)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Application> getApplicationsByUser(User user) {
        return entityManager.createQuery("SELECT a FROM Application a WHERE a.applicant = :applicant", Application.class)
                .setParameter("applicant", user)
                .getResultList();
    }

    public void apply(User user, ThesisTopic topic) {
        if (!this.canApply(topic)) {
            throw new InvalidApplicationAttemptException();
        }
        if (!this.hasNoWaitingApplications(user, topic) && !this.hasNotApplied(user, topic)) {
            throw new ApplicationInProgressException();
        }
        Application application = new Application();
        application.setApplicant(user);
        application.setTopic(topic);
        application.setStatus(Application.Status.WAITING);
        application.setApplicationDate(new DateTime());
        entityManager.persist(application);
        entityManager.flush();
        createEvent.fire(new ApplicationEvent(application));
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

    public void cancelApplication(Application application) {
        Application entity = this.getApplication(application.getId());
        if (!this.isWaitingApplication(application)) {
            throw new ApplicationProcessedException();
        }
        entityManager.remove(entity);
        entityManager.flush();
    }

    public boolean isWaitingApplication(Application application) {
        return this.getApplication(application.getId())
                .getStatus()
                .equals(Application.Status.WAITING);
    }

    public boolean hasNoWaitingApplications(User user, ThesisTopic topic) {
        return this.getApplications(topic, Application.Status.WAITING, user).isEmpty();
    }

    public boolean hasNotApplied(User user, ThesisTopic topic) {
        return this.getApplications(topic, Application.Status.ACCEPTED, user).isEmpty();
    }

    public boolean canApply(ThesisTopic topic) {
        long capacity = topicManager.getTopic(topic.getId()).getCapacity();
        long theses = thesisManager.countTheses(topic);
        return (capacity - theses) > 0;
    }
}
