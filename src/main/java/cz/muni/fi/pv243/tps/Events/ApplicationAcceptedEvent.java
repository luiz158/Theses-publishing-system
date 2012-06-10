package cz.muni.fi.pv243.tps.Events;

import cz.muni.fi.pv243.tps.domain.Application;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
public class ApplicationAcceptedEvent {
    private Application application;


    public ApplicationAcceptedEvent(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
