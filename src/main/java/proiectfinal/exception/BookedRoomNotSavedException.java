package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_MODIFIED;

@ResponseStatus(NOT_MODIFIED)
public class BookedRoomNotSavedException extends Exception {

    private HttpStatus status = NOT_MODIFIED;

    public HttpStatus getStatus() {
        return status;
    }

    public BookedRoomNotSavedException() {
    }

    public BookedRoomNotSavedException(String message) {
        super(message);
    }

    public BookedRoomNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

}
