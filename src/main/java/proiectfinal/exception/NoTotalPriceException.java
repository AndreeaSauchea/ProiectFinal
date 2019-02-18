package proiectfinal.exception;

public class NoTotalPriceException extends Exception {

    public NoTotalPriceException() {
    }

    public NoTotalPriceException(String message) {
        super(message);
    }

    public NoTotalPriceException(String message, Throwable cause) {
        super(message, cause);
    }
}
