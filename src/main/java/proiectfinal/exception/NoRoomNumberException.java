package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ResponseStatus(NOT_ACCEPTABLE)
public class NoRoomNumberException extends Exception {

    private HttpStatus status = NOT_ACCEPTABLE;

    public HttpStatus getStatus() {
        return status;
    }

    public NoRoomNumberException() {
    }

    public NoRoomNumberException(String message) {
        super(message);
    }

    public NoRoomNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
