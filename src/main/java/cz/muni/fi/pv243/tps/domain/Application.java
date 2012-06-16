package cz.muni.fi.pv243.tps.domain;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.*;

/**
 * @author <a href="mailto:vaclav.dedik@gmail.com">Vaclav Dedik</a>
 */
@Entity
@SequenceGenerator(name = "application_sequence")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "application_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User applicant;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ThesisTopic topic;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Application() {
    }

    public Application(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
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

        Application that = (Application) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void declineApplication(Application application) {
    }

    public static enum Status{
        ACCEPTED,
        DECLINED,
        WAITING,
    }
}
