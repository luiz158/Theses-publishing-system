package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Thesis;

import javax.ejb.Stateless;
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


    public Thesis getThesis(Long id){
        return em.find(Thesis.class, id);
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
