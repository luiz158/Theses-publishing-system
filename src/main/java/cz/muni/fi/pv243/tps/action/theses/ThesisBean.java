package cz.muni.fi.pv243.tps.action.theses;

import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.ejb.ThesisManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */

// TO-DO: this should be implemented
@Named
public class ThesisBean {
    @Inject
    ThesisManager thesisManager;

    @Produces
    @Model
    public List<Thesis> getTheses(){
        return thesisManager.getTheses();
    }
}
