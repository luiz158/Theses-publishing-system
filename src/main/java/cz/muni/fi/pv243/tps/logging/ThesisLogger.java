package cz.muni.fi.pv243.tps.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@MessageLogger
public interface ThesisLogger {

    @Log @Message("Thesis with id %d created.")
    void thesisCreated(Long thesisId);
}
