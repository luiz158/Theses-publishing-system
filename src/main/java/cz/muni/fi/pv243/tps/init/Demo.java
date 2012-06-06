package cz.muni.fi.pv243.tps.init;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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
