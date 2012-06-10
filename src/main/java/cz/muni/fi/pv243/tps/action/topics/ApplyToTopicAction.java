package cz.muni.fi.pv243.tps.action.topics;

import com.ocpsoft.pretty.PrettyContext;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.ejb.TopicManager;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
@ViewScoped
public class ApplyToTopicAction implements Serializable {
    @Inject
    private transient ApplicationManager applicationManager;

    public String apply(String topicId){
        applicationManager.apply(1L, Long.parseLong(topicId));
        return "pretty:";
    }


}
