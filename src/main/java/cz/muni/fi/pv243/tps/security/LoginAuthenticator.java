package cz.muni.fi.pv243.tps.security;

import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.exceptions.InvalidUserIdentityException;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import javax.inject.Inject;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class LoginAuthenticator extends BaseAuthenticator {

    @Inject
    private Credentials credentials;

    @Inject
    private UserManager userManager;

    @Override
    public void authenticate() {
        UserIdentity userIdentity = new UserIdentity(
                credentials.getUsername(),
                ((PasswordCredential) credentials.getCredential()).getValue()
        );

        try {
            userManager.getUserByUserIdentity(userIdentity);

            setStatus(AuthenticationStatus.SUCCESS);
            setUser(userIdentity);
        } catch (InvalidUserIdentityException e) {
            setStatus(AuthenticationStatus.FAILURE);
        }
    }
}
