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
        String input = "=+-*/<>(){},;";

        Lexer lexer = new Lexer(input);
        assertToken(lexer.nextToken(), TokenType.ASSIGN, "=");
        assertToken(lexer.nextToken(), TokenType.PLUS, "+");
        assertToken(lexer.nextToken(), TokenType.MINUS, "-");
        assertToken(lexer.nextToken(), TokenType.ASTERISK, "*");
        assertToken(lexer.nextToken(), TokenType.SLASH, "/");
        assertToken(lexer.nextToken(), TokenType.LT, "<");
        assertToken(lexer.nextToken(), TokenType.GT, ">");
        assertToken(lexer.nextToken(), TokenType.LPAREN, "(");
        assertToken(lexer.nextToken(), TokenType.RPAREN, ")");
        assertToken(lexer.nextToken(), TokenType.LBRACE, "{");
        assertToken(lexer.nextToken(), TokenType.RBRACE, "}");
        assertToken(lexer.nextToken(), TokenType.COMMA, ",");
        assertToken(lexer.nextToken(), TokenType.SEMICOLON, ";");
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

    @Test
    public void testNextToken_fullScript() {
        // TODO(grant): figure out how to break this across multiple lines.
        String input = "let five = 5; let ten = 10; let add = fn(x,y) { if (x > 10) { return false; } return x+y;}";

        Lexer lexer = new Lexer(input);

        // Line 1.
        assertToken(lexer.nextToken(), TokenType.LET);
        assertToken(lexer.nextToken(), TokenType.IDENT, "five");
        assertToken(lexer.nextToken(), TokenType.ASSIGN);
        assertToken(lexer.nextToken(), TokenType.INT, "5");
        assertToken(lexer.nextToken(), TokenType.SEMICOLON);

        // Line 2.
        assertToken(lexer.nextToken(), TokenType.LET);
        assertToken(lexer.nextToken(), TokenType.IDENT, "ten");
        assertToken(lexer.nextToken(), TokenType.ASSIGN);
        assertToken(lexer.nextToken(), TokenType.INT, "10");
        assertToken(lexer.nextToken(), TokenType.SEMICOLON);

        // Line 3.
        assertToken(lexer.nextToken(), TokenType.LET);
        assertToken(lexer.nextToken(), TokenType.IDENT, "add");
        assertToken(lexer.nextToken(), TokenType.ASSIGN);
        assertToken(lexer.nextToken(), TokenType.FUNCTION);
        assertToken(lexer.nextToken(), TokenType.LPAREN);
        assertToken(lexer.nextToken(), TokenType.IDENT, "x");
        assertToken(lexer.nextToken(), TokenType.COMMA);
        assertToken(lexer.nextToken(), TokenType.IDENT, "y");
        assertToken(lexer.nextToken(), TokenType.RPAREN);
        assertToken(lexer.nextToken(), TokenType.LBRACE);

        // Line 4.
        assertToken(lexer.nextToken(), TokenType.IF);
        assertToken(lexer.nextToken(), TokenType.LPAREN);
        assertToken(lexer.nextToken(), TokenType.IDENT, "x");
        assertToken(lexer.nextToken(), TokenType.GT);
        assertToken(lexer.nextToken(), TokenType.INT, "10");
        assertToken(lexer.nextToken(), TokenType.RPAREN);
        assertToken(lexer.nextToken(), TokenType.LBRACE);

        // Line 5.
        assertToken(lexer.nextToken(), TokenType.RETURN);
        assertToken(lexer.nextToken(), TokenType.FALSE);
        assertToken(lexer.nextToken(), TokenType.SEMICOLON);

        // Line 6.
        assertToken(lexer.nextToken(), TokenType.RBRACE);

        // Line 7.
        assertToken(lexer.nextToken(), TokenType.RETURN);
        assertToken(lexer.nextToken(), TokenType.IDENT, "x");
        assertToken(lexer.nextToken(), TokenType.PLUS);
        assertToken(lexer.nextToken(), TokenType.IDENT, "y");
        assertToken(lexer.nextToken(), TokenType.SEMICOLON);

        // Line 8.
        assertToken(lexer.nextToken(), TokenType.RBRACE);

    }

    // TODO(grant): consider creating even higher level assertion functions, e.g.
    // assertAssignment for basic assignments like let x = 5;
    private void assertToken(Token token, TokenType expectedType) {
        assertThat(token.getType()).isEqualTo(expectedType);
    }

    private void assertToken(Token token, TokenType expectedTokenType, String expectedTokenLiteral) {
        assertThat(token.getType()).isEqualTo(expectedTokenType);
        assertThat(token.getLiteral()).isEqualTo(expectedTokenLiteral);
    }
}
