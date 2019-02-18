package proiectfinal.exception;

public class BookedRoomNotSavedException extends Exception {

    public BookedRoomNotSavedException() {
    }

    public BookedRoomNotSavedException(String message) {
        super(message);
    }

    public BookedRoomNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

}
