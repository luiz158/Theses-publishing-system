package cz.muni.fi.pv243.tps.exceptions;

import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.AuthorizationException;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.solder.logging.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */

// FIX ME: We need to set a proper http status code for each exception. Some neater solution would be nice
@HandlesExceptions
public class ExceptionHandler {

    @Inject
    FacesContext facesContext;

    @Inject
    Logger logger;

    @Inject
    PagesConfig pagesConfig;

    public void handleAuthorizationException(@Handles CaughtException<AuthorizationException> e,
                                             FacesContext facesContext){
        e.handled();
        redirect(PagesConfig.Pages.ACCESS_DENIED);
    }

    public void handleInvalidEntityId(@Handles CaughtException<InvalidEntityIdException> e){
        e.handled();
        redirect(PagesConfig.Pages.NOT_FOUND);
    }

    public void handleInvalidApplicationAttempt (@Handles
                                                 CaughtException<InvalidApplicationAttemptException> e){
        e.handled();
        redirect(PagesConfig.Pages.CURRENT_PAGE);
    }

    public void handleException(@Handles CaughtException<Throwable> e){
//        logger.error(e);
//        e.handled();
        redirect(PagesConfig.Pages.INTERNAL_ERROR);
    }

    private void redirect(PagesConfig.PagesDefinition page){
        facesContext.getApplication()
                .getNavigationHandler()
                .handleNavigation(facesContext, null, pagesConfig.getViewId(page));
    }
}