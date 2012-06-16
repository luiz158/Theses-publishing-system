package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.User;
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

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@ViewScoped
public class ShowUserAction implements Serializable {
    @Inject
    private transient UserManager userManager;

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
}
