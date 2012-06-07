package cz.muni.fi.pv243.tps.action.users;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
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
    private UserManager userManager;

    private User editedUser;

    @Begin
    public void setUserById(String id) {
        editedUser = userManager.getUser(Long.parseLong(id));
    }

    @End
    public String edit() {
        userManager.editUser(editedUser);
        return "users?faces-redirect=true";
    }

    @Produces
    @Named
    public User getEditedUser() {
        return editedUser;
    }
}