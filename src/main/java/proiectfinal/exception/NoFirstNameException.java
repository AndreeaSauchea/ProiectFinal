package proiectfinal.exception;

public class NoFirstNameException extends Exception {

    public NoFirstNameException() {
    }

    public NoFirstNameException(String message) {
        super(message);
    }

    public NoFirstNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
