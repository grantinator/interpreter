package main.javatests.com.ast;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import main.java.com.ast.Identifier;
import main.java.com.ast.LetStatement;
import main.java.com.ast.Program;
import main.java.com.token.Token;
import main.java.com.token.TokenType;

public class AstTests {
        @Test
        public void TestToString() {
                /*
                 * We will create an AST representing the code 'let myVar = anotherVar' and test
                 * that the toString method returns this.
                 */

                Program.Builder programBuilder = new Program.Builder();

                LetStatement letStatement = new LetStatement.Builder()
                                .setToken(new Token.Builder().setType(TokenType.LET).setLiteral("let").build())
                                .setName(new Identifier.Builder()
                                                .setToken(new Token.Builder().setType(TokenType.IDENT)
                                                                .setLiteral("myVar").build())
                                                .setValue("myVar").build())
                                .setValue(new Identifier.Builder()
                                                .setToken(new Token.Builder().setType(TokenType.IDENT)
                                                                .setLiteral("anotherVar").build())
                                                .setValue("anotherVar").build())
                                .build();
                programBuilder.addStatement(letStatement);

                assertThat(programBuilder.build().toString()).isEqualTo("let myVar = anotherVar;");
        }
}
