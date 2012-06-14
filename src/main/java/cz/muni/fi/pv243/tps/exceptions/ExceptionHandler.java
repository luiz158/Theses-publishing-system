package cz.muni.fi.pv243.tps.exceptions;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.application.PrettyNavigationHandler;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
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

    @Inject
    PagesConfig pagesConfig;

    public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> e,
                                             FacesContext facesContext){
        e.handled();
        messages.error("You don't have permission to access this page");
        redirect(PagesConfig.Pages.ACCESS_DENIED);
    }

    public void handleInvalidEntityId(@Handles CaughtException<InvalidEntityIdException> e){
        e.handled();
        redirect(PagesConfig.Pages.NOT_FOUND);
    }

    public void handleInvalidApplicationAttempt (@Handles
                                                 CaughtException<InvalidApplicationAttemptException> e){
        e.handled();
        messages.error("You've already applied to this topic");
        redirect(PagesConfig.Pages.CURRENT_PAGE);
    }

    public void handleException(@Handles CaughtException<Throwable> e){
        e.handled();
        messages.error("Some unexpected error has occurred.");
        redirect(PagesConfig.Pages.ACCESS_DENIED);
    }

    private void redirect(PagesConfig.PagesDefinition page){
        facesContext.getApplication()
                .getNavigationHandler()
                .handleNavigation(facesContext, null, pagesConfig.getViewId(page));
    }
}