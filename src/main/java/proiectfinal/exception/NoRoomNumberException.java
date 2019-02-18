package proiectfinal.exception;

public class NoRoomNumberException extends Exception {
    public NoRoomNumberException() {
    }

    public NoRoomNumberException(String message) {
        super(message);
    }

    public NoRoomNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
