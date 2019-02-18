package proiectfinal.exception;

public class CheckInNotFoundException extends Exception {

    public CheckInNotFoundException() {
    }

    public CheckInNotFoundException(String message) {
        super(message);
    }

    public CheckInNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
