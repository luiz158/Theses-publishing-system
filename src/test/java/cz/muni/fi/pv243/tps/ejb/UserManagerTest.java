package cz.muni.fi.pv243.tps.ejb;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationOperationException;
import cz.muni.fi.pv243.tps.init.Demo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@RunWith(Arquillian.class)
public class UserManagerTest {

    @Inject
    private UserManager userManager;

    @Deployment
    public static WebArchive createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers
                .use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml");

        WebArchive archive = ShrinkWrap.create(WebArchive.class)
                .addClass(UserManager.class)
                .addClass(Demo.class)
                .addPackage(User.class.getPackage())
                .addPackage(InvalidApplicationOperationException.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsLibraries(resolver.artifacts(
                        "org.jboss.seam.security:seam-security"
                ).resolveAsFiles());

        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void testGetUser() {
        User user = userManager.getUser(1L);
        assertEquals("supervisor", user.getCredentials().getUsername());
    }
}
