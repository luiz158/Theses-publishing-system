package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.events.UserEvent;
import cz.muni.fi.pv243.tps.exceptions.InvalidEntityIdException;
import cz.muni.fi.pv243.tps.exceptions.InvalidSystemOperatinException;
import cz.muni.fi.pv243.tps.exceptions.InvalidUserIdentityException;
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
public class UserManagerTest {

    @Inject
    private UserManager userManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction tx;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive archive = ArchiveTemplates.WEB_ARCHIVE
                .addClass(UserManager.class)
                .addClass(UserIdentity.class)
                .addClass(Role.class)
                .addPackage(User.class.getPackage())
                .addPackage(InvalidSystemOperatinException.class.getPackage())
                .addPackage(UserEvent.class.getPackage());

        System.out.println(archive.toString(true));
        return archive;
    }

    private final List<User> users = new ArrayList<User>();

    @Before
    public void setUp() throws SystemException, NotSupportedException {
        tx.begin();

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

        entityManager.persist(spvsr);
        entityManager.persist(admin);
        entityManager.persist(student);

        users.add(0, spvsr);
        users.add(1, admin);
        users.add(2, student);
    }

    @After
    public void tearDown() throws SystemException {
        tx.rollback();
    }

    @Test
    public void createUserTest() {
        User expected = new User();
        expected.setUserIdentity(new UserIdentity("TestUser", "TestUserPassword"));
        expected.setName(new User.Name("Test", "User"));
        userManager.createUser(expected);

        User actual = entityManager.find(User.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void editUserTest() {
        User expected = users.get(0);
        expected.setUserIdentity(new UserIdentity("supervisor2", "newpassword"));
        userManager.editUser(expected);

        User actual = entityManager.find(User.class, expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void getExistingUserTest() {
        User expected = users.get(0);
        User actual = userManager.getUser(expected.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = InvalidEntityIdException.class)
    public void getNotExistingUserTest() {
        userManager.getUser(Long.MAX_VALUE);
    }

    @Test
    public void getExistingUserByUserIdentityTest() {
        User expected = users.get(0);
        for (User u : userManager.getUsers()) {
            System.out.println("DEBUG " + u.getUserIdentity().getUsername() + " " + u.getUserIdentity().getPassword());
        }
        User actual = userManager.getUserByUserIdentity(expected.getUserIdentity());

        assertEquals(expected, actual);
        assertEquals(expected.getUserIdentity(), actual.getUserIdentity());
    }

    @Test(expected = InvalidUserIdentityException.class)
    public void getNotExistingUserByUserIdentityTest() {
        userManager.getUserByUserIdentity(new UserIdentity("NotExistingUsername", "NotExistingPassword"));
    }

    @Test
    public void getUsersTest() {
        List<User> users = userManager.getUsers();

        for (User u : this.users) {
            assertTrue(users.contains(u));
        }

        assertEquals(this.users.size(), users.size());
    }

    @Test
    public void getUsersByRoleTest() {
        List<User> expected = new ArrayList<User>();
        for (User u : this.users) {
            if (u.getUserIdentity().getRole().equals(Role.SUPERVISOR)) {
                expected.add(u);
            }
        }

        List<User> actual = userManager.getUsersByRole(Role.SUPERVISOR);

        for (User u : expected) {
            assertTrue(actual.contains(u));
        }

        assertEquals(expected.size(), actual.size());
    }
}
