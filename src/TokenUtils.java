import java.io.IOException;
import java.io.PushbackReader;

public class TokenUtils {
    public static Token checkToken (StringBuilder tokenBuilder){
        Token newToken = null;
        if(tokenBuilder.toString().equals("principal")){
            newToken = new Token(TokenType.PRINCIPAL, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("pf")){
            newToken = new Token(TokenType.PF, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("obg")){
            newToken = new Token(TokenType.OBG, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("int")){
            newToken = new Token(TokenType.INT, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("car")){
            newToken = new Token(TokenType.CAR, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("bool")){
            newToken = new Token(TokenType.BOOL, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("faca")){
            newToken = new Token(TokenType.FACA, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("enquanto")){
            newToken = new Token(TokenType.ENQUANTO, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("para")){
            newToken = new Token(TokenType.PARA, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("retornar")){
            newToken = new Token(TokenType.RETORNAR, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("se")){
            newToken = new Token(TokenType.SE, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("ou")){
            newToken = new Token(TokenType.OU, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("+")){
            newToken = new Token(TokenType.MAIS, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("-")){
            newToken = new Token(TokenType.MENOS, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("*")){
            newToken = new Token(TokenType.MULT, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals(";")){
            newToken = new Token(TokenType.SEMICOLON, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("VERDADEIRO")){
            newToken = new Token(TokenType.BOOL_LIT, tokenBuilder.toString().toCharArray());
        }else if(tokenBuilder.toString().equals("FALSO")){
            newToken = new Token(TokenType.BOOL_LIT, tokenBuilder.toString().toCharArray());
        }else {
            newToken = new Token(TokenType.ID, tokenBuilder.toString().toCharArray());
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
