package spittr.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dell on 2017-7-11.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "not modified")
public class NotModifiedException extends Exception {
}
