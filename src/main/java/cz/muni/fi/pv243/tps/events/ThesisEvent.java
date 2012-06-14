package cz.muni.fi.pv243.tps.events;

import cz.muni.fi.pv243.tps.domain.Thesis;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class ThesisEvent {

    private Thesis thesis;

    public ThesisEvent(Thesis thesis) {
        this.thesis = thesis;
    }

    public Thesis getThesis() {
        return thesis;
    }

    public void setThesis(Thesis thesis) {
        this.thesis = thesis;
    }
}
