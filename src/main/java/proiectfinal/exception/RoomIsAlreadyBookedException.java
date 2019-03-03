package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ResponseStatus(NOT_ACCEPTABLE)
public class RoomIsAlreadyBookedException extends Exception {

    private HttpStatus status = NOT_ACCEPTABLE;

    public HttpStatus getStatus() {
        return status;
    }

    public RoomIsAlreadyBookedException() {
    }

    public RoomIsAlreadyBookedException(String message) {
        super(message);
    }

    public RoomIsAlreadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
