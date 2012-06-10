package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

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

    private ThesisTopic newTopic;

    @Begin
    public void setNewTopic(){
        newTopic = new ThesisTopic();
    }

    @End
    public String createTopic(){
        topicManager.createTopic(newTopic);
        return "topics?faces-redirect=true";
    }

    @Produces
    @Named
    public ThesisTopic newTopic(){
        return newTopic;
    }
}
