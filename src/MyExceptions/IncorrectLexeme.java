package MyExceptions;

public class IncorrectLexeme extends IllegalArgumentException {
    public IncorrectLexeme() {
        super("Incorrect lexeme found");
    }
}
