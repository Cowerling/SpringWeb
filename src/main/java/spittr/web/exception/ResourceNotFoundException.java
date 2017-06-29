package spittr.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by dell on 2017-6-28.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Request Inappropriate Thing")
public class ResourceNotFoundException extends Exception {
}
