package main.java.com.ast;

import main.java.com.token.Token;

/* Class for expression statements.
 * 
 * Expression statements are complete statements for an expression. These are legal in our programming language. An example would be
 * 
 * x + 5; <-- Expression Statement
 * This is a legal statement and does not need to be associated with a let or return statment.
 */
public class ExpressionStatement implements Statement {
    private Token token;
    private Expression expression;

    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    public String toString() {
        return this.expression.toString();
    }

    public ExpressionStatement statementNode() {
        return this;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public String tokenLiteral() {
        return this.token.getLiteral();
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

        public ExpressionStatement build() {
            return new ExpressionStatement(this.token, this.expression);
        }
    }
}
