package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.events.ThesisEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Update;

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
    private EntityManager em;

    @Inject
    @Create
    private Event<ThesisEvent> createEvent;

    public Thesis getThesis(Long id){
        return em.find(Thesis.class, id);
    }

    public void createThesis(Thesis thesis){
        em.persist(thesis);
        createEvent.fire(new ThesisEvent(thesis));
    }

    public void createThesisFromApplication(Application application){
        Thesis thesis = new Thesis();
        thesis.setWorker(application.getApplicant());
        thesis.setTopic(application.getTopic());
        createThesis(thesis);
    }

    public List<Thesis> getTheses(){
        return em.createQuery("SELECT t FROM Thesis t", Thesis.class).getResultList();
    }

    public List<Thesis> getThesesByTopicId(Long topicId){
        return em.createQuery("SELECT t FROM Thesis t WHERE t.topic.id = :topicId", Thesis.class)
                .setParameter("topicId", topicId)
                .getResultList();
    }
}
