package main.java.com.token;

public enum TokenType {

    ILLEGAL("ILLEGAL"),
    EOF("EOF"),
    IDENT("IDENT"),
    INT("INT"),

    // Operators.
    ASSIGN("="),
    PLUS("+"),
    MINUS("-"),
    BANG("!"),
    ASTERISK("*"),
    SLASH("/"),
    LT("<"),
    GT(">"),
    EQ("=="),
    NOT_EQ("!="),

    // Special characters.
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    COMMA(","),
    SEMICOLON(";"),

    // Keywords.
    FUNCTION("FUNCTION"),
    LET("LET"),
    TRUE("TRUE"),
    FALSE("FALSE"),
    IF("IF"),
    ELSE("ELSE"),
    RETURN("RETURN");

    private String literal;

    TokenType(String literal) {
        this.literal = literal;
    }

    public String toString() {
        return literal;
    }

    public String getLiteral() {
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
        } else if (ident.equals("true")) {
            return TokenType.TRUE;
        } else if (ident.equals("false")) {
            return TokenType.FALSE;
        } else if (ident.equals("if")) {
            return TokenType.IF;
        } else if (ident.equals("else")) {
            return TokenType.ELSE;
        } else if (ident.equals("return")) {
            return TokenType.RETURN;
        }
        return TokenType.IDENT;
    }
}