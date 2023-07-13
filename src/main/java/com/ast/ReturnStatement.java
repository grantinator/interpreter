package main.java.com.ast;

import main.java.com.token.Token;

public class ReturnStatement implements Statement {
    private Token token;
    private Expression returnValue;

    ReturnStatement(Token token, Expression returnValue) {
        this.token = token;
        this.returnValue = returnValue;
    }

    public Token getToken() {
        return this.token;
    }

    public String toString() {
        return String.format("%s %s;", this.token.getLiteral(), this.returnValue);
    }

    public Expression getExpression() {
        return this.returnValue;
    }

    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    public ReturnStatement statementNode() {
        return this;
    }

    public static class Builder {
        private Token token;
        private Expression returnValue;

        public Builder() {
        }

        public Builder setToken(Token token) {
            this.token = token;
            return this;
        }

        public Builder setReturnValue(Expression returnValue) {
            this.returnValue = returnValue;
            return this;
        }

        public ReturnStatement build() {
            return new ReturnStatement(this.token, this.returnValue);
        }
    }
}
