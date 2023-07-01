package MyExceptions;

public class UnknownCurrencyException extends IllegalArgumentException {
    public UnknownCurrencyException() {
        super("Currency selected incorrectly");
    }
}
