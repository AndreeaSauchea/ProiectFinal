package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class CheckInNotFoundException extends Exception {

    private HttpStatus status = NOT_FOUND;

    public HttpStatus getStatus() {
        return status;
    }

    public CheckInNotFoundException() {
    }

    public CheckInNotFoundException(String message) {
        super(message);
    }

    public CheckInNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
