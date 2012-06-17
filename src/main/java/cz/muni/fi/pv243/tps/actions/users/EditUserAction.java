package cz.muni.fi.pv243.tps.actions.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.annottions.Admin;
import cz.muni.fi.pv243.tps.security.annottions.IsCurrentUser;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.international.status.Messages;

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
public class EditUserAction implements Serializable {

    @Inject
    private transient UserManager userManager;

    @Inject
    private Messages messages;

    @Inject
    private PagesConfig pagesConfig;

    private User editedUser;

    public void setUserById(String id) {
        editedUser = userManager.getUser(Long.parseLong(id));
    }

    @Admin
    public String edit() {
        String returnedViewId = performEdit();
        messages.info("Profile of user with id {0} has been successfully updated.", editedUser.getId());
        return returnedViewId;
    }

    @IsCurrentUser
    public String editPassword() {
        String returnedViewId = performEdit();
        messages.info("Your password has been successfully updated.");
        return returnedViewId;
    }

    @IsCurrentUser
    public String editEmail() {
        String returnedViewId = performEdit();
        messages.info("Your email has been successfully updated.");
        return returnedViewId;
    }

    private String performEdit() {
        userManager.editUser(editedUser);
        return pagesConfig.getViewId(PagesConfig.Users.SHOW, editedUser.getId());
    }

    @Produces
    @Named
    public User getEditedUser() {
        return editedUser;
    }
}
