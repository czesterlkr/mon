package mon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * Created by kalipiec on 2015-03-26.
 */
@Embeddable
@Getter
@Setter
public class Employee {

    @Column(name = "face")
    private boolean face = false;

    @Column(name = "salary")
    private BigDecimal salary;

    @ManyToOne
    private Project project;


}
