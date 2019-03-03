package proiectfinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class HotelIsNotBookedException extends Exception {

    private HttpStatus status = NOT_FOUND;

    public HttpStatus getStatus() {
        return status;
    }

    public HotelIsNotBookedException() {
    }

    public HotelIsNotBookedException(String message) {
        super(message);
    }

    public HotelIsNotBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
