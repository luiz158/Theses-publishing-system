package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.ejb.ThesisManager;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import org.jboss.seam.security.Identity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@ViewScoped
public class ShowUserAction implements Serializable {
    @Inject
    private transient UserManager userManager;

    @Inject
    private transient ThesisManager thesisManager;

    @Inject
    private transient ApplicationManager applicationManager;

    @Inject
    private Identity identity;

    private User user;

    public void setUserById(String id) {
        user = userManager.getUser(Long.parseLong(id));
    }

    public void setUserByUserIdentity() {
        user = userManager.getUserByUserIdentity((UserIdentity) identity.getUser());
    }

    @Produces
    @Named
    public User getUser() {
        return user;
    }

    private List<Application> currentApplications;

    @Produces
    @Named
    public List<Application> getUsersApplications() {
        if (currentApplications == null) {
            currentApplications = applicationManager.getApplicationsByUser(user);
        }

        return currentApplications;
    }

    private List<Thesis> currentTheses;

    @Produces
    @Named
    public List<Thesis> getUsersTheses() {
        if (currentTheses == null) {
            currentTheses = thesisManager.getThesesByWorker(user);
        }
        return currentTheses;
    }
}
