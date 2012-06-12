package cz.muni.fi.pv243.tps.security;

import org.hibernate.validator.constraints.Length;
import org.picketlink.idm.api.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Embeddable
public class UserIdentity implements User, Serializable {

    @NotNull
    @Length(min = 4, max = 20)
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Length(min = 6, max = 30)
    @Column(nullable = false)
    private String password;

    @Enumerated
    private Role role;

    public UserIdentity() {
    }

    public UserIdentity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getId() {
        return this.username;
    }

    @Override
    public String getKey() {
        return getId();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserIdentity that = (UserIdentity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
