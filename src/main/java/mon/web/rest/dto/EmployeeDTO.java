package mon.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mon.domain.Project;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * Created by Grzegorz on 2015-05-07.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {


    private boolean face;

    private BigDecimal salary;

    private Project project;
}
