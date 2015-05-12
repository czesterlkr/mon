package mon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by kalipiec on 2015-03-26.
 */
@Embeddable
@Setter
@Getter
public class Employee {

    @Column(name = "face")
    private boolean face = false;
}
