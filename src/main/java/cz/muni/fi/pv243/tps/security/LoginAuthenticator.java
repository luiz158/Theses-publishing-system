package cz.muni.fi.pv243.tps.security;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.exceptions.InvalidCredentialsException;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;

import javax.inject.Inject;
import javax.persistence.NoResultException;

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
        User.Credentials userCredentials = new User.Credentials();
        userCredentials.setUsername(credentials.getUsername());
        userCredentials.setPassword(((PasswordCredential) credentials.getCredential()).getValue());
        try {
            User user = userManager.getUserByCredentials(userCredentials);

            setStatus(AuthenticationStatus.SUCCESS);
            setUser(userCredentials);
        } catch (InvalidCredentialsException e) {
            setStatus(AuthenticationStatus.FAILURE);
        }
    }
}
