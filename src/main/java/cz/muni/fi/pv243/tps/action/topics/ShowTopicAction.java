package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@RequestScoped
public class ShowTopicAction {
    @Inject
    private TopicManager topicManager;

    private ThesisTopic topic;

    public void  setTopicById(String id){
        topic = topicManager.getTopic(Long.parseLong(id));
    }

    @Produces
    @Named
    public ThesisTopic getTopic(){
        return topic;
    }
}
