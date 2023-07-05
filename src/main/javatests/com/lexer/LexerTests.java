package main.javatests.com.lexer;

import static com.google.common.truth.Truth.assertThat;

import java.beans.Transient;

import org.junit.Test;

import main.java.com.lexer.Lexer;
import main.java.com.token.Token;
import main.java.com.token.TokenType;

public class LexerTests {
    @Test
    public void testNextToken_success() {
        String input = "=+(){},;";

        Lexer lexer = new Lexer(input);
        Token parsedToken;
        for (int i = 0; i < input.length(); i++) {
            TokenType tokenType = TokenType.fromLiteral(String.valueOf(input.charAt(i)));
            parsedToken = lexer.nextToken();
            assertThat(parsedToken.getLiteral()).isEqualTo(tokenType.toString());
        }
    }

    @Test
    public void testNextToken_parsesIdentifiers() {
        String input = "let foo = 3";

        Lexer lexer = new Lexer(input);
        assertToken(lexer.nextToken(), TokenType.LET, "let");
        assertToken(lexer.nextToken(), TokenType.IDENT, "foo");
        assertToken(lexer.nextToken(), TokenType.ASSIGN, "=");
        assertToken(lexer.nextToken(), TokenType.INT, "3");

    }

    @Test
    public void testNextToken_parsesKeywords() {
        String input = "let myFn = fn(args) {}";

        Lexer lexer = new Lexer(input);
        assertToken(lexer.nextToken(), TokenType.LET, "let");
        assertToken(lexer.nextToken(), TokenType.IDENT, "myFn");
        assertToken(lexer.nextToken(), TokenType.ASSIGN, "=");
        assertToken(lexer.nextToken(), TokenType.FUNCTION, "fn");
        assertToken(lexer.nextToken(), TokenType.LPAREN, "(");
        assertToken(lexer.nextToken(), TokenType.IDENT, "args");
        assertToken(lexer.nextToken(), TokenType.RPAREN, ")");
        assertToken(lexer.nextToken(), TokenType.LBRACE, "{");
        assertToken(lexer.nextToken(), TokenType.RBRACE, "}");
    }

    private void assertToken(Token token, TokenType tokenType, String tokenLiteral) {
        assertThat(token.getType()).isEqualTo(tokenType);
        assertThat(token.getLiteral()).isEqualTo(tokenLiteral);
    }
}
