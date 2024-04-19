import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String path = args[0] + "\\" + args[1];
            FileReader input = new FileReader(path);
            BufferedReader reader = new BufferedReader(input);

            //análise Léxica
            int caracterAtual;
            StringBuilder tokenBuilder = new StringBuilder();
            ArrayList<Token> tokenList = new ArrayList<>();
            while ((caracterAtual = reader.read()) != -1){
                System.out.println(tokenBuilder.toString());
                if (caracterAtual != 32 && caracterAtual != '\n' && caracterAtual != '\r'){
                    tokenBuilder.append((char) caracterAtual);
                }else{
                    if(!tokenBuilder.isEmpty()){
                        Token newToken = new Token(TokenType.id, tokenBuilder.toString().toCharArray());
                        tokenBuilder.setLength(0);
                        tokenList.add(newToken);
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
