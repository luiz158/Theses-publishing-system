package cz.muni.fi.pv243.tps.logging;

import cz.muni.fi.pv243.tps.domain.Application;
import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@MessageLogger
public interface ApplicationLogger {

    @Log @Message("Student %d applied to thesis topic %d")
    void applicationSent(Long studentId, Long topicId);

    @Log @Message("Status of application %d changed to %s by user %d")
    void applicationStatusChanged(Long applicationId, Application.Status status, Long userId);

}
