package main.java.com.ast;

import java.util.ArrayList;

import main.java.com.token.Token;

public class LetStatement implements Statement {

    private final Token token; // Should always be TokenType.LET.
    private final Identifier name;
    private Expression value;

    LetStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    public LetStatement statementNode() {
        return this;
    }

    public String toString() {
        return String.format("%s %s = %s;", this.token.getLiteral(), this.name.getValue(), this.value.tokenLiteral());
    }

    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    public Token getToken() {
        return this.token;
    }

    public Identifier getName() {
        return this.name;
    }

    public Expression getValue() {
        return this.value;
    }

    public static class Builder {
        private Token token;
        private Identifier name;
        private Expression value;

        public Builder() {
        }

        public Builder setToken(Token token) {
            this.token = token;
            return this;
        }

        public Builder setName(Identifier identifier) {
            this.name = identifier;
            return this;
        }

        public Builder setValue(Expression value) {
            this.value = value;
            return this;
        }

        public LetStatement build() {
            return new LetStatement(this.token, this.name, this.value);
        }
    }
}
