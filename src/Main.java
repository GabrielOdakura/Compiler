import Analise.LexicalAnalysis;
import Analise.SintaticAnalysis;
import Model.Token;

import java.io.*;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        String path = args[0] + "\\" + args[1];
        try {
            LinkedList<Token> tokenList = new LinkedList<>();
            LexicalAnalysis lexer = new LexicalAnalysis(path);
            tokenList = lexer.empacotarTokens();

            SintaticAnalysis parser = new SintaticAnalysis(tokenList);
            parser.parse();

            System.out.println("Parser Funcionou!");

            //debug
            System.out.println("--------------------------------DEBUG--------------------------------");
            System.out.println("tamanho: " + tokenList.size()+ "\n");
            for (int i = 0; i < tokenList.size(); i++){
                System.out.println("[Indice Model.Token: " + i + "]\t||\t" + tokenList.get(i) + "\n");
            }
            System.out.println("--------------------------------DEBUG--------------------------------");

        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
