package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.ThesisEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Update;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */

@Stateless
public class ThesisManager implements Serializable{
    @PersistenceContext
    EntityManager em;

    @Inject
    @Create
    private Event<ThesisEvent> createEvent;

    public Thesis getThesis(Long id){
        Thesis thesis = em.find(Thesis.class, id);
        if (thesis == null) {
            throw new InvalidEntityIdException();
        }
        return thesis;
    }

    public Thesis getThesis(User user, ThesisTopic topic){
        List<Thesis> t = em.createQuery("SELECT t FROM Thesis t " +
                "WHERE  t.worker = :user " +
                "AND t.topic = :topic", Thesis.class)
                .setParameter("user", user)
                .setParameter("topic", topic)
                .getResultList();
        if (t.isEmpty()){
            return null;
        } else {
            return t.get(0);
        }

    }

    public boolean createThesis(Thesis thesis){
        if (getThesis(thesis.getWorker(), thesis.getTopic()) != null){
            return false;
        }
        em.persist(thesis);
        em.flush();
        createEvent.fire(new ThesisEvent(thesis));
        return true;
    }

    public boolean createThesisFromApplication(Application application){
        Thesis thesis = new Thesis();
        thesis.setWorker(application.getApplicant());
        thesis.setTopic(application.getTopic());
        thesis.setStatus(Thesis.Status.IN_PROGRESS);
        return createThesis(thesis);
    }

    public List<Thesis> getTheses(){
        return em.createQuery("SELECT t FROM Thesis t", Thesis.class).getResultList();
    }

    public List<Thesis> getThesesByTopicId(Long topicId){
        return em.createQuery("SELECT t FROM Thesis t WHERE t.topic.id = :topicId", Thesis.class)
                .setParameter("topicId", topicId)
                .getResultList();
    }

    public long countTheses(ThesisTopic topic){
        return em.createQuery("SELECT count(t) " +
                "FROM Thesis t " +
                "WHERE t.topic = :topic", Long.class)
                .setParameter("topic", topic)
                .getSingleResult();
    }
}
