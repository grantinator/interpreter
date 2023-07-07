package main.java.com.ast;

import java.util.ArrayList;

import main.java.com.token.Token;

public class Identifier implements Expression {
    private final Token token; // TokenType.IDENT token.
    private final String value;

    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    public Identifier expressionNode() {
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    public static class Builder {
        private Token token;
        private String value;

        public Builder() {
        }

        public Builder setToken(Token token) {
            this.token = token;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Identifier build() {
            return new Identifier(this.token, this.value);
        }
    }
}
