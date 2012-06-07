package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Model
public class NewTopicAction {
    @Inject
    private TopicManager topicManager;

    private ThesisTopic newTopic;

    public void createTopic(){
        topicManager.createTopic(newTopic);
    }

    @PostConstruct
    public void init(){
        newTopic = new ThesisTopic();
    }

    @Produces
    @Named
    public ThesisTopic getNewTopic(){
        return newTopic;
    }
}
