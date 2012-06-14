package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
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
import javax.transaction.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@RunWith(Arquillian.class)
public class TopicManagerTest {
    @Inject
    private TopicManager topicManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction transaction;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ArchiveTemplates.WEB_ARCHIVE
                .addClass(TopicManager.class)
                .addClass(UserIdentity.class)
                .addClass(Role.class)
                .addPackage(ThesisTopic.class.getPackage());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<ThesisTopic> topics = new ArrayList<ThesisTopic>();

    @Before
    public void setUp() {
        final User spvsr = new User();
        spvsr.setUserIdentity(new UserIdentity("supervisor", "password"));
        spvsr.getUserIdentity().setRole(Role.SUPERVISOR);
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                entityManager.persist(spvsr);
                return null;
            }
        }).execute();

        final ThesisTopic topic1 = new ThesisTopic();
        topic1.setCapacity(1);
        topic1.setDescription("Thesis topic 1 description");
        topic1.setTitle("Topic1");
        topic1.setSupervisor(spvsr);

        final ThesisTopic topic2 = new ThesisTopic();
        topic2.setCapacity(1);
        topic2.setDescription("Thesis topic 2 description");
        topic2.setTitle("Topic2");
        topic2.setSupervisor(spvsr);

        final ThesisTopic topic3 = new ThesisTopic();
        topic3.setCapacity(1);
        topic3.setDescription("Thesis topic 3 description");
        topic3.setTitle("Topic3");
        topic3.setSupervisor(spvsr);

        new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                entityManager.persist(topic1);
                entityManager.persist(topic2);
                entityManager.persist(topic3);
                return null;
            }
        }).execute();

        topics.add(0, topic1);
        topics.add(1, topic2);
        topics.add(2, topic3);
    }

    @After
    public void tearDown() {
        new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
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
    public void getExistingTopicTest() {
        ThesisTopic expected = topics.get(0);
        ThesisTopic actual = topicManager.getTopic(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void getNotExistingTopicTest() {
        ThesisTopic topic = topicManager.getTopic(Long.MAX_VALUE);
        assertNull(topic);
    }

    @Test
    public void getTopicsTest() {
        List<ThesisTopic> topics = topicManager.getTopics();

        for (int i = 0; i < topics.size(); i++) {
            assertEquals(this.topics.get(i), topics.get(i));
        }
    }

    @Test
    public void createTopicTest() {
        final ThesisTopic expected = new ThesisTopic();
        expected.setCapacity(1);
        expected.setDescription("Thesis topic 4 description");
        expected.setTitle("Topic4");
        expected.setSupervisor(new User(4L));

        topicManager.createTopic(expected);

        final ThesisTopic actual = (ThesisTopic) new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                return entityManager.find(ThesisTopic.class, expected.getId());
            }
        }).execute();

        assertEquals(expected, actual);
    }

    @Test
    public void editTopicTest() {
        final ThesisTopic expected = topics.get(0);
        topicManager.editTopic(expected);

        final ThesisTopic actual = (ThesisTopic) new TransactionProxy(transaction, new TransactionProxyable() {
            @Override
            public Object execute() {
                return entityManager.find(ThesisTopic.class, expected.getId());
            }
        }).execute();

        assertEquals(expected, actual);
    }

}
