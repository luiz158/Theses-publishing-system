package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationAttemptException;

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
    EntityManager entityManager;

    public ThesisTopic getTopic(Long id){
        return entityManager.find(ThesisTopic.class, id);
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

    public void apply(User user, ThesisTopic topic){
        try{
            Application application = new Application();
            application.setApplicant(user);
            application.setTopic(topic);
            entityManager.persist(application);
            entityManager.flush();
        } catch (Exception e){
            throw new InvalidApplicationAttemptException();
        }
    }

    public void apply(Long userId, Long topicId){
            User user = new User();
            user.setId(userId);
            ThesisTopic topic = new ThesisTopic();
            topic.setId(topicId);
            apply(user, topic);
    }
}
