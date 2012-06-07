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
    EntityManager em;

    public ThesisTopic getTopic(long id){
        return em.find(ThesisTopic.class, id);
    }

    public List<ThesisTopic> getTopics(){
           return em.createQuery("SELECT t FROM ThesisTopic t", ThesisTopic.class).getResultList();
    }

    public void createTopic(ThesisTopic topic){
        em.persist(topic);
    }

    public void apply(User user, ThesisTopic topic){
        try{
            Application application = new Application();
            application.setApplicant(user);
            application.setTopic(topic);
            em.persist(application);
            em.flush();
        } catch (Exception e){
            throw new InvalidApplicationAttemptException();
        }
    }

    public void apply(long userId, long topicId){
            User user = new User();
            user.setId(userId);
            ThesisTopic topic = new ThesisTopic();
            topic.setId(topicId);
            apply(user, topic);
    }
}
