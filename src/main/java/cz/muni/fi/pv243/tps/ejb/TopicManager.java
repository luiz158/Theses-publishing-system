package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.events.TopicEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Update;
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
public class TopicManager {
    @PersistenceContext
    EntityManager entityManager;

    @Inject
    @Create
    private Event<TopicEvent> createEvent;

    @Inject
    @Update
    private Event<TopicEvent> updateEvent;

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
        createEvent.fire(new TopicEvent(topic));
    }

    public void editTopic(ThesisTopic topic){
        entityManager.merge(topic);
        updateEvent.fire(new TopicEvent(topic));
    }
}
