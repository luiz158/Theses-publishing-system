package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@ConversationScoped
public class EditTopicAction implements Serializable {

    @Inject
    private TopicManager topicManager;

    private ThesisTopic editedTopic;

    @Begin
    public void setTopicById(String id){
        editedTopic = topicManager.getTopic(Long.parseLong(id));
    }

    @End
    public String editTopic(){
        topicManager.editTopic(editedTopic);
        return "topics?faces-redirect=true";
    }

    @Produces
    @Named
    public ThesisTopic getEditedTopic(){
        return editedTopic;
    }
}