package cz.muni.fi.pv243.tps.action.applications;

import cz.muni.fi.pv243.tps.events.qualifiers.Acceptation;
import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;

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

    public String accept(Application application){
        applicationManager.acceptApplication(application);
        return "pretty:";
    }

    public String decline(Application application){
        applicationManager.declineApplication(application);
        return "pretty:";
    }
}
