package cz.muni.fi.pv243.tps.validation;

import org.jboss.seam.faces.validation.InputField;

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
@FacesValidator("passwordMatchValidator")
public class PasswordMatchValidator implements Validator {

    @Inject
    @InputField
    private String password;

    @Inject
    @InputField
    private String passwordRepeat;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!password.equals(passwordRepeat)) {
            throw new ValidatorException(new FacesMessage("Passwords don't match."));
        }
    }
}
