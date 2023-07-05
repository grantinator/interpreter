package main.java.com.lexer;

import main.java.com.token.Token;
import main.java.com.token.TokenType;

/* Lexer class for parsing inputs into {@link Tokens}. */
public class Lexer {
    private final String input;
    private int position = -1; // current position in input (points to current char).
    private int readPosition = 0; // current reading position in input (after current char).
    private char ch = 0; // current char under examination.

    public Lexer(String input) {
        this.input = input;
        this.readChar();
    }

    public void readChar() {
        if (this.readPosition >= this.input.length()) {
            this.ch = 0;
        } else {
            this.ch = this.input.charAt(this.readPosition);
        }
        this.position = this.readPosition;
        this.readPosition += 1;
    }

    public Token nextToken() {
        Token.Builder tokenBuilder = new Token.Builder();
        this.skipWhitespace();

        String tokenLiteral = Character.toString(this.ch);

        switch (tokenLiteral) {
            case ";":
                tokenBuilder.setType(TokenType.SEMICOLON);
                break;
            case "(":
                tokenBuilder.setType(TokenType.LPAREN);
                break;
            case ")":
                tokenBuilder.setType(TokenType.RPAREN);
                break;
            case ",":
                tokenBuilder.setType(TokenType.COMMA);
                break;
            case "+":
                tokenBuilder.setType(TokenType.PLUS);
                break;
            case "=":
                tokenBuilder.setType(TokenType.ASSIGN);
                break;
            case "-":
                tokenBuilder.setType(TokenType.MINUS);
                break;
            case "!":
                tokenBuilder.setType(TokenType.BANG);
                break;
            case "*":
                tokenBuilder.setType(TokenType.ASTERISK);
                break;
            case "/":
                tokenBuilder.setType(TokenType.SLASH);
                break;
            case "<":
                tokenBuilder.setType(TokenType.LT);
                break;
            case ">":
                tokenBuilder.setType(TokenType.GT);
                break;
            case "{":
                tokenBuilder.setType(TokenType.LBRACE);
                break;
            case "}":
                tokenBuilder.setType(TokenType.RBRACE);
                break;
            case "":
                tokenBuilder.setType(TokenType.EOF);
                break;
            default:
                if (Character.isLetter(this.ch)) {
                    tokenLiteral = this.readIdentifier();
                    tokenBuilder.setLiteral(tokenLiteral);
                    tokenBuilder.setType(TokenType.lookupIdent(tokenLiteral));
                    return tokenBuilder.build();
                } else if (isDigit(this.ch)) {
                    tokenLiteral = this.readNumber();
                    tokenBuilder.setLiteral(tokenLiteral);
                    tokenBuilder.setType(TokenType.INT);
                    return tokenBuilder.build();
                } else {
                    tokenBuilder.setType(TokenType.ILLEGAL);
                }
        }
        tokenBuilder.setLiteral(tokenLiteral);
        this.readChar();
        return tokenBuilder.build();

    }

    private String readIdentifier() {
        int startPosition = this.position;
        // Identifiers are a string of 1 or more non-sepcial characters.
        while (isLetter(this.ch)) {
            this.readChar();
        }
        return this.input.substring(startPosition, this.position);
    }

    /*
     * A custom isLetter implementation is necessary to give control of what is
     * considered a letter in our programming language.
     */
    private static boolean isLetter(char character) {
        return ('a' <= character && character <= 'z') || ('A' <= character && character <= 'Z') || (character == '_');
    }

    private void skipWhitespace() {
        while (isWhitespace(this.ch)) {
            this.readChar();
        }
    }

    private static boolean isWhitespace(char character) {
        return (character == ' ' || character == '\t' || character == '\r' || character == '\n');
    }

    private String readNumber() {
        int startPosition = this.position;
        while (isDigit(this.ch)) {
            this.readChar();
        }

        return this.input.substring(startPosition, this.position);
    }

    private static boolean isDigit(char character) {
        return '0' <= character && character <= '9';
    }
}
