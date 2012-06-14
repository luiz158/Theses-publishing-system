package cz.muni.fi.pv243.tps.events;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class TopicEvent {

    private ThesisTopic thesisTopic;

    public TopicEvent(ThesisTopic thesisTopic) {
        this.thesisTopic = thesisTopic;
    }

    public ThesisTopic getThesisTopic() {
        return thesisTopic;
    }

    public void setThesisTopic(ThesisTopic thesisTopic) {
        this.thesisTopic = thesisTopic;
    }
}
