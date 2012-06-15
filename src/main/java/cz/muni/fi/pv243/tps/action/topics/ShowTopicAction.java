package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.ejb.TopicManager;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@ViewScoped
public class ShowTopicAction implements Serializable {
    @Inject
    private transient TopicManager topicManager;

    @Inject
    private transient ApplicationManager applicationManager;

    private ThesisTopic topic;

    public void  setTopicById(String id){
        topic = topicManager.getTopic(Long.parseLong(id));
    }

    @Produces
    @Named
    public List<Application> getTopicApplications(){
        return  applicationManager.getApplicationsByTopic(topic);
    }

    @Produces
    @Named
    public ThesisTopic getTopic(){
        return topic;
    }
}
