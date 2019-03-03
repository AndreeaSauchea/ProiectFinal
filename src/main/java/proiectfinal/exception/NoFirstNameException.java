package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ResponseStatus(NOT_ACCEPTABLE)
public class NoFirstNameException extends Exception {

    private HttpStatus status = NOT_ACCEPTABLE;

    public HttpStatus getStatus() {
        return status;
    }

    public NoFirstNameException() {
    }

    public NoFirstNameException(String message) {
        super(message);
    }

    public NoFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
