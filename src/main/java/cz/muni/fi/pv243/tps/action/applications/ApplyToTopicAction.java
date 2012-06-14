package cz.muni.fi.pv243.tps.action.applications;

import com.ocpsoft.pretty.PrettyContext;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
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

    public String apply(String topicId){
        applicationManager.apply(1L, Long.parseLong(topicId));
        return "pretty:";
    }
}
