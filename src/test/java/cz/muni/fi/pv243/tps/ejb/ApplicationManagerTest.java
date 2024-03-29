package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;
import cz.muni.fi.pv243.tps.security.Role;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@RunWith(Arquillian.class)
public class ApplicationManagerTest {

    @Inject
    private ApplicationManager applicationManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction tx;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ArchiveTemplates.WEB_ARCHIVE
                .addClass(UserIdentity.class)
                .addClass(Role.class)
                .addPackage(ApplicationEvent.class.getPackage())
                .addPackage(TopicManager.class.getPackage())
                .addPackage(InvalidEntityIdException.class.getPackage())
                .addPackage(Application.class.getPackage());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<Application> applications = new ArrayList<Application>();

    @Before
    public void setUp() throws SystemException, NotSupportedException {
        tx.begin();

        User student1 = new User();
        student1.setUserIdentity(new UserIdentity("student", "password"));
        student1.getUserIdentity().setRole(Role.STUDENT);
        student1.setName(new User.Name("Student", "Prvni"));
        student1.setEmail("student@email.com");

        User student2 = new User();
        student2.setUserIdentity(new UserIdentity("student2", "password"));
        student2.getUserIdentity().setRole(Role.STUDENT);
        student2.setName(new User.Name("Student", "Druhy"));
        student2.setEmail("student2@email.com");

        User spvsr = new User();
        spvsr.setUserIdentity(new UserIdentity("supervisor", "password"));
        spvsr.getUserIdentity().setRole(Role.SUPERVISOR);
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        ThesisTopic topic = new ThesisTopic();
        topic.setCapacity(1);
        topic.setDescription("Thesis topic 1 description");
        topic.setTitle("Topic1");
        topic.setSupervisor(spvsr);

        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(spvsr);
        entityManager.persist(topic);

        Application application1 = new Application();
        application1.setApplicant(student1);
        application1.setTopic(topic);
        application1.setStatus(Application.Status.WAITING);

        Application application2 = new Application();
        application2.setApplicant(student2);
        application2.setTopic(topic);
        application2.setStatus(Application.Status.WAITING);

        entityManager.persist(application1);
        entityManager.persist(application2);

        applications.add(0, application1);
        applications.add(1, application2);
    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }

    @Test
    public void getExistingApplicationTest() {
        Application expected = applications.get(0);
        Application actual = applicationManager.getApplication(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidEntityIdException.class)
    public void getNotExistingApplicationTest() {
        applicationManager.getApplication(Long.MAX_VALUE);
    }

    @Test
    public void acceptApplicationTest() {
        applicationManager.acceptApplication(applications.get(0));

        Application actual = entityManager.find(Application.class, applications.get(0).getId());

        assertEquals(Application.Status.ACCEPTED, actual.getStatus());
    }

    @Test
    public void declineApplicationTest() {
        applicationManager.declineApplication(applications.get(0));

        Application actual = entityManager.find(Application.class, applications.get(0).getId());

        assertEquals(Application.Status.DECLINED, actual.getStatus());
    }
}
