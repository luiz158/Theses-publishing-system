package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.international.status.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@RequestScoped
public class NewUserAction {
    @Inject
    private UserManager userManager;

    @Inject
    private Messages messages;

    @Inject
    private PagesConfig pagesConfig;

    private User newUser;

    @PostConstruct
    public void init() {
        newUser = new User();
        newUser.setUserIdentity(new UserIdentity());
        newUser.setName(new User.Name());
    }

    public String create() {
        userManager.createUser(newUser);
        messages.info("Your account has been successfully created.");
        return pagesConfig.getViewId(PagesConfig.Pages.INDEX);
    }

    @Produces
    @Named
    public User getNewUser() {
        return newUser;
    }
}
