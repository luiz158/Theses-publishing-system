package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.Events.ApplicationAcceptedEvent;
import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;

import javax.enterprise.event.Event;
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
    Event<ApplicationAcceptedEvent> event;

    public String accept(Application application){
        applicationManager.acceptApplication(application);
        event.fire(new ApplicationAcceptedEvent(application));
        return "pretty:";
    }

    public String decline(Application application){
        applicationManager.declineApplication(application);
        return "pretty:";
    }
}
