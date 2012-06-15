package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.Application;
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
    private UserTransaction transaction;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ArchiveTemplates.WEB_ARCHIVE
                .addClass(ThesisManager.class)
                .addClass(UserIdentity.class)
                .addClass(Role.class)
                .addClass(ThesisEvent.class)
                .addPackage(InvalidEntityIdException.class.getPackage())
                .addPackage(Thesis.class.getPackage());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<Thesis> theses = new ArrayList<Thesis>();

    @Before
    public void setUp() {
        final User spvsr = new User();
        spvsr.setUserIdentity(new UserIdentity("supervisor", "password"));
        spvsr.getUserIdentity().setRole(Role.SUPERVISOR);
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        final User student = new User();
        student.setUserIdentity(new UserIdentity("student", "password"));
        student.getUserIdentity().setRole(Role.STUDENT);
        student.setName(new User.Name("Student", "Druhy"));
        student.setEmail("student@email.com");

        final ThesisTopic topic = new ThesisTopic();
        topic.setCapacity(1);
        topic.setDescription("Thesis topic 1 description");
        topic.setTitle("Topic1");
        topic.setSupervisor(spvsr);

        new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                entityManager.persist(spvsr);
                entityManager.persist(student);
                entityManager.persist(topic);
                return null;
            }
        }).execute();

        final Thesis thesis1 = new Thesis();
        thesis1.setTopic(topic);
        thesis1.setWorker(student);

        final Thesis thesis2 = new Thesis();
        thesis2.setTopic(topic);
        thesis2.setWorker(student);

        new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                entityManager.persist(thesis1);
                entityManager.persist(thesis2);
                return null;
            }
        }).execute();

        theses.add(0, thesis1);
        theses.add(1, thesis2);
    }

    @After
    public void tearDown() {
        new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                List<Thesis> theses = entityManager
                        .createQuery("SELECT t FROM Thesis t", Thesis.class)
                        .getResultList();
                for (Thesis t : theses) {
                    entityManager.remove(t);
                }
                List<ThesisTopic> topics = entityManager
                        .createQuery("SELECT t FROM ThesisTopic t", ThesisTopic.class)
                        .getResultList();
                for (ThesisTopic t : topics) {
                    entityManager.remove(t);
                }
                List<User> users = entityManager
                        .createQuery("SELECT u FROM User u", User.class)
                        .getResultList();
                for (User u : users) {
                    entityManager.remove(u);
                }
                return null;
            }
        }).execute();
    }

    @Test
    public void getExistingThesisTest() {
        Thesis expected = theses.get(0);
        Thesis actual = thesisManager.getThesis(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidEntityIdException.class)
    public void getNotExistiongThesisTest() {
        thesisManager.getThesis(Long.MAX_VALUE);
    }

    @Test
    public void createThesisTest() {
        final Thesis expected = new Thesis();
        expected.setTopic(theses.get(0).getTopic());
        expected.setWorker(theses.get(0).getWorker());
        thesisManager.createThesis(expected);

        final Thesis actual = (Thesis) new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                return entityManager.find(Thesis.class, expected.getId());
            }
        }).execute();

        assertEquals(expected, actual);
    }

    @Test
    public void getTopicsTest() {
        List<Thesis> theses = thesisManager.getTheses();

        for (int i = 0; i < theses.size(); i++) {
            assertEquals(this.theses.get(i), theses.get(i));
        }
    }

    @Test
    public void getTopicsByTopicIdTest() {
        Long topicId = this.theses.get(0).getTopic().getId();
        List<Thesis> theses = thesisManager.getThesesByTopicId(topicId);

        List<Thesis> thesesWithGivenTopicId = new ArrayList<Thesis>();

        for (Thesis t : this.theses) {
            if (t.getTopic().getId().equals(topicId)) {
                thesesWithGivenTopicId.add(t);
            }
        }

        for (int i = 0; i < theses.size(); i++) {
            assertEquals(thesesWithGivenTopicId.get(i), theses.get(i));
        }
    }
}
