package mon.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by kalipiec on 2015-04-14.
 */
@RequiredArgsConstructor
@Getter
public enum EntranceStatus {

    ALREADY_IN(HttpStatus.ALREADY_REPORTED), OK(HttpStatus.OK);

    private final HttpStatus status;
}
