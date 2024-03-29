package cz.muni.fi.pv243.tps.actions.topics;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
public class TopicListAction  implements Serializable{
    @Inject
    private transient TopicManager topicManager;

    @Produces
    @Model
    List<ThesisTopic> getTopics(){
        return topicManager.getTopics();
    }
}
