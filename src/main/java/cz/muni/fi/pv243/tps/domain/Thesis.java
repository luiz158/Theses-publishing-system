package cz.muni.fi.pv243.tps.domain;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Entity
@SequenceGenerator(name = "thesis_sequence")
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"worker_id","topic_id"})
)
public class Thesis  implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "thesis_sequence")
    private Long id;

    @OneToOne
    private User worker;

    @ManyToOne
    private ThesisTopic topic;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Thesis() {
    }

    public Thesis(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public ThesisTopic getTopic() {
        return topic;
    }

    public void setTopic(ThesisTopic topic) {
        this.topic = topic;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thesis thesis = (Thesis) o;

        if (id != null ? !id.equals(thesis.id) : thesis.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static enum Status{
        IN_PROGRESS,
        POSTPONE,
        CANCELED,
        SUCCESSFUL,
        UNSUCCESSFUL,
    }
}
