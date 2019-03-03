package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NoServicesOnThisListException extends Exception {

    private HttpStatus status = NOT_FOUND;

    public HttpStatus getStatus() {
        return status;
    }

    public NoServicesOnThisListException() { }

    public NoServicesOnThisListException(String message) {
        super(message);
    }

    public NoServicesOnThisListException(String message, Throwable cause) {
        super(message, cause);
    }
}
