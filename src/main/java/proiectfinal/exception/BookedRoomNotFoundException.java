package proiectfinal.exception;

public class BookedRoomNotFoundException extends Exception {

    public BookedRoomNotFoundException() {
    }

    public BookedRoomNotFoundException(String message) {
        super(message);
    }

    public BookedRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
