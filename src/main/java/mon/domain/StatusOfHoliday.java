package mon.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StatusOfHoliday.
 */
@Entity
@Table(name = "T_STATUSOFHOLIDAY")
public class StatusOfHoliday implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatusOfHoliday statusOfHoliday = (StatusOfHoliday) o;

        if ( ! Objects.equals(id, statusOfHoliday.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StatusOfHoliday{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
