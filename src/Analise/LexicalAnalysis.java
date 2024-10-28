package Analise;

import Exceptions.LexicalException;
import Model.Token;
import Model.TokenType;

import java.io.*;
import java.util.LinkedList;

public class LexicalAnalysis {
    private final PushbackReader reader;
    public LexicalAnalysis(String path) {
        this.reader = lerArquivo(path);
    }

    private PushbackReader lerArquivo(String path) {
        PushbackReader pushback;
        try {
            FileReader input = new FileReader(path);
            BufferedReader reader = new BufferedReader(input);
            pushback = new PushbackReader(reader);
        }catch (IOException e){
            throw new RuntimeException("Não foi possível ler o arquivo!");
        }
        return pushback;
    }

    public LinkedList<Token> empacotarTokens() throws IOException {
        int caracterAtual;
        StringBuilder tokenBuilder = new StringBuilder();
        LinkedList<Token> tokenList = new LinkedList<>();
        while ((caracterAtual = reader.read()) != -1){
            //System.out.println(tokenBuilder.toString());
            if(tokenBuilder.length() == 0 && (caracterAtual >= 48 && caracterAtual <= 57)){// reconhece cadeias de números
                tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                int peekChar;
                while((peekChar = peek(reader)) >= 48 && peekChar <= 57){// look ahead para ver se o próximo char é um número
                    tokenBuilder.append(Character.toChars(reader.read())[0]);
                }
                tokenList.add(new Token(TokenType.INT_LIT, tokenBuilder.toString().toCharArray()));
                tokenBuilder.setLength(0);
            }else if(caracterAtual == 39) {//procura por um 'C', C sendo um caracter qualquer
                if(tokenBuilder.length() == 0){
                    tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                    tokenBuilder.append(Character.toChars(reader.read())[0]);
                    int peekedChar = peek(reader);
                    if(peekedChar == 39){//caso o próximo char seja ' ele consome o caracter
                        tokenBuilder.append(Character.toChars(reader.read())[0]);
                        Token tokenAtual = checkToken(tokenBuilder);
                        tokenList.add(tokenAtual);
                        tokenBuilder.setLength(0);
                    }else{// caso o próximo caracter não seja ' ele retorna um token inválido
                        throw new LexicalException("Erro Léxico: Caracter declarado não é válido!");
                    }
                }else{
                    Token tokenAtual = checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenBuilder.setLength(0);
                }
            }else if(caracterAtual == '(' || caracterAtual == ')'){
                if(tokenBuilder.length() != 0){
                    Token tokenAtual = checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenAtual = reconhecerParenteses(caracterAtual);
                    tokenList.add(tokenAtual);
                    tokenBuilder.setLength(0);
                }else{
                    Token tokenAtual = reconhecerParenteses(caracterAtual);
                    tokenList.add(tokenAtual);
                }
            }else if(caracterAtual != ' ' && caracterAtual != '\n' && caracterAtual != '\r'){//checa por espaços ou quebras de linha
                tokenBuilder.append(Character.toChars(caracterAtual)[0]);
                if (peek(reader) == ';') {
                    Token tokenAtual = checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenList.add(new Token(TokenType.SEMICOLON, new char[]{Character.toChars(reader.read())[0]}));
                    tokenBuilder.setLength(0);
                }
            }else{
                if(!tokenBuilder.isEmpty() ){
                    Token tokenAtual = checkToken(tokenBuilder);
                    tokenList.add(tokenAtual);
                    tokenBuilder.setLength(0);
                }
            }
        }

        if(caracterAtual == -1 && tokenBuilder.length() != 0){
            Token tokenAtual = checkToken(tokenBuilder);
            tokenList.add(tokenAtual);
            tokenBuilder.setLength(0);
            tokenList.add(new Token(TokenType.EOF, new char[0]));
        }
        return tokenList;
    }


    private Token checkToken (StringBuilder tokenBuilder){
        Token newToken = null;
        switch (tokenBuilder.toString()) {
            case "principal" -> newToken = new Token(TokenType.PRINCIPAL, tokenBuilder.toString().toCharArray());
            case "pf" -> newToken = new Token(TokenType.PF, tokenBuilder.toString().toCharArray());
            case "obg" -> newToken = new Token(TokenType.OBG, tokenBuilder.toString().toCharArray());
            case "int" -> newToken = new Token(TokenType.INT, tokenBuilder.toString().toCharArray());
            case "car" -> newToken = new Token(TokenType.CAR, tokenBuilder.toString().toCharArray());
            case "bool" -> newToken = new Token(TokenType.BOOL, tokenBuilder.toString().toCharArray());
            case "faca" -> newToken = new Token(TokenType.FACA, tokenBuilder.toString().toCharArray());
            case "enquanto" -> newToken = new Token(TokenType.ENQUANTO, tokenBuilder.toString().toCharArray());
            case "para" -> newToken = new Token(TokenType.PARA, tokenBuilder.toString().toCharArray());
            case "retornar" -> newToken = new Token(TokenType.RETORNAR, tokenBuilder.toString().toCharArray());
            case "se" -> newToken = new Token(TokenType.SE, tokenBuilder.toString().toCharArray());
            case "senao" -> newToken = new Token(TokenType.SENAO, tokenBuilder.toString().toCharArray());
            case "entao" -> newToken = new Token(TokenType.ENTAO, tokenBuilder.toString().toCharArray());
            case "ou" -> newToken = new Token(TokenType.OU, tokenBuilder.toString().toCharArray());
            case "+" -> newToken = new Token(TokenType.MAIS, tokenBuilder.toString().toCharArray());
            case "-" -> newToken = new Token(TokenType.MENOS, tokenBuilder.toString().toCharArray());
            case "*" -> newToken = new Token(TokenType.MULT, tokenBuilder.toString().toCharArray());
            case ";" -> newToken = new Token(TokenType.SEMICOLON, tokenBuilder.toString().toCharArray());
            case "=" -> newToken = new Token(TokenType.IGUAL, tokenBuilder.toString().toCharArray());
            case "VERDADEIRO", "FALSO" -> newToken = new Token(TokenType.BOOL_LIT, tokenBuilder.toString().toCharArray());
            default -> {
                if ((tokenBuilder.charAt(0) >= 65 && tokenBuilder.charAt(0) <= 90) || (tokenBuilder.charAt(0) >= 97 && tokenBuilder.charAt(0) <= 122)) {
                    newToken = new Token(TokenType.ID, tokenBuilder.toString().toCharArray());
                } else
                    throw new LexicalException("Declaração de ID só pode começar com letras maiúsculas e minúsculas! Valor do Token: " + tokenBuilder.toString());
            }
        }
        return newToken;
    }

    private Token reconhecerParenteses(int caracterAtual){
        Token novoToken = null;
        if (caracterAtual == 40){// char '('
            novoToken = new Token(TokenType.ABRE_PARENTESES, new char[]{'('});
        }else if(caracterAtual == 41){// char ')'
            novoToken = new Token(TokenType.FECHA_PARENTESES, new char[]{')'});
        }
        return novoToken;
    }

    private int peek(PushbackReader pushbackReader) throws IOException {
        int nextChar = pushbackReader.read();
        if(nextChar != -1)
            pushbackReader.unread(nextChar);
        return nextChar;
    }
}
