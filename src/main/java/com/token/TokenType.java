package main.java.com.token;

public enum TokenType {

    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"), // add, foobar, x, y,
    INT("INT"),
    ASSIGN("="),
    PLUS("+"),
    COMMA(","),
    SEMICOLON(";"),

    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),

    FUNCTION("FUNCTION"),
    LET("LET");

    private String literal;

    TokenType(String literal) {
        this.literal = literal;
    }

    public String toString() {
        return literal;
    }

    public static TokenType fromLiteral(String literal) {
        switch (literal) {
            case "=":
                return TokenType.ASSIGN;
            case ";":
                return TokenType.SEMICOLON;
            case "(":
                return TokenType.LPAREN;
            case ")":
                return TokenType.RPAREN;
            case ",":
                return TokenType.COMMA;
            case "+":
                return TokenType.PLUS;
            case "{":
                return TokenType.LBRACE;
            case "}":
                return TokenType.RBRACE;
            case "":
                return TokenType.EOF;
            default:
                return TokenType.ILLEGAL;
        }
    }

    public static TokenType lookupIdent(String ident) {
        if (ident.equals("fn")) {
            return TokenType.FUNCTION;
        } else if (ident.equals("let")) {
            return TokenType.LET;
        }
        return TokenType.IDENT;
    }
}