package proiectfinal.exception;

public class CheckOutNotFoundException extends Exception{

    public CheckOutNotFoundException() {
    }

    public CheckOutNotFoundException(String message) {
        super(message);
    }

    public CheckOutNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
