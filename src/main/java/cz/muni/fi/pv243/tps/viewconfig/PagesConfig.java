package cz.muni.fi.pv243.tps.viewconfig;

import cz.muni.fi.pv243.tps.security.Admin;
import cz.muni.fi.pv243.tps.security.IsCurrentUser;
import cz.muni.fi.pv243.tps.security.IsSupervisorOf;
import cz.muni.fi.pv243.tps.security.Supervisor;
import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import org.jboss.seam.security.annotations.LoggedIn;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@ViewConfig
public interface PagesConfig {

    public static final String PAGES_PREFIX = "/WEB-INF/pages/";
    public static final String DEFAULT_PAGE = PAGES_PREFIX + "index.xhtml";

    public String getViewId(PagesDefinition pd, Object... params);
    public String getViewId(String pd);

    public interface PagesDefinition {}

    public static enum Pages implements PagesDefinition {

        @ViewPattern("/*")
        @AccessDeniedView(PAGES_PREFIX + "denied.xhtml")
        @LoginView(PAGES_PREFIX + "login.xhtml")
        ALL,

        @CurrentPage
        CURRENT_PAGE,

        @ViewPattern(PAGES_PREFIX + "index.xhtml")
        @UrlMapping(pattern = "/")
        INDEX,

        @ViewPattern(PAGES_PREFIX + "not_found.xhtml")
        @UrlMapping(pattern = "/404")
        NOT_FOUND,

        @ViewPattern(PAGES_PREFIX + "denied.xhtml")
        @UrlMapping(pattern = "/403")
        ACCESS_DENIED,

        @ViewPattern(PAGES_PREFIX + "error.xhtml")
        @UrlMapping(pattern = "/500")
        INTERNAL_ERROR,

        @FacesRedirect
        @ViewPattern(PAGES_PREFIX + "login.xhtml")
        @UrlMapping(pattern = "/login")
        LOG_IN,

        @ViewPattern(PAGES_PREFIX + "logout.xhtml")
        @UrlMapping(pattern = "/logout")
        LOG_OUT,
    }

    public static enum Topics implements PagesDefinition {
        @ViewPattern(PAGES_PREFIX + "topics/topics.xhtml")
        @UrlMapping(pattern = "/topics")
        TOPICS,

        @ViewPattern(PAGES_PREFIX + "/topics/show.xhtml")
        @UrlMapping(pattern = "/topic/#{id}")
        SHOW,

        @Supervisor
        @LoggedIn
        @ViewPattern(PAGES_PREFIX + "topics/new.xhtml")
        @UrlMapping(pattern = "/topic/new")
        NEW,

        @IsSupervisorOf
        @LoggedIn
        @ViewPattern(PAGES_PREFIX + "topics/edit.xhtml")
        @UrlMapping(pattern = "/topic/#{id}/edit")
        EDIT,
    }

    public static enum Users implements PagesDefinition {
        @ViewPattern(PAGES_PREFIX + "users/users.xhtml")
        @UrlMapping(pattern = "/users")
        USERS,

        @ViewPattern(PAGES_PREFIX + "users/show.xhtml")
        @UrlMapping(pattern = "/user/#{id}")
        SHOW,

        @LoggedIn
        @ViewPattern(PAGES_PREFIX + "users/my_profile.xhtml")
        @UrlMapping(pattern = "/my-profile")
        MY_PROFILE,

        @ViewPattern(PAGES_PREFIX + "users/signup.xhtml")
        @UrlMapping(pattern = "/user/signup")
        SIGN_UP,

        @Admin
        @LoggedIn
        @ViewPattern(PAGES_PREFIX + "users/edit.xhtml")
        @UrlMapping(pattern = "/user/#{id}/edit")
        EDIT,

        @LoggedIn
        @IsCurrentUser
        @ViewPattern(PAGES_PREFIX + "users/edit_password.xhtml")
        @UrlMapping(pattern = "/user/#{id}/edit/password")
        EDIT_PASSWORD,

        @LoggedIn
        @IsCurrentUser
        @ViewPattern(PAGES_PREFIX + "users/edit_email.xhtml")
        @UrlMapping(pattern = "/user/#{id}/edit/email")
        EDIT_EMAIL,
    }
}
