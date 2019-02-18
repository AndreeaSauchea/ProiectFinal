package proiectfinal.exception;

public class NoRoomIdException extends Exception {

    public NoRoomIdException() {
    }

    public NoRoomIdException(String message) {
        super(message);
    }

    public NoRoomIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
