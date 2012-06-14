package cz.muni.fi.pv243.tps.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@MessageLogger
public interface UserLogger {

    @Log @Message("User with id %d created.")
    void userCreated(Long userId);

    @Log @Message("User with id %d updated.")
    void userEdited(Long userId);
}
