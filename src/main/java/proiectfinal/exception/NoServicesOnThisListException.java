package proiectfinal.exception;

public class NoServicesOnThisListException extends Exception {

    public NoServicesOnThisListException() { }

    public NoServicesOnThisListException(String message) {
        super(message);
    }

    public NoServicesOnThisListException(String message, Throwable cause) {
        super(message, cause);
    }
}
