package cz.muni.fi.pv243.tps.logging.observers;

import cz.muni.fi.pv243.tps.events.ThesisEvent;
import cz.muni.fi.pv243.tps.events.qualifiers.Create;
import cz.muni.fi.pv243.tps.logging.ThesisLogger;

import javax.enterprise.event.Observes;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class ThesisLoggingObserver {

    public void thesisCreatedLog(@Observes @Create ThesisEvent event, ThesisLogger logger) {
        logger.thesisCreated(event.getThesis().getId());
    }
}
