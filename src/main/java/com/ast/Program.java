package main.java.com.ast;

import java.util.ArrayList;
import java.util.Arrays;

/* Root of every program the AST produces. */
public class Program implements Node {
    // Every program is a series of Statements.
    private ArrayList<Statement> statements;

    Program(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public String tokenLiteral() {
        if (this.statements.size() > 0) {
            return this.statements.get(0).tokenLiteral();
        }
        return "";
    }

    public String toString() {
        String statementsString = "";
        for (Statement statement : this.statements) {
            statementsString += statement.toString();
        }

        return statementsString;
    }

    public ArrayList<Statement> getStatements() {
        return this.statements;
    }

    public static class Builder {
        private ArrayList<Statement> statements;

        public Builder() {
            this.statements = new ArrayList<Statement>();
        }

        public Builder addStatement(Statement statement) {
            this.statements.add(statement);
            return this;
        }

        public Program build() {
            return new Program(this.statements);
        }
    }
}
