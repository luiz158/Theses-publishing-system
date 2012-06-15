package cz.muni.fi.pv243.tps.security;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.exceptions.InvalidUserIdentityException;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import javax.inject.Inject;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class UserAuthenticator extends BaseAuthenticator {

    @Inject
    private Credentials credentials;

    @Inject
    private UserManager userManager;

    @Inject
    private Messages messages;

    @Override
    public void authenticate() {
        UserIdentity userIdentity = new UserIdentity(
                credentials.getUsername(),
                ((PasswordCredential) credentials.getCredential()).getValue()
        );

        try {
            User user =  userManager.getUserByUserIdentity(userIdentity);
            setStatus(AuthenticationStatus.SUCCESS);
            setUser(user.getUserIdentity());
        } catch (InvalidUserIdentityException e) {
            messages.error("Authentication failed. Incorrect username or password.");
            setStatus(AuthenticationStatus.FAILURE);
        }
    }
}
