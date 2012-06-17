package cz.muni.fi.pv243.tps.action.applications;

import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.events.qualifiers.Acceptation;
import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;
import cz.muni.fi.pv243.tps.exceptions.ApplicationInProgressException;
import cz.muni.fi.pv243.tps.security.IsSupervisorOf;
import cz.muni.fi.pv243.tps.security.Supervisor;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Identity;

import javax.enterprise.event.Event;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@ViewScoped
public class ProcessApplicationAction  implements Serializable {
    @Inject
    private transient ApplicationManager applicationManager;

    @Inject
    private PagesConfig pagesConfig;

    @Inject
    private UserManager userManager;

    @Inject
    private Identity identity;

    @Inject
    private Messages messages;

    @IsSupervisorOf
    public String accept(Application application){
        applicationManager.acceptApplication(application);
        messages.info("Application has been accepted");
        return pagesConfig.getViewId(PagesConfig.Pages.CURRENT_PAGE);
    }

    @IsSupervisorOf
    public String decline(Application application){
        applicationManager.declineApplication(application);
        messages.info("Application has been declined");
        return pagesConfig.getViewId(PagesConfig.Pages.CURRENT_PAGE);
    }

    public String cancelApplication(Application application){
        try {
            applicationManager.cancelApplication(application);
            messages.info("Application was canceled successfully");
        } catch (ApplicationInProgressException e){
            messages.info("This application can't be canceled");
        }

        return pagesConfig.getViewId(PagesConfig.Pages.CURRENT_PAGE);
    }

    public boolean canCancel(Application application){
        if (application == null){
            return false;
        }
        User user = userManager.getUserByUserIdentity((UserIdentity) identity.getUser());
        return applicationManager.isWaitingApplication(application)
                && application.getApplicant().equals(user);
    }
}
