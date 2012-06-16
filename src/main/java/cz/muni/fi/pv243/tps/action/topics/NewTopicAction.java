package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;
import cz.muni.fi.pv243.tps.security.IsSupervisorOf;
import cz.muni.fi.pv243.tps.security.Supervisor;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@ConversationScoped
public class NewTopicAction implements Serializable{

    @Inject
    private transient TopicManager topicManager;

    @Inject
    private Messages messages;

    @Inject
    private PagesConfig pagesConfig;

    private ThesisTopic newTopic;

    @Begin
    public void setNewTopic(){
        newTopic = new ThesisTopic();
        newTopic.setCapacity(1);
    }

    @End
    @Supervisor
    public String createTopic(){
        topicManager.createTopic(newTopic);
        messages.info("Topic has been successfully created");
        return pagesConfig.getViewId(PagesConfig.Topics.TOPICS);
    }

    @Produces
    @Named
    public ThesisTopic getNewTopic(){
        return newTopic;
    }
}
