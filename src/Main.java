import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String path = args[0] + "\\" + args[1];
        try {
            ArrayList<Token> tokenList = new ArrayList<>();
            tokenList = Token.lerTokens(path);

            /*NOVA PARTE*/
            /*
            ArrayList<Arvore> listaArvore = new ArrayList<>();
            ArrayList<Token> lAux = null;
            TreeUtils checarArvore = new TreeUtils();

            int h = 0;
            int j = 0;
            while(j <= tokenList.size()){
                Token tAux = tokenList.get(j);
                while(!(tokenList.get(h).cadeia[0] == ';')) {
                    lAux.add(tAux);
                    h++;
                }
                Arvore novaArvore = checarArvore.construirArvore(lAux,checarArvore.checkTree(lAux));
                listaArvore.add(novaArvore);
                j = h;
            }
            */

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
