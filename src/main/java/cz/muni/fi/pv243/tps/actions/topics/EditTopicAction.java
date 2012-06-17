package cz.muni.fi.pv243.tps.actions.topics;

import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.ejb.TopicManager;
import cz.muni.fi.pv243.tps.security.annotations.IsSupervisorOf;
import cz.muni.fi.pv243.tps.viewconfig.PagesConfig;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.international.status.Messages;

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
    private transient TopicManager topicManager;

    @Inject
    private Messages messages;

    @Inject
    private PagesConfig pagesConfig;

    private ThesisTopic editedTopic;

    @Begin
    public void setTopicById(String id) {
        editedTopic = topicManager.getTopic(Long.parseLong(id));
    }

    @End
    @IsSupervisorOf
    public String editTopic() {
        topicManager.editTopic(editedTopic);
        messages.info("Topic has been successfully edited");
        return pagesConfig.getViewId(PagesConfig.Topics.TOPICS);
    }

    @Produces
    @Named
    public ThesisTopic getEditedTopic() {
        return editedTopic;
    }
}
