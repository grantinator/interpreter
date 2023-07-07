package main.java.com.repl;

import java.util.Scanner;
import main.java.com.lexer.Lexer;
import main.java.com.token.Token;
import main.java.com.token.TokenType;

public class Repl {

    private static final String PROMPT = ">>";

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.console().printf("\n%s ", PROMPT);

            String scannedInput = scanner.nextLine();

            if (scannedInput.isEmpty()) {
                return;
            }

            Lexer lexer = new Lexer(scannedInput);
            Token currentToken = lexer.nextToken();
            while (currentToken.getType() != TokenType.EOF && currentToken.getType() != TokenType.ILLEGAL) {
                System.console().printf("%s\n", currentToken);
                currentToken = lexer.nextToken();
            }

        }
    }
}
