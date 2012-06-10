package cz.muni.fi.pv243.tps.observers;

import cz.muni.fi.pv243.tps.Events.ApplicationAcceptedEvent;
import cz.muni.fi.pv243.tps.ejb.ThesisManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@ApplicationScoped
public class ApplicationObserver {

    @Inject
    ThesisManager thesisManager;

    public void createThesis(@Observes ApplicationAcceptedEvent event){
        thesisManager.createThesisFromApplication(event.getApplication());
        System.err.println("OBSERVED");
    }
}
