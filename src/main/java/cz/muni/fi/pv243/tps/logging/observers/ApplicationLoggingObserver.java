package cz.muni.fi.pv243.tps.logging.observers;

import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.StatusChange;
import cz.muni.fi.pv243.tps.logging.ApplicationLogger;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
public class ApplicationLoggingObserver {

    public void applicationStatusChangeLog(@Observes @StatusChange ApplicationEvent event,
                                           ApplicationLogger logger){
        Application application = event.getApplication();
        logger.applicationStatusChanged(application.getId(), application.getStatus());
    }

    public void applicationCreatedLog(@Observes @Create ApplicationEvent event, ApplicationLogger logger){
        logger.applicationCreated(event.getApplication().getId());
    }
}
