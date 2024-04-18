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
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(input);

            //lê os tokens
            String lines;
            while ((lines = reader.readLine()) != null){
                builder.append(lines);
                System.out.println(lines);
            }

            //fecha o buffer para não ter entrada a mais de dados
            reader.close();

            //transformando a string recebida em uma array de caracteres
            String transform = builder.toString();
            char[] cadeia = null;
            cadeia = new char[transform.length()];
            for(int i = 0; i < transform.length(); i++){
                cadeia[i] = transform.charAt(i);
            }


            StringBuilder tokenBuilder = new StringBuilder();
            ArrayList<Token> tokenList = new ArrayList<>();
            for (int i = 0; i < cadeia.length; i++){
                System.out.println("Char: " + cadeia[i] + "\tInt: " + (int) cadeia[i] + "\tAt: " + i);
                if (cadeia[i] != 32 && cadeia[i] != '\n'){
                    tokenBuilder.append(cadeia[i]);
                }else{
                    if(!tokenBuilder.isEmpty()){
                        Token newToken = new Token(TokenType.id, tokenBuilder.toString().toCharArray());
                        tokenBuilder.setLength(0);
                        tokenList.add(newToken);
                    }
                }
                if(i == (cadeia.length - 1)){
                    Token newToken = new Token(TokenType.id, tokenBuilder.toString().toCharArray());
                    tokenBuilder.setLength(0);
                    tokenList.add(newToken);
                }
            }

            for (int i = 0; i < tokenList.size(); i++){
                System.out.println("tamanho: " + tokenList.size());
                System.out.println(tokenList.get(i));
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
