package cz.muni.fi.pv243.tps.viewconfig;

import org.jboss.seam.faces.rewrite.UrlMapping;
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

    static enum Pages {
        @ViewPattern(PAGES_PREFIX + "index.xhtml")
        @UrlMapping(pattern = "/")
        INDEX,

        @ViewPattern(PAGES_PREFIX + "not_found.xhtml")
        @UrlMapping(pattern = "/404")
        NOT_FOUND,

        @ViewPattern(PAGES_PREFIX + "denied.xhtml")
        @UrlMapping(pattern = "/403")
        ACCESS_DENIED,

        @ViewPattern(PAGES_PREFIX + "login.xhtml")
        @UrlMapping(pattern = "/login")
        LOG_IN,
    }

    static enum Topics {
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

    static enum Users {
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
