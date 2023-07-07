package main.java.com.parser;

import main.java.com.token.Token;
import main.java.com.token.TokenType;

public class ParserError {
    private final String message;

    ParserError(TokenType expectedType, TokenType actualType) {
        this.message = String.format("Expected next token to be %s but got %s instead", expectedType, actualType);
    }

    public String toString() {
        return this.message;
    }
}
