package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.Admin;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.international.status.Messages;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@ConversationScoped
public class EditUserAction implements Serializable {

    @Inject
    private transient UserManager userManager;

    @Inject
    private Messages messages;

    private User editedUser;

    @Begin
    public void setUserById(String id) {
        editedUser = userManager.getUser(Long.parseLong(id));
    }

    @End
    public String edit() {
        userManager.editUser(editedUser);
        messages.info("Your profile has been successfully updated.");
        return PagesConfig.PAGES_PREFIX + "users/show.xhtml?id=" + editedUser.getId() + "&faces-redirect=true";
    }

    @Produces
    @Named
    @Current
    public User getEditedUser() {
        return editedUser;
    }
}
