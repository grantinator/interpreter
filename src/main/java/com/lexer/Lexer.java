package main.java.com.lexer;

import main.java.com.token.Token;
import main.java.com.token.TokenType;

/* Lexer class for parsing inputs into {@link Tokens}. */
public class Lexer {
    private final String input;
    private int position = -1; // current position in input (points to current char).
    private int readPosition = 0; // current reading position in input (after current char).
    private char ch = 0; // current char under examination. TODO(grantbaum): refactor so this uses byte.

    public Lexer(String input) {
        this.input = input;
        this.readChar();
    }

    // Returns the next char without the lexer's position in the input.
    public char peekChar() {
        if (this.readPosition >= this.input.length()) {
            return '\0';
        }

        return this.input.charAt(this.readPosition);
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
            case "-":
                tokenBuilder.setType(TokenType.MINUS);
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
            case "\"":
                tokenLiteral = this.readString();
                tokenBuilder.setType(TokenType.STRING);
                break;
            case "=":
                // Determine whether current char is the start of a '==' sequence or a single
                // '='.
                if (this.peekChar() == '=') {
                    tokenBuilder = setTwoCharLiteral(tokenBuilder.setType(TokenType.EQ));
                } else {
                    tokenBuilder.setType(TokenType.ASSIGN);
                }
                break;
            case "!":
                // Determine whether current char is the start of a '!=' sequence or a single
                // '!'.
                if (this.peekChar() == '=') {
                    tokenBuilder = setTwoCharLiteral(tokenBuilder.setType(TokenType.NOT_EQ));
                } else {
                    tokenBuilder.setType(TokenType.BANG);
                }
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
                } else if (this.ch == 0) {
                    tokenLiteral = "";
                    tokenBuilder.setType(TokenType.EOF);
                } else {
                    tokenBuilder.setType(TokenType.ILLEGAL);
                }
        }
        tokenBuilder.setLiteral(tokenLiteral);
        this.readChar();
        return tokenBuilder.build();

    }

    /*
     * Builds two character token assuming that this.position points to the first
     * character's location.
     */
    private Token.Builder setTwoCharLiteral(Token.Builder tokenBuilder) {
        char firstChar = this.ch;
        this.readChar();
        return tokenBuilder.setLiteral(String.valueOf(firstChar) + String.valueOf(this.ch));
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

    /*
     * Reads an identifier (variable) name from the input.
     * 
     * Assumes that the lexer's current position is the start of the identifier.
     * After calling readIdentifier the the lexer's position will point to the
     * character after the end of the identifier.
     */
    private String readIdentifier() {
        int startPosition = this.position;
        // Identifiers are a string of 1 or more non-sepcial characters.
        while (isLetter(this.ch)) {
            this.readChar();
        }
        return this.input.substring(startPosition, this.position);
    }

    /*
     * Reads a string from the input.
     * 
     * Assumes the lexer's current position is the first quotation mark surrounding
     * the string. At the end of this call the lexer's position will point to the
     * character after the final quotation mark in the string.
     */
    private String readString() {
        // We want to skip the initial quotation mark.
        this.readChar();
        int startPosition = this.position;

        while (isLetter(this.ch)) {
            // Check if the next character is the quotation mark ending the string.
            if (this.peekChar() == '"') {
                break;
            }
            this.readChar();
        }

        String stringLiteral = this.input.substring(startPosition, this.position + 1);
        this.readChar();

        return stringLiteral;
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
