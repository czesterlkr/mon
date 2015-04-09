package mon.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mon.domain.util.CustomLocalDateSerializer;
import mon.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Holiday.
 */
@Entity
@Table(name = "T_HOLIDAY")
public class Holiday implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private KindOfHoliday holiday_kindOfHoliday;

    @ManyToOne
    private StatusOfHoliday holiday_statusOfHoliday;

    @ManyToOne
    private User holiday_user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public KindOfHoliday getHoliday_kindOfHoliday() {
        return holiday_kindOfHoliday;
    }

    public void setHoliday_kindOfHoliday(KindOfHoliday kindOfHoliday) {
        this.holiday_kindOfHoliday = kindOfHoliday;
    }

    public StatusOfHoliday getHoliday_statusOfHoliday() {
        return holiday_statusOfHoliday;
    }

    public void setHoliday_statusOfHoliday(StatusOfHoliday statusOfHoliday) {
        this.holiday_statusOfHoliday = statusOfHoliday;
    }

    public User getHoliday_user() {
        return holiday_user;
    }

    public void setHoliday_user(User user) {
        this.holiday_user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Holiday holiday = (Holiday) o;

        if ( ! Objects.equals(id, holiday.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", fromDate='" + fromDate + "'" +
                ", toDate='" + toDate + "'" +
                ", requestDate='" + requestDate + "'" +
                ", comment='" + comment + "'" +
                '}';
    }
}
