package cz.muni.fi.pv243.tps.logging.observers;

import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Update;
import cz.muni.fi.pv243.tps.events.UserEvent;
import cz.muni.fi.pv243.tps.logging.UserLogger;

import javax.enterprise.event.Observes;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
public class UserLoggingObserver {
    public void userCreatedLog(@Observes @Create UserEvent event, UserLogger logger) {
        logger.userCreated(event.getUser().getId());
    }

    public void userEditedLog(@Observes @Update UserEvent event, UserLogger logger) {
        logger.userEdited(event.getUser().getId());
    }
}
