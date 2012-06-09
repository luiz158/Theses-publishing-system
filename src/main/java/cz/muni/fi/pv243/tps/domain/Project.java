package cz.muni.fi.pv243.tps.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Entity
@SequenceGenerator(name = "project_sequence")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "project_sequence")
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private User supervisor;

    @OneToMany
    private Collection<Assignment> assignments;

    @ManyToOne
    private ThesisTopic thesisTopic;

    public Project() {
    }

    public Project(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Collection<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Collection<Assignment> assignments) {
        this.assignments = assignments;
    }

    public ThesisTopic getThesisTopic() {
        return thesisTopic;
    }

    public void setThesisTopic(ThesisTopic thesisTopic) {
        this.thesisTopic = thesisTopic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
