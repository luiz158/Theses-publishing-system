package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;

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
