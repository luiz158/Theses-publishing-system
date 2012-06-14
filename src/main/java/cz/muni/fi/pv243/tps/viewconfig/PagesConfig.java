package cz.muni.fi.pv243.tps.viewconfig;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@ViewConfig
public interface PagesConfig {

    public static final String PAGES_PREFIX = "/WEB-INF/pages/";
    public static final String DEFAULT_PAGE = PAGES_PREFIX + "index.xhtml";

    public String getViewId(PagesDefinition pd, Object... params);
    public String getCurrentViewId();
    public String getViewId(String pd);

    public interface PagesDefinition {}

    public static enum Pages implements PagesDefinition {

        @ViewPattern("/*")
        @AccessDeniedView(PAGES_PREFIX + "denied.xhtml")
        @LoginView(PAGES_PREFIX + "login.xhtml")
        ALL,

        //@LoggedIn
        @ViewPattern(PAGES_PREFIX + "index.xhtml")
        @UrlMapping(pattern = "/")
        INDEX,

        @ViewPattern(PAGES_PREFIX + "not_found.xhtml")
        @UrlMapping(pattern = "/404")
        NOT_FOUND,

        @ViewPattern(PAGES_PREFIX + "denied.xhtml")
        @UrlMapping(pattern = "/403")
        ACCESS_DENIED,

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

        @ViewPattern(PAGES_PREFIX + "topics/new.xhtml")
        @UrlMapping(pattern = "/topic/new")
        NEW,

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

        @ViewPattern(PAGES_PREFIX + "users/new.xhtml")
        @UrlMapping(pattern = "/user/new")
        NEW,

        @ViewPattern(PAGES_PREFIX + "users/edit.xhtml")
        @UrlMapping(pattern = "/user/#{id}/edit")
        EDIT,
    }
}
