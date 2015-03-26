package mon.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mon.domain.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by kalipiec on 2015-03-26.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    public String login;


    @Size(max = 50)
    public String firstName;

    @Size(max = 50)
    public String lastName;

    public static SimpleUserDTO fromUser(User user) {
        return new SimpleUserDTO(user.getLogin(), user.getFirstName(), user.getLastName());
    }
}
