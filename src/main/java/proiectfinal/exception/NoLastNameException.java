package proiectfinal.exception;

public class NoLastNameException extends Exception {
    public NoLastNameException() {
    }

    public NoLastNameException(String message) {
        super(message);
    }

    public NoLastNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
