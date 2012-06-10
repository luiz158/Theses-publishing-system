package cz.muni.fi.pv243.tps.events;

import cz.muni.fi.pv243.tps.domain.User;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
public abstract class UserEvent {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
