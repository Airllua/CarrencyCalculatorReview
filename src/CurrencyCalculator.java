import Cash.Cash;
import Lexemes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyCalculator {
    private static final String regularExpression = "toRubles\\(|toDollars\\(|\\$?\\d+([.,]\\d+)?р?|[()+\\-*/]";

    private static final String exitString = "exit";

    private static List<String> tokenizeExpression(String expression) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile(regularExpression);

        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("\nTask: ");
                String input = scanner.nextLine();
                if (input.equals(exitString) || input.isEmpty()) break;

                LexemeTextAnalyzer analyzer = new LexemeTextAnalyzer();
                LexemeBuffer lexBuffer = new LexemeBuffer(analyzer.lexAnalyzeList(tokenizeExpression(input)));
                Cash rezCash = LexemeTextAnalyzer.syntaxAnalyze(lexBuffer);

                System.out.println(rezCash);
            }
            catch (Exception e) { System.out.println(e.getMessage()); }
        }
    }
}