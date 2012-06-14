package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */

@Stateless
public class TopicManager {
    @PersistenceContext
    private EntityManager entityManager;

    public ThesisTopic getTopic(Long id){
        ThesisTopic topic =  entityManager.find(ThesisTopic.class, id);
        if (topic == null){
            throw new InvalidEntityIdException();
        }
        return  topic;
    }

    public List<ThesisTopic> getTopics(){
           return entityManager.createQuery("SELECT t FROM ThesisTopic t", ThesisTopic.class).getResultList();
    }

    public void createTopic(ThesisTopic topic){
        entityManager.persist(topic);
    }

    public void editTopic(ThesisTopic topic){
        entityManager.merge(topic);
    }
}
