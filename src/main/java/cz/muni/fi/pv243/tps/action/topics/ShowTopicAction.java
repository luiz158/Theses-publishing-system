package cz.muni.fi.pv243.tps.action.topics;

import cz.muni.fi.pv243.tps.action.Current;
import cz.muni.fi.pv243.tps.domain.Application;
import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.domain.ThesisTopic;
import cz.muni.fi.pv243.tps.domain.User;
import cz.muni.fi.pv243.tps.ejb.ApplicationManager;
import cz.muni.fi.pv243.tps.ejb.ThesisManager;
import cz.muni.fi.pv243.tps.ejb.TopicManager;
import cz.muni.fi.pv243.tps.ejb.UserManager;
import cz.muni.fi.pv243.tps.security.UserIdentity;
import org.jboss.seam.security.Identity;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Inject
    private transient ThesisManager thesisManager;

    @Inject
    private transient UserManager userManager;

    @Inject
    private Identity identity;

    private ThesisTopic topic;

    private Application application;

    public void  setTopicById(String id){
        topic = topicManager.getTopic(Long.parseLong(id));
    }

    @Produces
    @Named
    public List<Application> getWaitingTopicApplications(){
        return  applicationManager.getApplicationsByTopic(topic, Application.Status.WAITING);
    }

    @Produces
    @Named
    public List<Thesis> getTopicTheses(){
        return thesisManager.getThesesByTopicId(topic.getId());
    }

    @Produces
    @Named
    public Application getWaitingApplication(){
        if (application == null){
            application = applicationManager.getWaitingApplication(topic, (UserIdentity) identity.getUser());
        }
        return application;
    }

    @Produces
    @Named
    public ThesisTopic getTopic(){
        return topic;
    }
}
