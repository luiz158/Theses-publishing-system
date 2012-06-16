package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.TopicEvent;
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
public class TopicManagerTest {
    @Inject
    private TopicManager topicManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction tx;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ArchiveTemplates.WEB_ARCHIVE
                .addClass(TopicManager.class)
                .addClass(UserIdentity.class)
                .addClass(Role.class)
                .addClass(TopicEvent.class)
                .addPackage(InvalidEntityIdException.class.getPackage())
                .addPackage(ThesisTopic.class.getPackage());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<ThesisTopic> topics = new ArrayList<ThesisTopic>();

    @Before
    public void setUp() throws SystemException, NotSupportedException {
        tx.begin();

        User spvsr = new User();
        spvsr.setUserIdentity(new UserIdentity("supervisor", "password"));
        spvsr.getUserIdentity().setRole(Role.SUPERVISOR);
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        entityManager.persist(spvsr);

        ThesisTopic topic1 = new ThesisTopic();
        topic1.setCapacity(1);
        topic1.setDescription("Thesis topic 1 description");
        topic1.setTitle("Topic1");
        topic1.setSupervisor(spvsr);

        ThesisTopic topic2 = new ThesisTopic();
        topic2.setCapacity(1);
        topic2.setDescription("Thesis topic 2 description");
        topic2.setTitle("Topic2");
        topic2.setSupervisor(spvsr);

        ThesisTopic topic3 = new ThesisTopic();
        topic3.setCapacity(1);
        topic3.setDescription("Thesis topic 3 description");
        topic3.setTitle("Topic3");
        topic3.setSupervisor(spvsr);

        entityManager.persist(topic1);
        entityManager.persist(topic2);
        entityManager.persist(topic3);

        topics.add(0, topic1);
        topics.add(1, topic2);
        topics.add(2, topic3);
    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }

    @Test
    public void getExistingTopicTest() {
        ThesisTopic expected = topics.get(0);
        ThesisTopic actual = topicManager.getTopic(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidEntityIdException.class)
    public void getNotExistingTopicTest() {
        topicManager.getTopic(Long.MAX_VALUE);
    }

    @Test
    public void getTopicsTest() {
        List<ThesisTopic> topics = topicManager.getTopics();

        for (ThesisTopic t : this.topics) {
            assertTrue(topics.contains(t));
        }

        assertEquals(this.topics.size(), topics.size());
    }

    @Test
    public void createTopicTest() {
        ThesisTopic expected = new ThesisTopic();
        expected.setCapacity(1);
        expected.setDescription("Thesis topic 4 description");
        expected.setTitle("Topic4");
        expected.setSupervisor(new User(4L));

        topicManager.createTopic(expected);

        ThesisTopic actual = entityManager.find(ThesisTopic.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void editTopicTest() {
        ThesisTopic expected = topics.get(0);
        topicManager.editTopic(expected);

        ThesisTopic actual = entityManager.find(ThesisTopic.class, expected.getId());

        assertEquals(expected, actual);
    }

}
