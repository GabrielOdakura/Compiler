import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String path = args[0] + "\\" + args[1];
            FileReader input = new FileReader(path);
            BufferedReader reader = new BufferedReader(input);
            PushbackReader pushback = new PushbackReader(reader);

            //análise Léxica
            int caracterAtual;
            boolean adicionarPV = false; //adiciona um ";" caso seja verdadeiro
            StringBuilder tokenBuilder = new StringBuilder();
            ArrayList<Token> tokenList = new ArrayList<>();
            while ((caracterAtual = pushback.read()) != -1){// caracterAtual >= 48 && caracterAtual <= 57
                //System.out.println(tokenBuilder.toString());
                if(tokenBuilder.length() == 1 && (caracterAtual >= 48 && caracterAtual <= 57)){// reconhece cadeias de números
                    tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                    int peekChar;
                    while((peekChar = TokenUtils.peek(pushback)) >= 48 && peekChar <= 57){// look ahead para ver se o próximo char é um número
                        tokenBuilder.append(Character.toChars(pushback.read())[0]);
                    }
                    tokenList.add(new Token(TokenType.INT_LIT, tokenBuilder.toString().toCharArray()));
                    tokenBuilder.setLength(0);
                }else if(caracterAtual == 39) {//procura por um 'C', C sendo um caracter qualquer
                    if(tokenBuilder.length() == 1){
                        tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                        tokenBuilder.append(Character.toChars(TokenUtils.peek(pushback))[0]);
                    }else{

                    }
                }else if(caracterAtual != 32 && caracterAtual != '\n' && caracterAtual != '\r'){//checa por espaços ou quebras de linha
                    tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                    if (TokenUtils.peek(pushback) == 59) {
                        Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                        if (tokenAtual != null) {
                            tokenList.add(tokenAtual);
                            tokenList.add(new Token(TokenType.SEMICOLON, new char[]{Character.toChars(pushback.read())[0]}));
                        }
                        tokenBuilder.setLength(0);
                    }
                }else{
                    if(!tokenBuilder.isEmpty()){
                        Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                        if(tokenAtual != null){
                            tokenList.add(tokenAtual);
                        }
                        tokenBuilder.setLength(0);
                    }
                }
            }

            if(caracterAtual == -1){
                Token newToken = new Token(TokenType.ID, tokenBuilder.toString().toCharArray());
                tokenBuilder.setLength(0);
                tokenList.add(newToken);
            }

            //fecha o buffer para não ter entrada a mais de dados
            reader.close();

            //debug
            System.out.println("--------------------------------DEBUG--------------------------------");
            System.out.println("tamanho: " + tokenList.size()+ "\n");
            for (int i = 0; i < tokenList.size(); i++){
                System.out.println("[Indice Token: " + i + "]\t||\t" + tokenList.get(i) + "\n");
            }
            System.out.println("--------------------------------DEBUG--------------------------------");

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
