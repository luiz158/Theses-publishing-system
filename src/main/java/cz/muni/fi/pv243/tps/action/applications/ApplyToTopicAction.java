package cz.muni.fi.pv243.tps.action.applications;

import com.ocpsoft.pretty.PrettyContext;
import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.exceptions.ApplicationInProgressException;
import cz.muni.fi.pv243.tps.exceptions.InvalidApplicationAttemptException;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.LoggedIn;
import org.jboss.solder.exception.control.ExceptionHandled;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@ViewScoped
public class ApplyToTopicAction implements Serializable {
    @Inject
    private transient ApplicationManager applicationManager;

    @Inject
    private Messages messages;

    @Inject
    private Identity identity;

    @Inject
    private PagesConfig pagesConfig;

    @Inject
    private UserManager userManager;

    @LoggedIn
    public String apply(ThesisTopic topic){
        User user = userManager.getUserByUserIdentity((UserIdentity) identity.getUser());
        try{
            applicationManager.apply(user, topic);
            messages.info("You've successfully applied for this topic");
        } catch (InvalidApplicationAttemptException e){
            messages.error("You can't apply for this topic");
        } catch (ApplicationInProgressException e){
            messages.error("You've already applied for this topic.");
        }

        return pagesConfig.getViewId(PagesConfig.Pages.CURRENT_PAGE);
    }

    public boolean canApply(ThesisTopic topic){
        User user = userManager.getUserByUserIdentity((UserIdentity) identity.getUser());
        return applicationManager.canApply(topic)
                && applicationManager.hasNoWaitingApplications(user,topic)
                && applicationManager.hasNotApplied(user, topic);
    }
}
