package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.UserEvent;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationOperationException;
import cz.muni.fi.pv243.tps.exceptions.InvalidCredentialsException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
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
public class UserManagerTest {

    @Inject
    private UserManager userManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction transaction;

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers
                .use(MavenDependencyResolver.class)
                .includeDependenciesFromPom("pom.xml");

        WebArchive archive = ShrinkWrap.create(WebArchive.class)
                .addClass(UserManager.class)
                .addPackage(User.class.getPackage())
                .addPackage(InvalidApplicationOperationException.class.getPackage())
                .addPackage(UserEvent.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(resolver.resolveAsFiles());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<User> users = new ArrayList<User>();

    @Before
    public void setUp() {
        User spvsr = new User();
        spvsr.setCredentials(new User.Credentials("supervisor", "password"));
        spvsr.setName(new User.Name("Supervisor", "Prvni"));
        spvsr.setEmail("supr@email.com");

        User student = new User();
        User.Credentials credentials = new User.Credentials();
        student.setCredentials(new User.Credentials("student", "password"));
        student.setName(new User.Name("Studen", "Prvni"));
        student.setEmail("stud@muni.cz");

        User student2 = new User();
        student2.setCredentials(new User.Credentials("student2", "password"));
        student2.setName(new User.Name("Student", "Druhy"));
        student2.setEmail("stud2@muni.cz");

        try {
            transaction.begin();
            entityManager.persist(spvsr);
            entityManager.persist(student);
            entityManager.persist(student2);
            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception e1) {
            }
        }

        users.add(0, spvsr);
        users.add(1, student);
        users.add(2, student2);
    }

    @After
    public void tearDown() {
        try {
            transaction.begin();
            List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
            for (User u : users) {
                entityManager.remove(u);
            }
            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception e1) {
            }
        }
    }

    @Test
    public void testCreateUser() {
        User expected = new User();
        expected.setCredentials(new User.Credentials("TestUser", "TestUserPassword"));
        expected.setName(new User.Name("Test", "User"));
        userManager.createUser(expected);

        User actual = userManager.getUser(expected.getId());

        assertEquals(expected.getCredentials().getUsername(), actual.getCredentials().getUsername());
    }

    @Test
    public void testEditUser() {
        User expected = userManager.getUser(users.get(0).getId());
        expected.setCredentials(new User.Credentials("supervisor2", "newpassword"));
        userManager.editUser(expected);

        User actual = userManager.getUser(users.get(0).getId());

        assertEquals(expected.getCredentials().getUsername(), actual.getCredentials().getUsername());
    }

    @Test
    public void testGetExistingUser() {
        User expected = users.get(0);
        User actual = userManager.getUser(expected.getId());
        assertEquals(expected.getCredentials().getUsername(), actual.getCredentials().getUsername());
    }

    @Test
    public void testGetNotExistingUser() {
        User user = userManager.getUser(Long.MAX_VALUE);
        assertNull(user);
    }
    
    @Test
    public void testGetExistingUserByCredentials() {
        User expected = users.get(0);
        User actual = userManager.getUserByCredentials(expected.getCredentials());
        assertEquals(expected.getCredentials(), actual.getCredentials());
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testGetNotExistingUserByCredentials() {
        userManager.getUserByCredentials(new User.Credentials("NotExistingUsername", "NotExistingPassword"));
    }

    @Test
    public void testGetUsers() {
        List<User> users = userManager.getUsers();

        assertEquals(this.users.get(0).getCredentials().getUsername(), users.get(0).getCredentials().getUsername());
        assertEquals(this.users.get(1).getCredentials().getUsername(), users.get(1).getCredentials().getUsername());
        assertEquals(this.users.get(2).getCredentials().getUsername(), users.get(2).getCredentials().getUsername());
    }
}
