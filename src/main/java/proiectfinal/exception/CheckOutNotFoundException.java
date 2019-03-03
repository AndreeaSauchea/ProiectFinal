package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class CheckOutNotFoundException extends Exception{

    private HttpStatus status = NOT_FOUND;

    public HttpStatus getStatus() {
        return status;
    }

    public CheckOutNotFoundException() {
    }

    public CheckOutNotFoundException(String message) {
        super(message);
    }

    public CheckOutNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
