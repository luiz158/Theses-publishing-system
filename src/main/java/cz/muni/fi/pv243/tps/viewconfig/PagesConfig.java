package cz.muni.fi.pv243.tps.viewconfig;

import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

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
        ACCESS_DENIED
    }
}
