package cz.muni.fi.pv243.tps.events.observers;

import cz.muni.fi.pv243.tps.events.qualifiers.Acceptation;
import cz.muni.fi.pv243.tps.ejb.ThesisManager;
import cz.muni.fi.pv243.tps.events.ApplicationEvent;

import javax.enterprise.event.Observes;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
public class ApplicationObserver {

    public void applicationAccepted(@Observes @Acceptation ApplicationEvent event,
                                    ThesisManager thesisManager){
        thesisManager.createThesisFromApplication(event.getApplication());
    }
}
