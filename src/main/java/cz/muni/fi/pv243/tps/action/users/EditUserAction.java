package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.Admin;
import cz.muni.fi.pv243.tps.security.IsCurrentUser;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.international.status.Messages;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
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

    @IsCurrentUser
    public String edit() {
        userManager.editUser(editedUser);
        messages.info("Profile of user with id {0} has been successfully updated.", editedUser.getId());
        return pagesConfig.getViewId(PagesConfig.Users.SHOW, editedUser.getId());
    }

    @Produces
    @Named
    @Current
    public User getEditedUser() {
        return editedUser;
    }
}
