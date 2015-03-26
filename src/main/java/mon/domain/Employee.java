package mon.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by kalipiec on 2015-03-26.
 */
@Embeddable
public class Employee {

    @Column(name = "face")
    private boolean face = false;
}
