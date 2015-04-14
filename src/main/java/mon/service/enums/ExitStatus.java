package mon.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by kalipiec on 2015-04-14.
 */
@RequiredArgsConstructor
@Getter
public enum ExitStatus {

    NOT_IN(HttpStatus.BAD_GATEWAY), OK(HttpStatus.OK);

    private final HttpStatus status;
}
