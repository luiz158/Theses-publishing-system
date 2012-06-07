package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@RequestScoped
public class ShowUserAction {
    @Inject
    private UserManager userManager;

    private User user;

    public void setUserById(String id) {
        user = userManager.getUser(Long.parseLong(id));
    }

    @Produces
    @Named
    public User getUser() {
        return user;
    }
}
