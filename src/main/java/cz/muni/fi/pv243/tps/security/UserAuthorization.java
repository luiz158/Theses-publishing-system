package cz.muni.fi.pv243.tps.security;

import cz.muni.fi.pv243.tps.ejb.TopicManager;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.annottions.Admin;
import cz.muni.fi.pv243.tps.security.annottions.IsCurrentUser;
import cz.muni.fi.pv243.tps.security.annottions.IsSupervisorOf;
import cz.muni.fi.pv243.tps.security.annottions.Supervisor;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@RequestScoped
public class UserAuthorization {

    @Inject
    private Identity identity;

    @Inject
    private UserManager userManager;

    @Inject
    private TopicManager topicManager;

    private UserIdentity currentUserIdentity;

    @Secures
    @Admin
    public boolean isAdmin() {
        return identity.isLoggedIn() && hasRole(Role.ADMIN.toString());
    }

    @Secures
    @Supervisor
    public boolean  isSupervisor(){
        if (isAdmin()){
            return true;
        }

        return identity.isLoggedIn() && hasRole(Role.SUPERVISOR.toString());
    }


    @Secures
    @IsSupervisorOf
    public boolean isSupervisorOf() {
        if (!identity.isLoggedIn()) {
            return false;
        }

        if (isAdmin()) {
            return true;
        }

        String currentUserId = getQueryParam("id");

        if (currentUserId == null) {
            return false;
        }

        UserIdentity loggedInUser = (UserIdentity) identity.getUser();

        if (currentUserIdentity == null) {
            currentUserIdentity = topicManager.getTopic(Long.parseLong(currentUserId)).getSupervisor().getUserIdentity();
        }

        return loggedInUser.equals(currentUserIdentity);
    }

    @Secures
    @IsCurrentUser
    public boolean isCurrentUser() {
        if (!identity.isLoggedIn()) {
            return false;
        }

        if (isAdmin()) {
            return true;
        }

        String currentUserId = getQueryParam("id");

        // No id means that the user is viewing his profile
        if (currentUserId == null) {
            return true;
        }

        UserIdentity loggedInUser = (UserIdentity) identity.getUser();

        if (currentUserIdentity == null) {
            currentUserIdentity = userManager.getUser(Long.parseLong(currentUserId)).getUserIdentity();
        }

        return loggedInUser.equals(currentUserIdentity);
    }

    public boolean hasRole(String role) {
        return role.equals(((UserIdentity) identity.getUser()).getRole().toString());
    }

    private String getQueryParam(String param) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param);
    }
}
