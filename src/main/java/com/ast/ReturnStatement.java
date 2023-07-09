package main.java.com.ast;

import main.java.com.token.Token;

public class ReturnStatement implements Statement {
    private Token token;
    private Expression expression;

    ReturnStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    public Token getToken() {
        return this.token;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public String tokenLiteral() {
        return this.token.getLiteral();
    }

    public ReturnStatement statementNode() {
        return this;
    }

    public static class Builder {
        private Token token;
        private Expression expression;

        public Builder() {
        }

        public Builder setToken(Token token) {
            this.token = token;
            return this;
        }

        public Builder setExpression(Expression expression) {
            this.expression = expression;
            return this;
        }

        public ReturnStatement build() {
            return new ReturnStatement(this.token, this.expression);
        }
    }
}
