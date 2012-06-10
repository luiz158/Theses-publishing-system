package cz.muni.fi.pv243.tps.events;

import cz.muni.fi.pv243.tps.domain.User;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public class UserEditedEvent extends UserEvent {
    public UserEditedEvent() {
    }

    public UserEditedEvent(User user) {
        super.setUser(user);
    }
}
