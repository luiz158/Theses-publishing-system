package cz.muni.fi.pv243.tps.actions.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.Role;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class UsersAction {
    @Inject
    private UserManager userManager;

    @Produces
    @Model
    public List<User> getUsers() {
        return userManager.getUsers();
    }

    @Produces
    @Model
    public List<User> getSupervisors() {
        return userManager.getUsersByRole(Role.SUPERVISOR);
    }

    @Produces
    @Named
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }
}
