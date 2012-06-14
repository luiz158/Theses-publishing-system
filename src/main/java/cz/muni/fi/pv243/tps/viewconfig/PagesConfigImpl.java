package cz.muni.fi.pv243.tps.viewconfig;

import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.view.config.ViewPattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@RequestScoped
@Named("pagesConfig")
public class PagesConfigImpl implements PagesConfig, Serializable {
    @Override
    public String getViewId(PagesDefinition pd, Object... params) {
        Annotation viewPatternRaw = null;
        Annotation urlMappingRaw = null;
        try {
            viewPatternRaw = pd.getClass().getField(((Enum) pd).name()).getAnnotation(ViewPattern.class);
            urlMappingRaw = pd.getClass().getField(((Enum) pd).name()).getAnnotation(UrlMapping.class);
        } catch (NoSuchFieldException e) {
            // Won't ever happen
        }

        if (viewPatternRaw != null) {
            ViewPattern viewPattern = (ViewPattern) viewPatternRaw;
            StringBuilder viewId = new StringBuilder(viewPattern.value()).append("?faces-redirect=true");

            if (urlMappingRaw != null) {
                UrlMapping urlMapping = (UrlMapping) urlMappingRaw;
                String pattern = urlMapping.pattern();

                for (Object o : params) {
                    // Get name of parameter inside #{}
                    String param = pattern.replaceFirst(".*#\\{(..*)\\}.*", "$1");
                    // Remove it from urlMapping pattern
                    pattern = pattern.replaceFirst("#\\{..*\\}", "");
                    // If a param was found
                    if (param != null) {
                        // Append "&param=X" to viewId
                        viewId.append("&").append(param).append("=").append(o.toString());
                    }
                }
            }

            return viewId.toString() ;
        } else {
            return PagesConfig.DEFAULT_PAGE + "?faces-redirect=true";
        }
    }

    @Override
    public String getCurrentViewId() {
        return "pretty:";
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getViewId(String pd) {
        // If the definition is absolute, remove PagesConfig.
        String pageDefinition = pd.replaceFirst("^PagesConfig\\.", "");
        // Split pages definison to two pieces (e.g. "Users.NEW" to {"Users", "NEW"})
        String[] splitDefinition = pageDefinition.split("\\.", 2);
        // Get enums in PagesConfig
        Class<?>[] enums = PagesConfig.class.getDeclaredClasses();

        Class<?> matchingEnum = null;
        // Iterate through all enums to find the matching enum
        for (Class<?> e : enums) {
            if (e.getName().equals(PagesConfig.class.getName() + "$" + splitDefinition[0])) {
                matchingEnum = e;
            }
        }

        // If no such enum exists, return the default page viewId
        if (matchingEnum == null) {
            return PagesConfig.DEFAULT_PAGE;
        }

        // Otherwise get declared fields (pages definitions)
        Field[] fields = matchingEnum.getDeclaredFields();

        Field matchingField = null;
        // Iterate through all fields to find the matching field
        for (Field f : fields) {
            if (f.getName().equals(splitDefinition[1])) {
                matchingField = f;
            }
        }

        // If no such field is find, return the default page viewId
        if (matchingField == null) {
            return PagesConfig.DEFAULT_PAGE;
        }

        // Otherwise get the page definition as a PagesDefinition class
        PagesDefinition concretePagesDefinition;
        try {
            concretePagesDefinition = (PagesDefinition) Enum.valueOf((Class<Enum>) matchingField.getType(), matchingField.getName());
        } catch (ClassCastException e) {
            // Way too many casts, better safe than sorry
            return PagesConfig.DEFAULT_PAGE;
        }

        return getViewId(concretePagesDefinition);
    }
}
