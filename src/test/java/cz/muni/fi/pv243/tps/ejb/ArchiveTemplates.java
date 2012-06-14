package cz.muni.fi.pv243.tps.ejb;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import javax.enterprise.inject.Produces;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class ArchiveTemplates {

    public static final WebArchive WEB_ARCHIVE;

    static {
        WEB_ARCHIVE = ShrinkWrap.create(WebArchive.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("jbossas-ds.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addClass(TransactionProxy.class)
                .addClass(TransactionProxyable.class)
                .addAsLibraries(DependencyResolvers
                        .use(MavenDependencyResolver.class)
                        .includeDependenciesFromPom("pom.xml")
                        .resolveAsFiles());
    }
}
