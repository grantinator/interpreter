package main.java.com.parser;

import java.util.ArrayList;

import main.java.com.ast.Identifier;
import main.java.com.ast.LetStatement;
import main.java.com.ast.Program;
import main.java.com.lexer.Lexer;
import main.java.com.token.Token;
import main.java.com.token.TokenType;
import main.java.com.ast.Statement;

public class Parser {
    private final Lexer lexer;
    private Token currToken;
    private Token peekToken;

    private final ArrayList<ParserError> errors = new ArrayList<ParserError>();

    private Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public static Parser getInstance(Lexer lexer) {
        Parser instance = new Parser(lexer);
        // Read two tokens so currToken and peekToken are both set.
        instance.nextToken();
        instance.nextToken();
        return instance;
    }

    public void nextToken() {
        this.currToken = peekToken;
        this.peekToken = lexer.nextToken();
    }

    public ArrayList<ParserError> getErrors() {
        return this.errors;
    }

    public Program parseProgram() {
        Program.Builder programBuilder = new Program.Builder();

        while (!this.currTokenIs(TokenType.EOF)) {
            Statement currStatement = this.parseStatement();
            if (currStatement != null) {
                programBuilder.addStatement(currStatement);
            }
            this.nextToken();
        }

        return programBuilder.build();
    }

    private Statement parseStatement() {
        switch (this.currToken.getType()) {
            case LET:
                return this.parseLetStatement();
            default:
                return null;
        }
    }

    private Statement parseLetStatement() {
        LetStatement.Builder letStatement = new LetStatement.Builder().setToken(this.currToken);

        if (!this.expectPeek(TokenType.IDENT)) {
            return null;
        }

        letStatement.setName(
                new Identifier.Builder().setToken(this.currToken).setValue(this.currToken.getLiteral()).build());

        if (!this.expectPeek(TokenType.ASSIGN)) {
            return null;
        }

        // TODO(grant): capture expressions after the assignment.
        while (!this.currTokenIs(TokenType.SEMICOLON)) {
            this.nextToken();
        }

        return letStatement.build();
    }

    private boolean currTokenIs(TokenType tokenType) {
        return this.currToken.getType() == tokenType;
    }

    private boolean peekTokenIs(TokenType tokenType) {
        return this.peekToken.getType() == tokenType;
    }

    private boolean expectPeek(TokenType tokenType) {
        if (this.peekTokenIs(tokenType)) {
            this.nextToken();
            return true;
        }
        this.peekError(tokenType);
        return false;
    }

    private void peekError(TokenType tokenType) {
        this.errors.add(new ParserError(tokenType, this.peekToken.getType()));
    }
}
