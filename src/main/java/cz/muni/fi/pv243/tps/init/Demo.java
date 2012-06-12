package cz.muni.fi.pv243.tps.init;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.security.UserIdentity;

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
        spvsr.setUserIdentity(new UserIdentity("supervisor", "password"));
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        User student = new User();
        student.setUserIdentity(new UserIdentity("student", "password"));
        student.setName(new User.Name("Studen", "Prvni"));
        student.setEmail("stud@muni.cz");

        User student2 = new User();
        student2.setUserIdentity(new UserIdentity("student2", "password"));
        student2.setName(new User.Name("Student", "Druhy"));
        student2.setEmail("stud2@muni.cz");

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
