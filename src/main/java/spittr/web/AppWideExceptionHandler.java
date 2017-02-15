package spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
}
