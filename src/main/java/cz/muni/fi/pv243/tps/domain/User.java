package cz.muni.fi.pv243.tps.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
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
    private Credentials credentials;

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

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
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

    // User credentials
    @Embeddable
    public static class Credentials implements org.picketlink.idm.api.User {
        @Length(min = 4, max = 20)
        @Column(nullable = false, unique = true)
        private String username;

        @Length(min = 6, max = 30)
        private String password;

        public Credentials() {
        }

        public Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public String getId() {
            return getUsername();
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Credentials that = (Credentials) o;

            if (password != null ? !password.equals(that.password) : that.password != null) return false;
            if (username != null ? !username.equals(that.username) : that.username != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = username != null ? username.hashCode() : 0;
            result = 31 * result + (password != null ? password.hashCode() : 0);
            return result;
        }
    }

    // Inner class for Name representation
    @Embeddable
    public static class Name {
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