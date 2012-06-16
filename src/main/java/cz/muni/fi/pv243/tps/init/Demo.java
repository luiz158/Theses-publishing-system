package cz.muni.fi.pv243.tps.init;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.security.Role;
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
        spvsr.getUserIdentity().setRole(Role.SUPERVISOR);
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        User admin = new User();
        admin.setUserIdentity(new UserIdentity("admin", "password"));
        admin.getUserIdentity().setRole(Role.ADMIN);
        admin.setName(new User.Name("Admin", "Prvni"));
        admin.setEmail("admin@admin.cz");

        User student = new User();
        student.setUserIdentity(new UserIdentity("student", "password"));
        student.getUserIdentity().setRole(Role.STUDENT);
        student.setName(new User.Name("Student", "Druhy"));
        student.setEmail("stud@muni.cz");

        em.persist(spvsr);
        em.persist(admin);
        em.persist(student);

        // Theses topics
        ThesisTopic topic = new ThesisTopic();
        topic.setSupervisor(spvsr);
        topic.setCapacity(2);
        topic.setTitle("Thesis topic 1");
        topic.setDescription("Some dummy description for testing purpose");

        em.persist(topic);
    }
}
