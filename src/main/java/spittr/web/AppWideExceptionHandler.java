package spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import spittr.web.exception.DuplicateSpittleException;

import java.io.IOException;

/**
 * Created by dell on 2017-2-8.
 */
@ControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(DuplicateSpittleException.class)
    public String duplicateSpittleHandler() {
        return "error/duplicate";
    }

    @ExceptionHandler(IOException.class)
    public String ioHandler() {
        return "error/io";
    }

    @ExceptionHandler(AuthenticationException.class)
    public String authenticationHandler() {
        return "error/authentication";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedHandler() {
        return "error/authentication";
    }

    @MessageExceptionHandler(Throwable.class)
    @SendToUser("/queue/errors")
    public Throwable handleExceptions(Throwable t) {
        return t;
    }
}
