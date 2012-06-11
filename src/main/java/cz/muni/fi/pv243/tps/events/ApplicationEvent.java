package cz.muni.fi.pv243.tps.events;

import cz.muni.fi.pv243.tps.domain.Application;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
public class ApplicationEvent {
    private Application application;


    public ApplicationEvent(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

}
