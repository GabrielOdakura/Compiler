import java.io.*;
import java.util.ArrayList;

public class Token {
    public TokenType tipo;
    public char[] cadeia;

    public Token (TokenType tipo, char[] cadeiaRecebida){
        this.tipo = tipo;
        this.cadeia = new char[cadeiaRecebida.length];
        for(int i = 0; i < cadeiaRecebida.length; i++){
            cadeia[i] = cadeiaRecebida[i];
        }
    }

    public ArrayList<Token> lerTokens(String path) throws IOException {
        FileReader input = new FileReader(path);
        BufferedReader reader = new BufferedReader(input);
        PushbackReader pushback = new PushbackReader(reader);

        int caracterAtual;
        StringBuilder tokenBuilder = new StringBuilder();
        ArrayList<Token> tokenList = new ArrayList<>();
        while ((caracterAtual = pushback.read()) != -1){
            //System.out.println(tokenBuilder.toString());
            if(tokenBuilder.length() == 0 && (caracterAtual >= 48 && caracterAtual <= 57)){// reconhece cadeias de números
                tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                int peekChar;
                while((peekChar = TokenUtils.peek(pushback)) >= 48 && peekChar <= 57){// look ahead para ver se o próximo char é um número
                    tokenBuilder.append(Character.toChars(pushback.read())[0]);
                }
                tokenList.add(new Token(TokenType.INT_LIT, tokenBuilder.toString().toCharArray()));
                tokenBuilder.setLength(0);
            }else if(caracterAtual == 39) {//procura por um 'C', C sendo um caracter qualquer
                if(tokenBuilder.length() == 0){
                    tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                    tokenBuilder.append(Character.toChars(pushback.read())[0]);
                    int peekedChar = TokenUtils.peek(pushback);
                    if(peekedChar == 39){//caso o próximo char seja ' ele consome o caracter
                        tokenBuilder.append(Character.toChars(pushback.read())[0]);
                        Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                        tokenList.add(tokenAtual);
                        tokenBuilder.setLength(0);
                    }else{// caso o próximo caracter não seja ' ele retorna um token inválido
                        Token tokenAtual = new Token(TokenType.INVALID, null);
                    }
                }else{
                    Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenBuilder.setLength(0);
                }
            }else if(caracterAtual == 40 || caracterAtual == 41){
                if(tokenBuilder.length() != 0){
                    Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenAtual = reconhecerParenteses(caracterAtual);
                    tokenList.add(tokenAtual);
                    tokenBuilder.setLength(0);
                }else{
                    Token tokenAtual = reconhecerParenteses(caracterAtual);
                    tokenList.add(tokenAtual);
                }
            }else if(caracterAtual != 32 && caracterAtual != '\n' && caracterAtual != '\r'){//checa por espaços ou quebras de linha
                tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                if (TokenUtils.peek(pushback) == 59) {
                    Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenList.add(new Token(TokenType.SEMICOLON, new char[]{Character.toChars(pushback.read())[0]}));
                    tokenBuilder.setLength(0);
                }
            }else{
                if(!tokenBuilder.isEmpty()){
                    Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenBuilder.setLength(0);
                }
            }
        }

        if(caracterAtual == -1 && tokenBuilder.length() != 0){
            Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
            tokenList.add(tokenAtual);
            tokenBuilder.setLength(0);
        }
        return tokenList;
    }

    private Token reconhecerParenteses(int caracterAtual){
        Token novoToken = null;
        if (caracterAtual == 40){// char '('
            novoToken = new Token(TokenType.abreParenteses, new char[]{'('});
        }else if(caracterAtual == 41){// char ')'
            novoToken = new Token(TokenType.fechaParenteses, new char[]{')'});
        }
        return novoToken;
    }

    @Override
    public String toString() {
        String teste = "";
        for(int i = 0; i < this.cadeia.length; i++) teste = teste + cadeia[i];
        return "[Tipo: " + this.tipo + "]\t||\t[Cadeia: " + teste + "]";
    }
}
