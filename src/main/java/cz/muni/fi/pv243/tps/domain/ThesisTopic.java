package cz.muni.fi.pv243.tps.domain;

import javax.persistence.*;

import java.util.Collection;

import static javax.persistence.GenerationType.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Entity
@SequenceGenerator(name = "thesis_topic_sequence")
public class ThesisTopic {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "thesis_topic_sequence")
    private Long id;

    @OneToMany
    private Collection<Application> applications;

    @ManyToOne
    private User supervisor;

    @OneToMany
    private Collection<Thesis> theses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Application> getApplications() {
        return applications;
    }

    public void setApplications(Collection<Application> applications) {
        this.applications = applications;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Collection<Thesis> getTheses() {
        return theses;
    }

    public void setTheses(Collection<Thesis> theses) {
        this.theses = theses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThesisTopic that = (ThesisTopic) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
