package cz.muni.fi.pv243.tps.action.applications;

import com.ocpsoft.pretty.PrettyContext;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.ejb.UserManager;
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
@RequestScoped
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
        applicationManager.apply(new User(1L), topic);
        messages.info("You've successfully applied to this topic");
        return "pretty:";
    }
}
