package cz.muni.fi.pv243.tps.observers;

import cz.muni.fi.pv243.tps.events.UserCreatedEvent;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import javax.enterprise.event.Observes;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class UserCreatedObserver {

    public void userCreatedMessage(@Observes UserCreatedEvent event, Messages messages) {
        messages.info("Your account has been successfully created.");
    }

    public void userCreatedLog(@Observes UserCreatedEvent event, Logger logger) {
        logger.info("User with id " + event.getUser().getId() + " created.");
    }
}
