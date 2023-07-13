package main.java.com.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import main.java.com.ast.Expression;
import main.java.com.ast.Identifier;
import main.java.com.ast.LetStatement;
import main.java.com.ast.Program;
import main.java.com.ast.ReturnStatement;
import main.java.com.lexer.Lexer;
import main.java.com.token.Token;
import main.java.com.token.TokenType;
import main.java.com.ast.Statement;

public class Parser {
    private final Lexer lexer;

    private Token currToken;
    private Token peekToken;

    // Supplier<Expression> is for methods with no argument and return type
    // ast.Expression.
    private Map<TokenType, Supplier<Expression>> prefixParseFn = new HashMap();
    // Function<Expression, Expression> takes ast.Expression as argument and returns
    // an ast.Expression.
    private Map<TokenType, Function<Expression, Expression>> infixParseFn = new HashMap();

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

    public void registerPrefix(TokenType tokenType, Supplier<Expression> prefixParseFn) {
        this.prefixParseFn.put(tokenType, prefixParseFn);
    }

    public void registerInfix(TokenType tokenType, Function<Expression, Expression> infixParseFn) {
        this.infixParseFn.put(tokenType, infixParseFn);
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
            case RETURN:
                return this.parseReturnStatement();
            default:
                return null;
        }
    }

    private Statement parseReturnStatement() {
        ReturnStatement.Builder returnStatementBuilder = new ReturnStatement.Builder().setToken(this.currToken);

        this.nextToken();

        // TODO: Skipping over the expression until we encounter a semicolon.
        if (!this.currTokenIs(TokenType.SEMICOLON)) {
            this.nextToken();
        }
        return returnStatementBuilder.build();
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
