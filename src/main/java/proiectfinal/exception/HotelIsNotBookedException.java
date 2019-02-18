package proiectfinal.exception;

public class HotelIsNotBookedException extends Exception {
    public HotelIsNotBookedException() {
    }

    public HotelIsNotBookedException(String message) {
        super(message);
    }

    public HotelIsNotBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
