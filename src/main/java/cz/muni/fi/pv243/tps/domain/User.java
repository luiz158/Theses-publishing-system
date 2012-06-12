package cz.muni.fi.pv243.tps.domain;

import cz.muni.fi.pv243.tps.security.UserIdentity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import static javax.persistence.GenerationType.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Entity
@SequenceGenerator(name = "user_sequence")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence")
    private Long id;

    @NotNull
    private UserIdentity userIdentity;

    @Email
    private String email;

    @NotNull
    private Name name;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserIdentity getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    // Inner class for Name representation
    @Embeddable
    public static class Name implements Serializable {
        @NotEmpty
        private String firstName;
        @NotEmpty
        private String lastName;

        public Name() {
        }

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Name name = (Name) o;

            if (firstName != null ? !firstName.equals(name.firstName) : name.firstName != null) return false;
            if (lastName != null ? !lastName.equals(name.lastName) : name.lastName != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = firstName != null ? firstName.hashCode() : 0;
            result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }

}