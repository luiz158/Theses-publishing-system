package cz.muni.fi.pv243.tps.observers;

import cz.muni.fi.pv243.tps.events.UserEditedEvent;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@ApplicationScoped
public class UserEditedObserver {

    public void userEditedMessage(@Observes UserEditedEvent event, Messages messages) {
        messages.info("User with id {0} successfully updated.", event.getUser().getId());
    }

    public void userEditedLog(@Observes UserEditedEvent event, Logger logger) {
        logger.info("User with id " + event.getUser().getId() + " updated.");
    }
}
