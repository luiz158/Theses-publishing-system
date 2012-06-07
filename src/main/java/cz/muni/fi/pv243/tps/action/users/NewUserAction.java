package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@RequestScoped
public class NewUserAction implements Serializable  {
    @Inject
    private UserManager userManager;

    private User newUser = new User();

    public String create() {
        userManager.createUser(newUser);
        return "users?faces-redirect=true";
    }

    @Produces
    @Named
    public User getNewUser() {
        return newUser;
    }
}