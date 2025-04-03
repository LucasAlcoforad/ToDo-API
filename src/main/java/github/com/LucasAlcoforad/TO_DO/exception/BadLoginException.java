package github.com.LucasAlcoforad.TO_DO.exception;

public class BadLoginException extends RuntimeException{
    public BadLoginException(String message) {
        super(message);
    }
}
