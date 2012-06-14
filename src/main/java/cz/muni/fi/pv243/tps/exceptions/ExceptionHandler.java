package cz.muni.fi.pv243.tps.exceptions;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.application.PrettyNavigationHandler;
import org.jboss.seam.faces.event.PreNavigateEvent;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.ExceptionHandled;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.solder.servlet.exception.handler.SendHttpError;

import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */

// FIX ME: We need to set a proper http status code for each exception. Some neater solution would be nice
@HandlesExceptions
public class ExceptionHandler {

    @Inject
    FacesContext facesContext;

    @Inject
    Messages messages;

    public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> e,
                                                  FacesContext facesContext){
        e.handled();
        messages.error("You don't have permission to access this page");
        redirect("pretty:/WEB-INF/pages/denied.xhtml");
    }

    public void handleInvalidEntityId(@Handles CaughtException<InvalidEntityIdException> e){
        e.handled();
        redirect("pretty:/WEB-INF/pages/not_found.xhtml");
    }

    public void handleInvalidApplicationAttempt (@Handles
                                                  CaughtException<InvalidApplicationAttemptException> e){
        e.handled();
        messages.error("You've already applied to this topic");
        redirect("pretty:");
    }

    public void handleException(@Handles CaughtException<Throwable> e){
        e.handled();
        messages.error("Some unexpected error has occurred.");
        redirect("pretty:/WEB-INF/pages/denied.xhtml");
    }

    private void redirect(String outcome){
        facesContext.getApplication()
                .getNavigationHandler()
                .handleNavigation(facesContext, null, outcome);
    }
}