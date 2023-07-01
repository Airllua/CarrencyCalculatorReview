package MyExceptions;

public class InvalidStringException extends IllegalArgumentException {
    public InvalidStringException() {
        super("An empty or invalid string");
    }
}
