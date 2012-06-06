package cz.muni.fi.pv243.tps.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

import static javax.persistence.GenerationType.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Entity
@SequenceGenerator(name = "thesis_topic_sequence")
public class ThesisTopic  implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "thesis_topic_sequence")
    private Long id;

    @ManyToOne
    private User supervisor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
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