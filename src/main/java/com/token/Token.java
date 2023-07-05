package main.java.com.token;

public class Token {
    private TokenType type;
    private String literal;

    private Token(Builder builder) {
        this.type = builder.type;
        this.literal = builder.literal;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getLiteral() {
        return this.literal;
    }

    public static class Builder {
        private TokenType type;
        private String literal;

        public Builder() {
        }

        public Builder setType(TokenType type) {
            this.type = type;
            return this;
        }

        public Builder setLiteral(String literal) {
            this.literal = literal;
            return this;
        }

        public Token build() {
            return new Token(this);
        }

        public String toString() {
            return String.format("Token.Builder with type: %s and literal %s", this.type, this.literal);
        }
    }
}