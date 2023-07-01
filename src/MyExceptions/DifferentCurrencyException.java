package MyExceptions;

public class DifferentCurrencyException extends IllegalArgumentException{
    public DifferentCurrencyException() {
        super("You can't stack different currencies");
    }
}
