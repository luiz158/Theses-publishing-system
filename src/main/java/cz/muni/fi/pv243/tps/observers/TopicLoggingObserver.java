package cz.muni.fi.pv243.tps.observers;

import cz.muni.fi.pv243.tps.events.TopicEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.events.qualifiers.Update;
import cz.muni.fi.pv243.tps.logging.TopicLogger;

import javax.enterprise.event.Observes;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class TopicLoggingObserver {

    public void topicEditedLog(@Observes @Update TopicEvent event, TopicLogger logger) {
        logger.topicEdited(event.getThesisTopic().getId());
    }

    public void topicCreatedLog(@Observes @Create TopicEvent event, TopicLogger logger) {
        logger.topicCreated(event.getThesisTopic().getId());
    }
}
