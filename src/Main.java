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
            while ((caracterAtual = pushback.read()) != -1){
                System.out.println(tokenBuilder.toString());
                // IMPORTANTE: Reestruturar isso para usar o TokenUtils.peek() para procurar caracteres ao invés de só espaços
                if(caracterAtual != 32 && caracterAtual != '\n' && caracterAtual != '\r'){//checa por espaços ou quebras de linha
                    tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                    if (TokenUtils.peek(pushback) == 59) {
                        Token tokenAtual = TokenUtils.checkToken(tokenBuilder);
                        if (tokenAtual != null) {
                            tokenList.add(tokenAtual);
                            tokenList.add(new Token(TokenType.semicolon, new char[]{';'}));
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
                Token newToken = new Token(TokenType.id, tokenBuilder.toString().toCharArray());
                tokenBuilder.setLength(0);
                tokenList.add(newToken);
            }

            //fecha o buffer para não ter entrada a mais de dados
            reader.close();

            //debug function
            System.out.println("tamanho: " + tokenList.size());
            for (int i = 0; i < tokenList.size(); i++){
                System.out.println(tokenList.get(i));
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
