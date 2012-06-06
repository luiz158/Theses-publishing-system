package cz.muni.fi.pv243.tps.action;

import cz.muni.fi.pv243.tps.domain.Thesis;
import cz.muni.fi.pv243.tps.ejb.ThesesManager;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author <a href="mailto:pseudo.em@gmail.com">Jakub Cechacek</a>.
 */
@Named
public class ThesesBean {
    @Inject
    ThesesManager thesesManager;

    @Produces
    @Model
    List<Thesis> getTheses(){
        return thesesManager.getTheses();
    }

}
