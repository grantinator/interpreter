package main.javatests.com.parser;

import static com.google.common.truth.Truth.assertThat;

import java.beans.Expression;
import java.beans.Transient;

import org.junit.Test;

import main.java.com.ast.ExpressionStatement;
import main.java.com.ast.Identifier;
import main.java.com.ast.LetStatement;
import main.java.com.ast.Program;
import main.java.com.ast.ReturnStatement;
import main.java.com.ast.Statement;
import main.java.com.lexer.Lexer;
import main.java.com.parser.Parser;
import main.java.com.token.TokenType;

public class ParserTests {
    @Test
    public void testLetStatements() {
        String input = "let x = 5;"
                + "let y = 10;"
                + "let foobar = 4567;";

        Lexer lexer = new Lexer(input);
        Parser parser = Parser.getInstance(lexer);

        Program program = parser.parseProgram();
        assertNoParserErrors(parser);

        assertThat(program.getStatements().size()).isEqualTo(3);

        assertLetStatement(program.getStatements().get(0), "x");
        assertLetStatement(program.getStatements().get(1), "y");
        assertLetStatement(program.getStatements().get(2), "foobar");
    }

    @Test
    public void testReturnStatements() {
        String input = "return 5; return 10; return add(15);";

        Lexer lexer = new Lexer(input);
        Parser parser = Parser.getInstance(lexer);

        Program program = parser.parseProgram();
        assertNoParserErrors(parser);

        assertReturnStatement(program.getStatements().get(0));
        assertReturnStatement(program.getStatements().get(1));
        assertReturnStatement(program.getStatements().get(2));
    }

    @Test
    public void testIdentifierExpression() {
        String input = "foobar";

        Lexer lexer = new Lexer(input);
        Parser parser = Parser.getInstance(lexer);

        Program program = parser.parseProgram();
        assertNoParserErrors(parser);

        assertThat(program.getStatements().size()).isEqualTo(1);
        assertThat(program.getStatements().get(0).getClass()).isEqualTo(ExpressionStatement.class);
        ExpressionStatement expressionStatement = (ExpressionStatement) program.getStatements().get(0);
        assertThat(expressionStatement.getExpression().getClass()).isEqualTo(Identifier.class);
        Identifier identifier = (Identifier) expressionStatement.getExpression();
        assertThat(identifier.getValue()).isEqulao("foobar");
        assertThat(identifier.tokenLiteral()).isEqualTo("foobar");
    }

    private void assertReturnStatement(Statement statement) {
        assertThat(statement.tokenLiteral()).isEqualTo("return");
        ReturnStatement returnStatement = (ReturnStatement) statement;
    }

    private void assertLetStatement(Statement statement, String name) {
        assertThat(statement.tokenLiteral()).isEqualTo("let");
        LetStatement letStatement = (LetStatement) statement;

        assertThat(letStatement.getName().getValue()).isEqualTo(name);
        assertThat(letStatement.getName().tokenLiteral()).isEqualTo(name);
    }

    private void assertNoParserErrors(Parser parser) {
        parser.getErrors().forEach(System.out::println);
        assertThat(parser.getErrors()).isEmpty();
    }
}
