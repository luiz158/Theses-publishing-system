package cz.muni.fi.pv243.tps.security;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@ApplicationScoped
public class UserAuthorization {

    @Secures
    @Admin
    public boolean isAdmin(Identity identity) {
        return identity.isLoggedIn()
                && Role.ADMIN.equals(((UserIdentity) identity.getUser()).getRole());
    }

    @Secures
    @IsSupervisorOf
    public boolean isSupervisorOf(Identity identity, @Current ThesisTopic thesisTopic) {
        if (identity.isLoggedIn()) {
            return false;
        }

        if (isAdmin(identity)) {
            return true;
        }

        UserIdentity currentUser = (UserIdentity) identity.getUser();
        UserIdentity supervisor = thesisTopic.getSupervisor().getUserIdentity();

        return currentUser.getUsername().equals(supervisor.getUsername());
    }

    @Secures
    @IsCurrentUser
    public boolean isCurrentUser(Identity identity, @Current User user) {
        if (identity.isLoggedIn()) {
            return false;
        }

        if (isAdmin(identity)) {
            return true;
        }

        UserIdentity currentUser = (UserIdentity) identity.getUser();
        UserIdentity editedUser = user.getUserIdentity();

        return currentUser.getUsername().equals(editedUser.getUsername());
    }
}
