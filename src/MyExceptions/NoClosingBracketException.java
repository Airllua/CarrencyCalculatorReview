package MyExceptions;

public class NoClosingBracketException extends IllegalArgumentException {
    public NoClosingBracketException() {
        super("Closing bracket not found");
    }
}
