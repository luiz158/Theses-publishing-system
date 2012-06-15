package cz.muni.fi.pv243.tps.security;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Named
@ApplicationScoped
public class UserAuthorization {

    @Secures
    @Admin
    public boolean isAdmin(Identity identity) {
        return identity.isLoggedIn()
                && Role.ADMIN.equals(((UserIdentity) identity.getUser()).getRole());
    }

    @Secures
    @Supervisor
    public boolean  isSupervisor(Identity identity){
        if (isAdmin(identity)){
            return true;
        }

        return identity.isLoggedIn()
                && Role.SUPERVISOR.equals(((UserIdentity) identity.getUser()).getRole());
    }


    //TODO: FIX ME
    @Secures
    @IsSupervisorOf
    public boolean isSupervisorOf(Identity identity, @Current ThesisTopic thesisTopic) {
        System.err.println("DUMP");
        System.err.println("TOPIC: " + thesisTopic);
        System.err.println("IDENTITY: " + identity);
        if (!identity.isLoggedIn()) {
            return false;
        }

        if (isAdmin(identity)) {
            return true;
        }
        UserIdentity currentUser = (UserIdentity) identity.getUser();
        UserIdentity supervisor = thesisTopic.getSupervisor().getUserIdentity();

        return currentUser.equals(supervisor);
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

        return currentUser.equals(editedUser);
    }

    public boolean hasRole(Identity identity, String role) {
        return role.equals(((UserIdentity) identity.getUser()).getRole().toString());
    }
}
