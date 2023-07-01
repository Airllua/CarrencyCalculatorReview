package Lexemes;

import Enums.*;
import Cash.*;
import MyExceptions.*;

import java.util.ArrayList;
import java.util.List;

/*------------------------------------------------------------------
 *                           PARSER RULES
 *------------------------------------------------------------------*/

//    plusminus: factor ( ( '+' | '-' ) multdiv )* ;
//
//    factor : NUMBER | '(' expr ')' ;

public class LexemeTextAnalyzer{
    final String LEFT_BRACKET = "(";
    final String RIGHT_BRACKET = ")";
    final String PLUS = "+";
    final String MINUS = "-";
    final String TRANSFER_TO_USD = "toDollars(";
    final String TRANSFER_TO_RUB = "toRubles(";

    public List<Lexeme> lexAnalyzeList(List<String> textTokens) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();

        for(String token : textTokens){
            switch (token) {
                case LEFT_BRACKET -> lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, token));
                case RIGHT_BRACKET -> lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, token));
                case PLUS -> lexemes.add(new Lexeme(LexemeType.PLUS, token));
                case MINUS -> lexemes.add(new Lexeme(LexemeType.MINUS, token));
                case TRANSFER_TO_USD -> lexemes.add(new Lexeme(LexemeType.TRANSFER_TO_USD, token));
                case TRANSFER_TO_RUB -> lexemes.add(new Lexeme(LexemeType.TRANSFER_TO_RUB, token));
                default -> lexemes.add(new Lexeme(LexemeType.NUMBER, token));
            }
        }

        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static Cash syntaxAnalyze(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            throw new InvalidStringException();
        } else {
            lexemes.back();
            return plusMinus(lexemes);
        }
    }

    private static Cash plusMinus(LexemeBuffer lexemes) {
        Cash value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case PLUS -> value.SumCash(factor(lexemes));
                case MINUS -> value.MinusCash(factor(lexemes));
                case EOF, RIGHT_BRACKET -> {
                    lexemes.back();
                    return value;
                }
                default -> throw new IncorrectLexeme();
            }
        }

    }
    private static Cash factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NUMBER -> { return Cash.determineCurrency(lexeme.value); }
            case LEFT_BRACKET -> {
                Cash value = plusMinus(lexemes);
                lexeme = lexemes.next();

                if (lexeme.type != LexemeType.RIGHT_BRACKET)
                    throw new NoClosingBracketException();

                return value;
            }
            case TRANSFER_TO_RUB -> {
                Cash value = plusMinus(lexemes);
                lexeme = lexemes.next();

                if (lexeme.type == LexemeType.RIGHT_BRACKET && value.getCurrency() == Currency.USD)
                    value = value.convertCurrency();
                else throw new NoClosingBracketException();

                return value;
            }
            case TRANSFER_TO_USD -> {
                Cash value = plusMinus(lexemes);
                lexeme = lexemes.next();

                if (lexeme.type == LexemeType.RIGHT_BRACKET && value.getCurrency() == Currency.RUB)
                    value = value.convertCurrency();
                else throw new NoClosingBracketException();

                return value;
            }
            default -> throw new IncorrectLexeme();
        }
    }
}
