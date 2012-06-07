package cz.muni.fi.pv243.tps.init;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.TopicManager;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationAttemptException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Singleton
@Startup
public class Demo {

    @PersistenceContext
    EntityManager em;

    @Inject
    TopicManager topicManager;

    @PostConstruct
    public  void initialize(){
        // Demo users
        User spvsr = new User();
        spvsr.setUsername("supervisor");
        spvsr.setPassword("password");

        User student = new User();
        student.setUsername("student");
        student.setPassword("password");

        User student2 = new User();
        student2.setUsername("student2");
        student2.setPassword("password");

        em.persist(spvsr);
        em.persist(student);
        em.persist(student2);

        // Theses topics
        ThesisTopic topic = new ThesisTopic();
        topic.setSupervisor(spvsr);
        topic.setCapacity(3);
        topic.setTitle("Thesis topic 1");
        topic.setDescription("Some dummy description for testing purpose");

        em.persist(topic);

        // Theses
        Thesis thesis = new Thesis();
        thesis.setWorker(student);
        thesis.setTopic(topic);

        em.persist(thesis);

        // Theses applications
        Application application = new Application();
        application.setApplicant(student2);
        application.setTopic(topic);

        em.persist(application);
    }
}
