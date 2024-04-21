import java.io.IOException;
import java.io.PushbackReader;

public class TokenUtils {
    public static Token checkToken (StringBuilder tokenBuilder){
        Token newToken = null;
        if(tokenBuilder.toString().equals("principal")){
            newToken = new Token(TokenType.principal, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("pf")){
            newToken = new Token(TokenType.pf, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("obg")){
            newToken = new Token(TokenType.obg, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("int")){
            newToken = new Token(TokenType.Int, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("car")){
            newToken = new Token(TokenType.car, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("bool")){
            newToken = new Token(TokenType.bool, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("faca")){
            newToken = new Token(TokenType.faca, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("enquanto")){
            newToken = new Token(TokenType.enquanto, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("para")){
            newToken = new Token(TokenType.para, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("retornar")){
            newToken = new Token(TokenType.retornar, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("se")){
            newToken = new Token(TokenType.se, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("ou")){
            newToken = new Token(TokenType.ou, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("+")){
            newToken = new Token(TokenType.mais, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("-")){
            newToken = new Token(TokenType.menos, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("*")){
            newToken = new Token(TokenType.mult, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals(";")){
            newToken = new Token(TokenType.semicolon, tokenBuilder.toString().toCharArray());
        }else {
            newToken = new Token(TokenType.id, tokenBuilder.toString().toCharArray());
        }
        return newToken;
    }

    public static int peek(PushbackReader pushbackReader) throws IOException {
        int nextChar = pushbackReader.read();
        if(nextChar != -1)
        pushbackReader.unread(nextChar);
        return nextChar;
    }
}
