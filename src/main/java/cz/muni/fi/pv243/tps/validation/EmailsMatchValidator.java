package cz.muni.fi.pv243.tps.validation;

import org.jboss.seam.faces.validation.InputField;

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
@FacesValidator("emailsMatchValidator")
public class EmailsMatchValidator implements Validator {

    @Inject
    @InputField
    private String email;

    @Inject
    @InputField
    private String emailRepeat;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (!email.equals(emailRepeat)) {
            throw new ValidatorException(new FacesMessage("Emails don't match."));
        }
    }
}
