package cz.muni.fi.pv243.tps.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@MessageLogger
public interface TopicLogger {

    @Log @Message("Topic with id %d created.")
    void topicCreated(Long topicId);

    @Log @Message("Topic with id %d updated,")
    void topicEdited(Long topicId);
}
