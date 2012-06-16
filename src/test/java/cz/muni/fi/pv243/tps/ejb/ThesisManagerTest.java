package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.ThesisEvent;
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

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@RunWith(Arquillian.class)
public class ThesisManagerTest {
    @Inject
    private ThesisManager thesisManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction tx;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ArchiveTemplates.WEB_ARCHIVE
                .addClass(UserIdentity.class)
                .addClass(Role.class)
                .addPackage(ThesisEvent.class.getPackage())
                .addPackage(ThesisManager.class.getPackage())
                .addPackage(InvalidEntityIdException.class.getPackage())
                .addPackage(Thesis.class.getPackage());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<Thesis> theses = new ArrayList<Thesis>();

    @Before
    public void setUp() throws SystemException, NotSupportedException {
        tx.begin();

        User spvsr = new User();
        spvsr.setUserIdentity(new UserIdentity("supervisor", "password"));
        spvsr.getUserIdentity().setRole(Role.SUPERVISOR);
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        User student = new User();
        student.setUserIdentity(new UserIdentity("student", "password"));
        student.getUserIdentity().setRole(Role.STUDENT);
        student.setName(new User.Name("Student", "Druhy"));
        student.setEmail("student@email.com");

        User student2 = new User();
        student2.setUserIdentity(new UserIdentity("student2", "password"));
        student2.getUserIdentity().setRole(Role.STUDENT);
        student2.setName(new User.Name("Student", "Treti"));
        student2.setEmail("student2@email.com");

        ThesisTopic topic = new ThesisTopic();
        topic.setCapacity(1);
        topic.setDescription("Thesis topic 1 description");
        topic.setTitle("Topic1");
        topic.setSupervisor(spvsr);

        entityManager.persist(spvsr);
        entityManager.persist(student);
        entityManager.persist(student2);
        entityManager.persist(topic);

        Thesis thesis1 = new Thesis();
        thesis1.setTopic(topic);
        thesis1.setWorker(student);
        thesis1.setStatus(Thesis.Status.IN_PROGRESS);

        Thesis thesis2 = new Thesis();
        thesis2.setTopic(topic);
        thesis2.setWorker(student2);
        thesis2.setStatus(Thesis.Status.SUCCESSFUL);

        entityManager.persist(thesis1);
        entityManager.persist(thesis2);

        theses.add(0, thesis1);
        theses.add(1, thesis2);
    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }

    @Test
    public void getExistingThesisTest() {
        Thesis expected = theses.get(0);
        Thesis actual = thesisManager.getThesis(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidEntityIdException.class)
    public void getNotExistingThesisTest() {
        thesisManager.getThesis(Long.MAX_VALUE);
    }

    @Test
    public void getExistingThesisByWorkerAndTopicTest() {
        Thesis expected = theses.get(0);
        Thesis actual = thesisManager.getThesis(expected.getWorker(), expected.getTopic());

        assertEquals(expected, actual);
    }

    @Test
    public void createThesisTest() {
        Thesis expected = new Thesis();
        expected.setTopic(theses.get(0).getTopic());

        User user = new User();
        user.setName(new User.Name("nekdo", "neco"));
        user.setEmail("email@email.com");
        user.setUserIdentity(new UserIdentity("nekdo", "password"));
        user.getUserIdentity().setRole(Role.STUDENT);

        entityManager.persist(user);

        expected.setWorker(user);
        thesisManager.createThesis(expected);

        Thesis actual = entityManager.find(Thesis.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void getThesesTest() {
        List<Thesis> theses = thesisManager.getTheses();

        for (Thesis t : this.theses) {
            assertTrue(theses.contains(t));
        }

        assertEquals(this.theses.size(), theses.size());
    }

    @Test
    public void getThesesByTopicIdTest() {
        Long topicId = this.theses.get(0).getTopic().getId();

        List<Thesis> expected = new ArrayList<Thesis>();
        for (Thesis t : this.theses) {
            if (t.getTopic().getId().equals(topicId)) {
                expected.add(t);
            }
        }


        List<Thesis> actual = thesisManager.getThesesByTopicId(topicId);

        for (Thesis t : expected) {
            assertTrue(actual.contains(t));
        }

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void countThesesBytTopicTest() {
        ThesisTopic topic = theses.get(0).getTopic();

        long expected = 0;

        for (Thesis t : theses) {
            if (topic.equals(t.getTopic())) {
                expected++;
            }
        }

        long actual = thesisManager.countTheses(topic);

        assertEquals(expected, actual);
    }

    @Test
    public void getThesesByWorkerTest() {
        User worker = theses.get(0).getWorker();

        List<Thesis> expected = new ArrayList<Thesis>();
        for (Thesis t : theses) {
            if (t.getWorker().equals(worker)) {
                expected.add(t);
            }
        }

        List<Thesis> actual = thesisManager.getThesesByWorker(worker);

        for (Thesis t : expected) {
            assertTrue(actual.contains(t));
        }

        assertEquals(expected.size(), actual.size());
    }
}
