package cz.muni.fi.pv243.tps.validation;

import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.exceptions.InvalidUserIdentityException;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import org.jboss.seam.faces.validation.InputField;
import org.jboss.seam.security.Identity;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@RequestScoped
@FacesValidator("correctOldPasswordValidator")
public class CorrectOldPasswordValidator implements Validator {
    @Inject
    @InputField
    private String oldPassword;

    @Inject
    private Identity identity;

    @Inject
    private UserManager userManager;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UserIdentity userIdentity = (UserIdentity) identity.getUser();
        userIdentity.setPassword(oldPassword);

        try {
            userManager.getUserByUserIdentity(userIdentity);
        } catch (InvalidUserIdentityException e) {
            throw new ValidatorException(new FacesMessage("Your old password is incorrect."));
        }
    }
}
