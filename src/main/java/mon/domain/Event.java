package mon.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mon.domain.util.CustomDateTimeDeserializer;
import mon.domain.util.CustomLocalDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by kalipiec on 2015-04-14.
 */
@Entity
@Table(name = "T_EVENT")
@Getter
@Setter
@ToString
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_at", nullable = true)
    private LocalDateTime endAt;

    @ManyToOne
    @NotNull
    private User user;

    public static Event fromNow() {
        Event event = new Event();
        event.setStartAt(LocalDateTime.now());
        return event;
    }

    public static Event fromNow(User user) {
        Event event = new Event();
        event.setStartAt(LocalDateTime.now());
        event.setUser(user);
        return event;
    }

    public void end() {
        this.setEndAt(LocalDateTime.now());
    }
}
